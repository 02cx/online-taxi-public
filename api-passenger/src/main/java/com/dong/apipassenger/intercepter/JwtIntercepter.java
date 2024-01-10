package com.dong.apipassenger.intercepter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dong.internalcommon.constant.TokenConstant;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.dto.TokenResult;
import com.dong.internalcommon.util.JwtUtils;

import com.dong.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtIntercepter implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";

        String requestToken = request.getHeader("Authorization");

        TokenResult tokenResult = JwtUtils.checkToken(requestToken);

        // 获取redis中的token
        if(tokenResult == null){
            resultString = "token invalid";
            result = false;
        }else {
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorKeyByToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
            // 从redis中获取token
            String redisToken = stringRedisTemplate.opsForValue().get(tokenKey);
            if((StringUtils.isBlank(redisToken)) || (!requestToken.trim().equals(redisToken.trim()))){
                resultString = "access token invalid";
                result = false;
            }
        }


        if(!result){
            PrintWriter pw = response.getWriter();
            pw.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;

    }
}
