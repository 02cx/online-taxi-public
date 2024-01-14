package com.dong.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUserWorkStatus;
import com.dong.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DriverUserWorkStatusService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeWorkStatus(Long driverId,Integer status){
        LambdaQueryWrapper<DriverUserWorkStatus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverUserWorkStatus::getDriverId,driverId);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(wrapper);

        driverUserWorkStatus.setWorkStatus(status);
        driverUserWorkStatus.setGmtModified(LocalDateTime.now());
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        return ResponseResult.success();
    }
}
