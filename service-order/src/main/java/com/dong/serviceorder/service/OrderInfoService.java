package com.dong.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.IdentityConstant;
import com.dong.internalcommon.constant.OrderConstant;
import com.dong.internalcommon.request.*;
import com.dong.internalcommon.response.*;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.util.RedisPrefixUtils;
import com.dong.serviceorder.domain.OrderInfo;
import com.dong.serviceorder.mapper.OrderInfoMapper;
import com.dong.serviceorder.remote.ServiceDriverUserClient;
import com.dong.serviceorder.remote.ServiceMapClient;
import com.dong.serviceorder.remote.ServicePriceClient;
import com.dong.serviceorder.remote.ServiceSsePushClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.tomcat.jni.Local;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    /**
     * 新增订单
     *
     * @param orderDTO 订单DTO
     * @return ResponseResult
     */
    public ResponseResult addOrderInfo(OrderDTO orderDTO) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderDTO, orderInfo);
        // 城市编码+车辆类型是否开通
        // 放在这里判断原因：只有城市和车型开通了服务，才能获取到计价版本，以及用户请求
        if (!isCityVehicleExists(orderDTO)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_EXISTS);
        }
        // 判断城市是否有可用司机
        String cityCode = orderDTO.getAddress();
        ResponseResult<Boolean> driverAvailable = serviceDriverUserClient.isDriverAvailable(cityCode);
        if (!driverAvailable.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_NOT_AVAILABLE);
        }
        // 获取最新的计价版本
        ResponseResult<PriceRuleResponse> latest = servicePriceClient.latest(orderDTO.getFareType());
        PriceRuleResponse priceRuleResponse = latest.getData();
        orderInfo.setFareVersion(priceRuleResponse.getFareVersion());
        // 判断用户是否有订单还在继续
        int count = passengerOrderOnGoing(orderDTO.getPassengerId());
        if (count != 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON);
        }
        // 判断用户设备是否是黑名单
        String blackDeviceKey = RedisPrefixUtils.blackDeviceCodePrefix + orderDTO.getDeviceCode();
        Boolean aBoolean = stringRedisTemplate.hasKey(blackDeviceKey);
        if (aBoolean) {
            // blackDeviceKey 存在
            String s = stringRedisTemplate.opsForValue().get(blackDeviceKey);
            Integer accessCount = Integer.parseInt(s);
            //WYD TODO 2024-01-25: 访问次数应该为2，为了方便测试这里改为100
            if (accessCount >= 100) {
                return ResponseResult.fail(CommonStatusEnum.DEVICE_INVALID);
            }
            stringRedisTemplate.opsForValue().increment(blackDeviceKey);
        } else {
            // blackDeviceKey 不存在，用户第一次下单
            stringRedisTemplate.opsForValue().setIfAbsent(blackDeviceKey, OrderConstant.INIT_ORDER_COUNT, OrderConstant.BLACK_DEVICE_TIME, TimeUnit.HOURS);
        }
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        orderInfoMapper.insert(orderInfo);


        // 周边终端搜索【派单】
        // 定时任务的处理
        for (int i = 0; i < 6; i++) {
            // 派单
            Integer result = dispatchRealTimeOrder(orderInfo);
            if(result == 1){
                break;
            }
            if(i == 5){
                // 订单无效
                orderInfo.setOrderStatus(OrderConstant.ORDER_INVALID);
                orderInfoMapper.updateById(orderInfo);
            }

            // 等待20s
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




        return ResponseResult.success();
    }


    /**
     * 周边终端搜索----派单
     *
     * @param orderInfo
     */
    public Integer dispatchRealTimeOrder(OrderInfo orderInfo) {
        AroundSearchTerminalDTO aroundSearchTerminalDTO = new AroundSearchTerminalDTO();
        aroundSearchTerminalDTO.setCenter(orderInfo.getDepLatitude() + "," + orderInfo.getDepLongitude());

        Integer result = 0;

        ArrayList<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        boolean flag = true;
        for (int i = 0; i < radiusList.size() && flag; i++) {
            Integer radius = radiusList.get(i);
            aroundSearchTerminalDTO.setRadius(radius);
            log.info("在半径" + radius + "m内搜索终端");
            // 获取终端
            ResponseResult<List<TerminalResponse>> listResponseResult = serviceMapClient.aroundSearchTerminal(aroundSearchTerminalDTO);
            // 解析终端
            List<TerminalResponse> data = listResponseResult.getData();
            log.info("搜索结果" + data);
            // 根据解析出来的终端，查询车辆信息
            for (int j = 0; j < data.size() && flag; j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                OrderDriverResponse orderDriverResponse = availableDriver.getData();
                log.info("搜索到的终端对应的司机信息：" + orderDriverResponse);
                if (availableDriver.getCode() != 1) {
                    log.info("没有车辆的carId:" + carId + "对应的司机");
                } else {
                    log.info("车辆Id：" + carId + "找到了正在出车的司机");
                    Long driverId = orderDriverResponse.getDriverId();

                    String driverVehicleType = orderDriverResponse.getVehicleType();
                    String orderVehicleType = orderInfo.getVehicleType();
                    if (!driverVehicleType.trim().equals(orderVehicleType.trim())) {
                        System.out.println("匹配的车型和订单车型不同");
                        continue;
                    }

                    //synchronized (driverId){  // 不能解决并发问题
                    //synchronized ((driverId + "").intern()){  // 能解决并发问题
                    String lock = (driverId + "").intern();
                    RLock rLock = redissonClient.getLock(lock);
                    rLock.lock(30, TimeUnit.SECONDS);

                    // 查询司机是否有正在进行的订单
                    if (driverOrderOnGoing(driverId) > 0) {
                        rLock.unlock();
                        continue;
                    }

                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(orderDriverResponse.getDriverPhone());
                    orderInfo.setCarId(orderDriverResponse.getCarId());
                    orderInfo.setVehicleType(orderDriverResponse.getVehicleType());
                    orderInfo.setOrderTime(LocalDateTime.now());
                    orderInfo.setDepartTime(LocalDateTime.now());
                    orderInfo.setReceiveOrderCarLongitude(terminalResponse.getLongitude());
                    orderInfo.setReceiveOrderCarLatitude(terminalResponse.getLatitude());
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());

                    orderInfo.setLicenseId(orderDriverResponse.getLicenseId());
                    orderInfo.setVehicleNo(orderDriverResponse.getVehicleNo());

                    orderInfo.setOrderStatus(OrderConstant.DRIVER_TAKE_ORDER);

                    // 推送消息给司机
                    JSONObject driverContent = new JSONObject();
                    driverContent.put("passengerId", orderInfo.getPassengerId());
                    driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                    // 乘客出发地
                    driverContent.put("departure", orderInfo.getDeparture());
                    driverContent.put("depLongitude", orderInfo.getDepLongitude());
                    driverContent.put("depLatitude", orderInfo.getDepLatitude());
                    // 乘客目的地
                    driverContent.put("destination", orderInfo.getDestination());
                    driverContent.put("destLongitude", orderInfo.getDestLongitude());
                    driverContent.put("destLatitude", orderInfo.getDestLatitude());

                    serviceSsePushClient.push(driverId, IdentityConstant.DRIVER_IDENTITY, driverContent.toString());

                    // 推送消息给乘客
                    JSONObject passengerContent = new JSONObject();
                    passengerContent.put("driverId", orderInfo.getDriverId());
                    passengerContent.put("driverPhone", orderInfo.getDriverPhone());
                    // 司机出发地
                    passengerContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                    passengerContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());
                    // 车辆信息
                    ResponseResult<CarDTO> carById = serviceDriverUserClient.getCarById(carId);
                    CarDTO carDTO = carById.getData();
                    passengerContent.put("brand", carDTO.getBrand());
                    passengerContent.put("model", carDTO.getModel());
                    passengerContent.put("vehicleNo", carDTO.getVehicleNo());
                    serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY, passengerContent.toString());
                    result = 1;
                    // 如果派单成功，则跳出循环
                    orderInfoMapper.updateById(orderInfo);
                    rLock.unlock();
                    flag = false;
                }

            }


        }
        return  result;
    }


    //城市编码+车辆类型是否开通
    private Boolean isCityVehicleExists(OrderDTO orderDTO) {
        String fareType = orderDTO.getFareType();
        int index = fareType.indexOf("$");
        PriceRuleDTO priceRuleDTO = new PriceRuleDTO();
        priceRuleDTO.setCityCode(fareType.substring(0, index));
        priceRuleDTO.setVehicleType(fareType.substring(index + 1, fareType.length()));

        ResponseResult<Boolean> responseResult = servicePriceClient.ifExists(priceRuleDTO);
        return responseResult.getData();
    }


    /**
     * 查找乘客进行中的订单
     *
     * @param passengerId
     * @return
     */
    public int passengerOrderOnGoing(Long passengerId) {
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getPassengerId, passengerId);
        queryWrapper.and(wrapper -> wrapper.eq(OrderInfo::getOrderStatus, OrderConstant.ORDER_START)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.DRIVER_TAKE_ORDER)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.ORDER_GOING_PASSENGER)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.DRIVER_ARRIVE_PICK)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.START_ITINERARY)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.INITIATE_COLLECTION)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.ORDER_UNPAID));
        Integer count = orderInfoMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 查找司机进行中的订单
     *
     * @param driverId
     * @return
     */
    public int driverOrderOnGoing(Long driverId) {
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getDriverId, driverId);
        queryWrapper.and(wrapper -> wrapper.eq(OrderInfo::getOrderStatus, OrderConstant.DRIVER_TAKE_ORDER)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.ORDER_GOING_PASSENGER)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.DRIVER_ARRIVE_PICK)
                .or().eq(OrderInfo::getOrderStatus, OrderConstant.START_ITINERARY));
        Integer count = orderInfoMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 司机去接乘客
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderDTO orderDTO) {
        Long orderId = orderDTO.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setToPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setToPickUpPassengerAddress(orderDTO.getToPickUpPassengerAddress());
        orderInfo.setToPickUpPassengerLongitude(orderDTO.getToPickUpPassengerLongitude());
        orderInfo.setToPickUpPassengerLatitude(orderDTO.getToPickUpPassengerLatitude());
        orderInfo.setOrderStatus(OrderConstant.ORDER_GOING_PASSENGER);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * 司机到达乘客上车点
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult driverArrivedDeparture(OrderDTO orderDTO) {
        Long orderId = orderDTO.getOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.DRIVER_ARRIVE_PICK);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * 乘客上车
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult pickUpPassenger(OrderDTO orderDTO) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderDTO.getOrderId());

        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setPickUpPassengerLongitude(orderDTO.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerLatitude(orderDTO.getPickUpPassengerLatitude());
        orderInfo.setOrderStatus(OrderConstant.START_ITINERARY);
        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 乘客到达目的地，下车
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult passengerGetoff(OrderDTO orderDTO) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderDTO.getOrderId());

        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(orderDTO.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderDTO.getPassengerGetoffLatitude());
        orderInfo.setOrderStatus(OrderConstant.ORDER_UNPAID);


        // 查询终端id
        ResponseResult<CarDTO> carById = serviceDriverUserClient.getCarById(orderInfo.getCarId());
        CarDTO data = carById.getData();
        String tid = data.getTid();
        // 查询载客时间和载客里程
        long starttime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endtime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        ResponseResult<TrsearchResponse> trsearch = serviceMapClient.trsearch(tid, starttime, endtime);
        Long driveMile = trsearch.getData().getDriveMile();
        Long driveTime = trsearch.getData().getDriveTime();
        orderInfo.setDriveMile(driveMile);
        orderInfo.setDriveTime(driveTime);

        // 获取实际价格
        CalculatePriceDTO calculatePriceDTO = new CalculatePriceDTO();
        calculatePriceDTO.setDistance(driveMile.intValue());
        calculatePriceDTO.setDuration(driveTime.intValue());
        calculatePriceDTO.setCityCode(orderInfo.getAddress());
        calculatePriceDTO.setVehicleType(orderInfo.getVehicleType());
        ResponseResult<PriceResponse> responseResult = servicePriceClient.calculatePrice(calculatePriceDTO);
        PriceResponse priceResponse = responseResult.getData();
        orderInfo.setPrice(priceResponse.getActualPrice());
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    /**
     * 支付成功
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult pay(OrderDTO orderDTO) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderDTO.getOrderId());

        orderInfo.setOrderStatus(OrderConstant.PAY_COMPLETED);
        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 取消订单
     *
     * @param orderId  订单id
     * @param identity 用户身份 乘客1  司机2
     * @return
     */
    public ResponseResult cancel(Long orderId, String identity) {
        // 获取订单状态
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Integer orderStatus = orderInfo.getOrderStatus();

        LocalDateTime cancelTime = LocalDateTime.now();
        Integer cancelTypeCode = null;
        Integer cancelOperator = null;
        // 乘客取消
        LocalDateTime receiveOrderTime = orderInfo.getReceiveOrderTime();
        if (identity.trim().equals(IdentityConstant.PASSENGER_IDENTITY)) {
            if (OrderConstant.ORDER_START.equals(orderStatus)) {
                cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
            } else if (orderStatus.equals(OrderConstant.DRIVER_TAKE_ORDER)) {
                long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                if (between < 1) {
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                } else {
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_DEFAULT;
                }
            } else if (orderStatus.equals(OrderConstant.ORDER_GOING_PASSENGER) || orderStatus.equals(OrderConstant.DRIVER_ARRIVE_PICK)) {
                cancelTypeCode = OrderConstant.CANCEL_PASSENGER_DEFAULT;
            } else {
                return ResponseResult.fail(CommonStatusEnum.ORDER_NOT_CANCEL);
            }
            cancelOperator = Integer.parseInt(IdentityConstant.PASSENGER_IDENTITY);
        }
        // 司机取消
        if (identity.trim().equals(IdentityConstant.DRIVER_IDENTITY)) {
            if (orderStatus.equals(OrderConstant.DRIVER_TAKE_ORDER)
                    || orderStatus.equals(OrderConstant.ORDER_GOING_PASSENGER)
                    || orderStatus.equals(OrderConstant.DRIVER_ARRIVE_PICK)) {
                long between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                if (between < 1) {
                    cancelTypeCode = OrderConstant.CANCEL_DRIVER_BEFORE;
                } else {
                    cancelTypeCode = OrderConstant.CANCEL_DRIVER_DEFAULT;
                }
            } else {
                return ResponseResult.fail(CommonStatusEnum.ORDER_NOT_CANCEL);
            }
            cancelOperator = Integer.parseInt(IdentityConstant.DRIVER_IDENTITY);
        }

        orderInfo.setCancelTime(cancelTime);
        orderInfo.setCancelOperator(cancelOperator);
        orderInfo.setCancelTypeCode(cancelTypeCode);
        orderInfo.setOrderStatus(OrderConstant.ORDER_CANCEL);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }
}
