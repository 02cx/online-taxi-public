package com.dong.servicealipay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public String test(){
        System.out.println("支付宝回调了");
        return "支付宝回调了";
    }
}
