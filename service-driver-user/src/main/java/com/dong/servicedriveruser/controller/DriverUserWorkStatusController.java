package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.request.WorkStatusDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUserWorkStatus;
import com.dong.servicedriveruser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserWorkStatusController {

    @Autowired
    private DriverUserWorkStatusService driverUserWorkStatusService;


    @PostMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody WorkStatusDTO workStatusDTO){
        return driverUserWorkStatusService.changeWorkStatus(workStatusDTO.getDriverId(), workStatusDTO.getStatus());
    }
}
