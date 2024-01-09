package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServiceVerificationcodeClient;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.response.NumberCodeResponse;
import com.dong.internalcommon.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private String verificationCodePrefix = "passenger_verification_code_";

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *  生成验证码
     * @param passengerPhone 乘客手机号
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        // 将验证码存在redis
        String key = generatorKeyByPhone(passengerPhone);
        stringRedisTemplate.opsForValue().set(key,numberCode + "",1, TimeUnit.MINUTES);
        // 调用第三方短信服务商接口，发送短信

        // 返回值
        return ResponseResult.success("");
    }

    /**
     * 生成redis的key
     * @param passengerPhone
     * @return
     */
    public String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    /**
     *  校验验证码
     * @param passengerPhone 乘客手机号
     * @param verificationCode  验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone,String verificationCode){
        // 根据用户手机号去redis查询验证码
        String key = generatorKeyByPhone(passengerPhone);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);

        // 校验验证码
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.success(CommonStatusEnum.VERIFICATION_CODE_EXPIRED.getCode(),CommonStatusEnum.VERIFICATION_CODE_EXPIRED.getMessage());
        }
        if(!codeRedis.trim().equals(verificationCode.trim())){
            return ResponseResult.success(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }

        // 判断之前是否有用户，进行对应的操作

        // 颁发token

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }
































}
