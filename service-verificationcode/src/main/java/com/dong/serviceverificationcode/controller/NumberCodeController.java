package com.dong.serviceverificationcode.controller;

import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") Integer size){

        System.out.println("size:" + size);

        // 生成验证码
        double mathRandom = (Math.random()*9 + 1) * Math.pow(10,size-1);
        System.out.println(mathRandom);
        int randomInt = (int) mathRandom;
        System.out.println("remote number code : " + randomInt);

        // 定义返回值
        NumberCodeResponse response = new NumberCodeResponse(randomInt);

        return ResponseResult.success(response);
    }
    
}
