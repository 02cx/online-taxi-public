package com.dong.apiboss.service;

import com.dong.apiboss.remote.ServiceDriverUserClient;
import com.dong.internalcommon.request.DriverCarBindingDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DriverCarBindService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult bindDriverCar(DriverCarBindingDTO driverCarBindingDTO){
        return serviceDriverUserClient.driverCarBindingRelation(driverCarBindingDTO);
    }

    public ResponseResult unbindDriverCar(DriverCarBindingDTO driverCarBindingDTO){
        return serviceDriverUserClient.driverCarUnbindingRelation(driverCarBindingDTO);
    }
}
