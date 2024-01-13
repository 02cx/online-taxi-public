package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServicePriceClient;
import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceService {
    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult forecasePrice(ForecastPriceDTO forecastPriceDTO){

        ResponseResult responseResult = servicePriceClient.forecastPrice(forecastPriceDTO);

        return responseResult;
    }
}
