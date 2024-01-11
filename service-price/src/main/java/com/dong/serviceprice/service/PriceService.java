package com.dong.serviceprice.service;

import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceService {

    public ResponseResult forecasePrice(ForecastPriceDTO forecastPriceDTO){

        log.info(forecastPriceDTO.toString());
        return ResponseResult.success();
    }
}
