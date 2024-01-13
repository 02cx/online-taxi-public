package com.dong.servicedriveruser.service;

import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     *  新增 司机用户
     * @return
     */
    public ResponseResult addDriverUser(DriverUserDTO driverUserDTO){
        DriverUser driverUser = new DriverUser();
        BeanUtils.copyProperties(driverUserDTO,driverUser);
        driverUserMapper.insert(driverUser);
        return ResponseResult.success();
    }

    /**
     *  根据司机用户id修改信息
     * @param driverUserDTO
     * @return
     */
    public ResponseResult updateDriverUser(DriverUserDTO driverUserDTO){
        DriverUser driverUser = new DriverUser();
        BeanUtils.copyProperties(driverUserDTO,driverUser);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }
}
