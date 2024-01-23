package com.dong.servicedriveruser.service;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     * 根据城市编码查询城市是否存在可用司机
     * @param cityCode
     * @return
     */
    public ResponseResult<Boolean> isAvailableDriver(String cityCode){
        Integer availableDriver = driverUserMapper.isAvailableDriver(cityCode);
        if(availableDriver > 0){
            return ResponseResult.success(true);
        }
        return ResponseResult.success(false);
    }
}
