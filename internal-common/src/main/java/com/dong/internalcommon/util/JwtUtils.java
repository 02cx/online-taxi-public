package com.dong.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dong.internalcommon.constant.IdentityConstant;
import com.dong.internalcommon.constant.TokenConstant;
import com.dong.internalcommon.result.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    // 盐
    private static final String SIGN = "wuyadong";

    private static final String JWT_KEY_PHONE = "phone";
    // 先假定乘客是1 司机是2
    private static final String JWT_KEY_IDENTITY = "identity";
    // token类型
    private static final String JWT_KEY_TOKEN_TYPE = "token_type";
    // 时间戳
    private static final String TOKEN_TIME = "tokenTime";


    // 生成token
    public static String generatorToken(String passengerPhone,String identity,String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_KEY_TOKEN_TYPE,tokenType);
        map.put(TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();

        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // 整合过期时间
        //builder.withExpiresAt(date);
        // 生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));

        return token;
    }

    // 解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        String tokenType = verify.getClaim(JWT_KEY_TOKEN_TYPE).asString();

        TokenResult tokenResult = TokenResult.builder().phone(phone).identity(identity).tokenType(tokenType).build();
        return tokenResult;
    }

    // 校验token
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try{
            tokenResult = JwtUtils.parseToken(token);
        }catch (Exception exception){

        }
        return tokenResult;
    }


    public static void main(String[] args) {
        String token = generatorToken("19882012313", IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        System.out.println("生成token：" + token);
        TokenResult tokenResult = parseToken(token);
        System.out.println("解析token：" + tokenResult.toString());
    }

}





































