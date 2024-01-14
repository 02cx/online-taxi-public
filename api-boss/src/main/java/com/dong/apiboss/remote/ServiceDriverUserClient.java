package com.dong.apiboss.remote;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.request.DriverCarBindingDTO;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @PostMapping("/user")
    public ResponseResult user(@RequestBody DriverUserDTO driverUserDTO);

    @PutMapping("/update-user")
    public ResponseResult updateUser(@RequestBody DriverUserDTO driverUserDTO);

    @PostMapping("/car")
    public ResponseResult car(@RequestBody CarDTO carDTO);


    @PostMapping("/driver-car-binding-relation")
    public ResponseResult driverCarBindingRelation(@RequestBody DriverCarBindingDTO driverCarBindingDTO);

    @PostMapping("/driver-car-unbinding-relation")
    public ResponseResult driverCarUnbindingRelation(@RequestBody DriverCarBindingDTO driverCarBindingDTO);

}
