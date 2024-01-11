package com.dong.serviceprice.service;

import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.response.DirectionResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PriceService {

    @Autowired
   private ServiceMapClient serviceMapClient;

    /**
     * 估算价格
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult forecasePrice(ForecastPriceDTO forecastPriceDTO){
        // 调用地图服务，获取距离和时间
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.driving(forecastPriceDTO);
        DirectionResponse data = responseResult.getData();
        System.out.println(data.getDistance() + "-------------> " + data.getDuration());
        return ResponseResult.success();
    }
}
