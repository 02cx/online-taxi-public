package com.dong.apidriver.remote;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.internalcommon.response.CheckDriverUserResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @PostMapping("/user")
    public ResponseResult user(@RequestBody DriverUserDTO driverUserDTO);

    @PutMapping("/update-user")
    public ResponseResult updateUser(@RequestBody DriverUserDTO driverUserDTO);

    @GetMapping("/check-driver")
    public ResponseResult<CheckDriverUserResponse> checkDriver(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @GetMapping("/car")
    public ResponseResult<CarDTO> getCarById(@RequestParam Long carId);
}

