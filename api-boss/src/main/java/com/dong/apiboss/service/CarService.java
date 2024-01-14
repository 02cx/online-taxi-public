package com.dong.apiboss.service;

import com.dong.apiboss.remote.ServiceDriverUserClient;
import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     *  远程到用service-driver-user，新增车辆
     * @param carDTO
     * @return
     */
    public ResponseResult addCar(CarDTO carDTO){
        return serviceDriverUserClient.car(carDTO);
    }
}
