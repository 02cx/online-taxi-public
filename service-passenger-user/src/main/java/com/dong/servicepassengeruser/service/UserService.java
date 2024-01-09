package com.dong.servicepassengeruser.service;

import com.dong.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone){
        // 根据乘客手机号查询用户信息

        // 判断用户信息是否存在

        // 用户信息不存在，则注册，否则不处理

        // 响应
        return ResponseResult.success("");
    }
}
