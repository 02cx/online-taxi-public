package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/car")
    public ResponseResult car(@RequestBody CarDTO carDTO){
        return carService.insertCar(carDTO);
    }
}
