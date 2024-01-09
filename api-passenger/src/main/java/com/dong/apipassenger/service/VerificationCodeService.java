package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServiceVerificationcodeClient;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
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

    public ResponseResult generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.numberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        // 将验证码存在redis
        String key = verificationCodePrefix + passengerPhone;
        stringRedisTemplate.opsForValue().set(key,numberCode + "",1, TimeUnit.MINUTES);
        // 调用第三方短信服务商接口，发送短信

        // 返回值
        return ResponseResult.success("");
    }
}
