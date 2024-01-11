package com.dong.apipassenger.remote;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-verificationcode")
public interface ServiceVerificationcodeClient {

    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") Integer size);
}
