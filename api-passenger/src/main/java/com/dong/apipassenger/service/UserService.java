package com.dong.apipassenger.service;

import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.dto.TokenResult;
import com.dong.internalcommon.util.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult getUserInfoByAccessToken(String accessToken){
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        // 调用远程服务，根据手机号查询用户信息


        return ResponseResult.success(phone);
    }
}
