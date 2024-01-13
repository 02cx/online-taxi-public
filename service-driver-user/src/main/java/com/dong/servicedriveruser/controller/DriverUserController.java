package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }
}
