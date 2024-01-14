package com.dong.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/authTest")
    public String auth(){
        return "auth";
    }

    @GetMapping("/no-noauthTest")
    public String noauth(){
        return "no-auth";
    }
}
