package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.request.VerificationCodeDTO;
import com.dong.internalcommon.response.CheckDriverUserResponse;
import com.dong.internalcommon.response.OrderDriverResponse;
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

    /**
     *  新增司机用户
     * @param driverUserDTO
     * @return
     */
    @PostMapping("/user")
    public ResponseResult user(@RequestBody DriverUserDTO driverUserDTO){
        return driverUserService.addDriverUser(driverUserDTO);
    }

    /**
     *  跟新司机用户信息
     * @param driverUserDTO
     * @return
     */
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

    /**
     * 根据carId查询创建订单所需要的司机信息-----司机ID，司机Phone
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable Long carId){
        return driverUserService.getAvailableDriver(carId);
    }
}
