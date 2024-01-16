package com.dong.servicemap.service;

import com.dong.internalcommon.request.UploadPointDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult uploadPoint(UploadPointDTO uploadPointDTO){
        return pointClient.uploadPoint(uploadPointDTO);
    }

}
