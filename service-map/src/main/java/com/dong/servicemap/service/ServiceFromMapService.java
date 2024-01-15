package com.dong.servicemap.service;

import com.dong.internalcommon.response.ServiceResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceClient serviceClient;

    /**
     *  创建指定名字的服务
     * @param name
     * @return
     */
    public ResponseResult addService(String name){
        String sid = serviceClient.addService(name);

        return ResponseResult.success( new ServiceResponse(sid));
    }
}
