package com.dong.serviceprice.controller;

import com.dong.internalcommon.request.CalculatePriceDTO;
import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {


    @Autowired
    private PriceService priceService;

    /**
     *  估计价格
     * @param forecastPriceDTO
     * @return
     */
    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        return priceService.forecasePrice(forecastPriceDTO);
    }

    /**
     * 计算实际价格
     * @param calculatePriceDTO
     * @return
     */
    @PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestBody CalculatePriceDTO calculatePriceDTO){
        return priceService.calculatePrice(calculatePriceDTO);
    }
}
