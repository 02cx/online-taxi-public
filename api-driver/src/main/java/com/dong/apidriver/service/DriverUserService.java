package com.dong.apidriver.service;

import com.dong.apidriver.remote.ServiceDriverUserClient;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.response.CheckDriverUserResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;


    /**
     *  远程到用service-driver-user，新增司机用户
     * @param driverUserDTO
     * @return
     */
    public ResponseResult modifyDriverUser(DriverUserDTO driverUserDTO){
        return serviceDriverUserClient.updateUser(driverUserDTO);
    }


}
