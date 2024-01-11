package com.dong.servicepassengeruser.controller;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("查询用户的电话：" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable String phone){
        return userService.getUserByPhone(phone);
    }
}
