package com.dong.apidriver.service;

import com.dong.apidriver.remote.ServiceDriverUserClient;
import com.dong.apidriver.remote.ServiceVerificationCodeClient;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.constant.IdentityConstant;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.internalcommon.response.CheckDriverUserResponse;
import com.dong.internalcommon.response.NumberCodeResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public ResponseResult sendVerificationCode(VerificationCodeDTO verificationCodeDTO){
        // 调用service-driver-user 根据手机号验证司机用户
        ResponseResult<CheckDriverUserResponse> checkResponseResult = serviceDriverUserClient.checkDriver(verificationCodeDTO);
        CheckDriverUserResponse checkDriverUserResponse =  checkResponseResult.getData();
        if(checkDriverUserResponse.getIfExists() == DriverCarConstant.DRIVER_STATE_INVALID){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_USER_NOT_EXISTS);
        }
        // 调用service-verificationcode  生成验证码
        //WYD TODO 2024-01-14: 验证码位数写死在代码中
        ResponseResult<NumberCodeResponse> verificationCodeResult = serviceVerificationCodeClient.numberCode(6);
        Integer verificationCode = verificationCodeResult.getData().getNumberCode();

        // 使用第三方短信服务，向用户手机上发送验证码
        log.info(verificationCodeDTO.getDriverPhone() + "---->" + verificationCode);
        // 验证码存入redis
        String verificationCodeKey = RedisPrefixUtils.generatorKeyByPhone(verificationCodeDTO.getDriverPhone(), IdentityConstant.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(verificationCodeKey,verificationCode + "",3, TimeUnit.MINUTES);

        return ResponseResult.success();
    }

}
