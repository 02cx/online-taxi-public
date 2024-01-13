package com.dong.servicedriveruser.service;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     *  新增 司机用户
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser){
        driverUserMapper.insert(driverUser);
        return ResponseResult.success();
    }
}
