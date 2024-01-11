package com.dong.apipassenger.remote;

import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-price")
public interface ServicePriceClient {

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
