package com.dong.servicemap.service;

import com.dong.internalcommon.response.DirectionResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     *  根据起始地经纬度和目的地经纬度，获取距离和时间
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        // 调用高德开放平台获取距离和时间
        DirectionResponse directionResponse = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(directionResponse);
    }
}
