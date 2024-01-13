package com.dong.servicedriveruser.controller;

import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @GetMapping("/test")
    public ResponseResult selectOne(){
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }
}
