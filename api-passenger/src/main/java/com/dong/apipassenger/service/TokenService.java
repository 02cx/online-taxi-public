package com.dong.apipassenger.service;

import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.TokenConstant;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.result.TokenResult;
import com.dong.internalcommon.response.TokenResponse;
import com.dong.internalcommon.util.JwtUtils;
import com.dong.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(TokenResponse tokenResponseSrc){
        String refreshTokenSrc = tokenResponseSrc.getRefreshToken();
        // 解析refreshToken
        TokenResult tokenResult = JwtUtils.parseToken(refreshTokenSrc);
        if(tokenResult == null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        // 在redis中查询refreshToken
        String refreshTokenKey = RedisPrefixUtils.generatorKeyByToken(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
        String redisRefreshToken = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        // 校验refreshToken
        if((StringUtils.isBlank(redisRefreshToken)) || (!refreshTokenSrc.trim().equals(redisRefreshToken.trim()))){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
        }

        // 生成新的双token
        String accessTokenKey = RedisPrefixUtils.generatorKeyByToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
        String accessToken = JwtUtils.generatorToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);


        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }
}
