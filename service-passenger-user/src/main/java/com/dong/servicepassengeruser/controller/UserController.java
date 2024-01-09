package com.dong.servicepassengeruser.controller;

import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
