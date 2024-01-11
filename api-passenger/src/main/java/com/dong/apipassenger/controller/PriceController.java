package com.dong.apipassenger.controller;

import com.dong.apipassenger.service.PriceService;
import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        return priceService.forecasePrice(forecastPriceDTO);
    }
}
