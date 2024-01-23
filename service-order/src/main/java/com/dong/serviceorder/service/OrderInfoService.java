package com.dong.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.OrderConstant;
import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.request.PriceRuleDTO;
import com.dong.internalcommon.response.PriceRuleResponse;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.util.RedisPrefixUtils;
import com.dong.serviceorder.domain.OrderInfo;
import com.dong.serviceorder.mapper.OrderInfoMapper;
import com.dong.serviceorder.remote.ServiceDriverUserClient;
import com.dong.serviceorder.remote.ServiceMapClient;
import com.dong.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

    /**
     * 新增订单
     * @param orderDTO
     * @return
     */
    public ResponseResult addOrderInfo(OrderDTO orderDTO){
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderDTO,orderInfo);
        // 城市编码+车辆类型是否开通
        // 放在这里判断原因：只有城市和车型开通了服务，才能获取到计价版本，以及用户请求
        if(!isCityVehicleExists(orderDTO)){
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_EXISTS);
        }
        // 判断城市是否有可用司机
        String cityCode = orderDTO.getAddress();
        ResponseResult<Boolean> driverAvailable = serviceDriverUserClient.isDriverAvailable(cityCode);
        if(!driverAvailable.getData()){
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_NOT_AVAILABLE);
        }
        // 获取最新的计价版本
        ResponseResult<PriceRuleResponse> latest = servicePriceClient.latest(orderDTO.getFareType());
        PriceRuleResponse priceRuleResponse = latest.getData();
        orderInfo.setFareVersion(priceRuleResponse.getFareVersion());
        // 判断用户是否有订单还在继续
        int count = orderOnGoing(orderDTO.getPassengerId());
        if(count != 0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON);
        }
        // 判断用户设备是否是黑名单
        String blackDeviceKey = RedisPrefixUtils.blackDeviceCodePrefix + orderDTO.getDeviceCode();
        Boolean aBoolean = stringRedisTemplate.hasKey(blackDeviceKey);
        if(aBoolean){
            // blackDeviceKey 存在
            String s = stringRedisTemplate.opsForValue().get(blackDeviceKey);
            Integer accessCount  = Integer.parseInt(s);
            if(accessCount >= 2){
                return ResponseResult.fail(CommonStatusEnum.DEVICE_INVALID);
            }
            stringRedisTemplate.opsForValue().increment(blackDeviceKey);
        }else{
            // blackDeviceKey 不存在，用户第一次下单
            stringRedisTemplate.opsForValue().setIfAbsent(blackDeviceKey,OrderConstant.INIT_ORDER_COUNT,OrderConstant.BLACK_DEVICE_TIME, TimeUnit.HOURS);
        }

        // 周边终端搜索
        dispatchRealTimeOrder(orderInfo);

        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success();
    }



    /**
     *  周边终端搜索
     * @param orderInfo
     */
    public void dispatchRealTimeOrder(OrderInfo orderInfo){
        AroundSearchTerminalDTO aroundSearchTerminalDTO = new AroundSearchTerminalDTO();
        aroundSearchTerminalDTO.setCenter(orderInfo.getDepLatitude() + "," + orderInfo.getDepLongitude());
        aroundSearchTerminalDTO.setRadius(2);
        ResponseResult<List<TerminalResponse>> listResponseResult = serviceMapClient.aroundSearchTerminal(aroundSearchTerminalDTO);
        if(listResponseResult.getData().size() == 0){
            aroundSearchTerminalDTO.setRadius(4);
            listResponseResult = serviceMapClient.aroundSearchTerminal(aroundSearchTerminalDTO);
            if(listResponseResult.getData().size() == 0){
                aroundSearchTerminalDTO.setRadius(5);
                listResponseResult = serviceMapClient.aroundSearchTerminal(aroundSearchTerminalDTO);
                if(listResponseResult.getData().size() == 0){
                    log.info("周边2km,4km,5km内没有搜索到有效的司机");
                }
            }
        }
    }


    //城市编码+车辆类型是否开通
    private Boolean isCityVehicleExists(OrderDTO orderDTO){
        String fareType = orderDTO.getFareType();
        int index = fareType.indexOf("$");
        PriceRuleDTO priceRuleDTO = new PriceRuleDTO();
        priceRuleDTO.setCityCode(fareType.substring(0,index));
        priceRuleDTO.setVehicleType(fareType.substring(index + 1,fareType.length()));

        ResponseResult<Boolean> responseResult = servicePriceClient.ifExists(priceRuleDTO);
        return responseResult.getData();
    }


    /**
     *  查找进行中的订单
     * @param passengerId
     * @return
     */
    public int orderOnGoing(Long passengerId){
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getPassengerId,passengerId);
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
}
