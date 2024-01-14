package com.dong.apiboss.controller;

import com.dong.apiboss.service.DriverUserService;
import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/driver-user")
    public ResponseResult driverUser(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.createDriverUser(driverUserDTO);
    }

    @PutMapping("/update-driver-user")
    public ResponseResult updateUriverUser(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.modifyDriverUser(driverUserDTO);
    }


}
