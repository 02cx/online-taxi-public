package com.dong.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 盐
    private static final String SIGNEL = "wuyadong";

    // 生成token
    public static String generatorToken(Map<String,String> map){
        // token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // 整合过期时间
        builder.withExpiresAt(date);
        // 生成token
        String token = builder.sign(Algorithm.HMAC256(SIGNEL));

        return token;
    }

    // 解析token
}





































