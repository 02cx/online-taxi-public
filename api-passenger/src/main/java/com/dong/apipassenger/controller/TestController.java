package com.dong.apipassenger.controller;

import com.dong.apipassenger.remote.ServiceOrderClient;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "test api passenger";
    }

    @GetMapping("/authTest")
    public ResponseResult authTest(){
        return ResponseResult.success("auth test");
    }

    @GetMapping("/noauthTest")
    public ResponseResult noauthTest(){
        return ResponseResult.success("noauth test");
    }

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    @GetMapping("/test-real-time-order/{orderId}")
    public String testRealTimeOrder(@PathVariable Long orderId){
        System.out.println("api-passenger,orderId = " + orderId);
        serviceOrderClient.testRealTimeOrder(orderId);
        return "success";
    }
}
