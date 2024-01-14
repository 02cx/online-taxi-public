package com.dong.apidriver.controller;

import com.dong.apidriver.service.DriverUserService;
import com.dong.apidriver.service.VerificationCodeService;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return verificationCodeService.sendVerificationCode(verificationCodeDTO);
    }

    @PostMapping("/check-code")
    public ResponseResult checkCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return verificationCodeService.checkCode(verificationCodeDTO.getDriverPhone(),verificationCodeDTO.getVerificationCode());
    }

}
