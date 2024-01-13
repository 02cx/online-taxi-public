package com.dong.apiboss.remote;

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
}
