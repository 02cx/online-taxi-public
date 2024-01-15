package com.dong.servicemap.controller;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceFromMapController {
    @Autowired
    ServiceFromMapService serviceFromMapService;


    @PostMapping("/add")
    public ResponseResult addService(@RequestParam String name){
        return serviceFromMapService.addService(name);
    }
}
