package com.dong.apiboss.controller;

import com.dong.apiboss.service.DriverCarBindService;
import com.dong.internalcommon.request.DriverCarBindingDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverCarBindController {

    @Autowired
    private DriverCarBindService driverCarBindService;

    @PostMapping("/bind")
    public ResponseResult bindDriverCar(@RequestBody DriverCarBindingDTO driverCarBindingDTO){
        return driverCarBindService.bindDriverCar(driverCarBindingDTO);
    }

    @PostMapping("/unbind")
    public ResponseResult unbindDriverCar(@RequestBody DriverCarBindingDTO driverCarBindingDTO){
        return driverCarBindService.unbindDriverCar(driverCarBindingDTO);
    }
}
