package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.Car;
import com.dong.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/car")
    public ResponseResult car(@RequestBody CarDTO carDTO){
        return carService.insertCar(carDTO);
    }

    @GetMapping("/car")
    public ResponseResult<CarDTO> getCarById( @RequestParam Long carId){
        return carService.getCarById(carId);
    }
}
