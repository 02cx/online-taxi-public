package com.dong.apidriver.controller;

import com.dong.apidriver.service.DriverUserService;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;


    @PutMapping("/update-driver-user")
    public ResponseResult updateUriverUser(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.modifyDriverUser(driverUserDTO);
    }


}
