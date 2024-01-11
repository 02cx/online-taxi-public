package com.dong.apipassenger.remote;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @GetMapping("/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable String phone);
}
