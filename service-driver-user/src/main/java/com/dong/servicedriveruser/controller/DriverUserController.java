package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.internalcommon.response.CheckDriverUserResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.addDriverUser(driverUserDTO);
    }

    @PutMapping("/update-user")
    public ResponseResult updateUser(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.updateDriverUser(driverUserDTO);
    }

    @GetMapping("/check-driver")
    public ResponseResult<CheckDriverUserResponse> checkDriver(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String driverPhone = verificationCodeDTO.getDriverPhone();
        ResponseResult responseResult = driverUserService.checkDriver(driverPhone);
        DriverUser driverUser = (DriverUser) responseResult.getData();
        if(ObjectUtils.isEmpty(driverUser)){
            CheckDriverUserResponse driverUserResponse = CheckDriverUserResponse.builder()
                    .driverPhone(driverPhone).ifExists(DriverCarConstant.DRIVER_STATE_INVALID).build();
            return responseResult.setData(driverUserResponse);
        }

        CheckDriverUserResponse driverUserResponse = CheckDriverUserResponse.builder()
                .driverPhone(driverPhone).ifExists(DriverCarConstant.DRIVER_STATE_VALID).build();
        return responseResult.setData(driverUserResponse);

    }
}
