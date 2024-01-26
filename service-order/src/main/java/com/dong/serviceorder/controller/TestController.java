package com.dong.serviceorder.controller;

import com.dong.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/test1")
    public Integer test1(){
        return orderInfoService.passengerOrderOnGoing(3L);
    }
}
