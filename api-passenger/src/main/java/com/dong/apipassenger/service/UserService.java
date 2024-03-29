package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServicePassengerUserClient;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.result.TokenResult;
import com.dong.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserInfoByAccessToken(String accessToken){
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        // 调用远程服务，根据手机号查询用户信息
        ResponseResult responseResult = servicePassengerUserClient.getUserByPhone(phone);

        return responseResult;
    }
}
