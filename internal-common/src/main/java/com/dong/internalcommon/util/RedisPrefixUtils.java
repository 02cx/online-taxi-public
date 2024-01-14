package com.dong.internalcommon.util;

public class RedisPrefixUtils {

    // 乘客验证码key
    private static String verificationCodePrefix = "verification_code_";
    // token key
    private static String tokenPrefix = "token_";

    /**
     * 生成redis的key
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone,String identity){
        return verificationCodePrefix + identity + "_" + passengerPhone;
    }

    /**
     * 生成token对应的redis key
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByToken(String passengerPhone,String identity,String tokenType){
        return tokenPrefix + passengerPhone + "-"  +identity + "-" + tokenType;
    }
}
