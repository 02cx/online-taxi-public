package com.dong.servicedriveruser.service;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.Car;
import com.dong.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    public ResponseResult insertCar(CarDTO carDTO){
        Car car = new Car();
        BeanUtils.copyProperties(carDTO,car);
        carMapper.insert(car);

        return ResponseResult.success();
    }
}
