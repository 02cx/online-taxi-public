package com.dong.serviceorder.controller;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.serviceorder.domain.OrderInfo;
import com.dong.serviceorder.mapper.OrderInfoMapper;
import com.dong.serviceorder.service.OrderInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @GetMapping("/test1")
    public Integer test1(){
        return orderInfoService.passengerOrderOnGoing(3L);
    }

    @GetMapping("/test-real-time-order/{orderId}")
    public String testRealTimeOrder(@PathVariable Long orderId){
        System.out.println("并发测试，orderId：" + orderId);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.dispatchRealTimeOrder(orderInfo);
        return "success";
    }
}
