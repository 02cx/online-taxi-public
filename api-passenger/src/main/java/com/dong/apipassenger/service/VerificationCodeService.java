package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServiceVerificationcodeClient;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    public String generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.numberCode(6);
        System.out.println("获取远程的number code：" + numberCodeResponse.getData().getNumberCode());

        // 将验证码存在redis
        System.out.println("存在redis");

        // 返回值
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");

        return result.toString();
    }
}
