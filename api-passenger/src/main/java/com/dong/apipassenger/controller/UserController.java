package com.dong.apipassenger.controller;

import com.dong.apipassenger.service.UserService;
import com.dong.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ResponseResult users(HttpServletRequest request){
        // 从请求头中获取accessToken
        String accessToken = request.getHeader("Authorization");

        return userService.getUserInfoByAccessToken(accessToken);
    }
}
