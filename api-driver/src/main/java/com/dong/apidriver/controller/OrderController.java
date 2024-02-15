package com.dong.apidriver.controller;

import com.dong.apidriver.service.ApiDriverOrderInfoService;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ApiDriverOrderInfoService apiDriverOrderInfoService;

    /**
     * 司机去接乘客
     * @param orderDTO
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderDTO orderDTO){
        return apiDriverOrderInfoService.toPickUpPassenger(orderDTO);
    }

    /**
     * 司机到达乘客上车点
     * @param orderDTO
     * @return
     */
    @PostMapping("/driver-arrived-departure")
    public ResponseResult driverArrivedDeparture(@RequestBody OrderDTO orderDTO){
        return apiDriverOrderInfoService.driverArrivedDeparture(orderDTO);
    }

    /**
     * 乘客上车
     * @param orderDTO
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderDTO orderDTO){
        return apiDriverOrderInfoService.pickUpPassenger(orderDTO);
    }

    /**
     * 乘客到达目的地，下车
     * @param orderDTO
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderDTO orderDTO){
        return apiDriverOrderInfoService.passengerGetoff(orderDTO);
    }

    /**
     * 取消订单
     * @param orderId 订单id
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId){
        return apiDriverOrderInfoService.cancel(orderId);
    }
}
