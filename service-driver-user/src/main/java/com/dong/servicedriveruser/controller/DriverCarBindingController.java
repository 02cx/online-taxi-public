package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.request.DriverCarBindingDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.service.DriverCarBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverCarBindingController {

    @Autowired
    private DriverCarBindingService driverCarBindingService;


    @PostMapping("/driver-car-binding-relation")
    public ResponseResult driverCarBindingRelation(@RequestBody DriverCarBindingDTO driverCarBindingDTO){
        return driverCarBindingService.driverCarBinding(driverCarBindingDTO);
    }

}
