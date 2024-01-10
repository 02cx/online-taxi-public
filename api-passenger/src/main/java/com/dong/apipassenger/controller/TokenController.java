package com.dong.apipassenger.controller;

import com.dong.apipassenger.service.TokenService;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponseSrc){
        return tokenService.refreshToken(tokenResponseSrc);
    }
}
