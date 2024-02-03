package com.dong.serviceorder.controller;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 生成订单----乘客端传递参数
     * @param orderDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderDTO orderDTO){
        log.info(orderDTO.toString());
        return orderInfoService.addOrderInfo(orderDTO);
    }

    /**
     * 司机去接乘客
     * @param orderDTO
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderDTO orderDTO){
        return orderInfoService.toPickUpPassenger(orderDTO);
    }

    /**
     * 司机到达乘客上车点
     * @param orderDTO
     * @return
     */
    @PostMapping("/driver-arrived-departure")
    public ResponseResult driverArrivedDeparture(@RequestBody OrderDTO orderDTO){
        return orderInfoService.driverArrivedDeparture(orderDTO);
    }

    /**
     * 乘客上车
     * @param orderDTO
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderDTO orderDTO){
        return orderInfoService.pickUpPassenger(orderDTO);
    }

    /**
     * 乘客到达目的地，下车
     * @param orderDTO
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderDTO orderDTO){
        return orderInfoService.passengerGetoff(orderDTO);
    }

}
