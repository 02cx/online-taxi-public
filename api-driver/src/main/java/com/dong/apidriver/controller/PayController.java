package com.dong.apidriver.controller;

import com.dong.apidriver.service.PayService;
import com.dong.internalcommon.request.PushPayPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 司机向乘客推送支付价格
     * @param pushPayPriceDTO
     * @return
     */
    @PostMapping("/push-pay-price")
    public ResponseResult pushPayPrice(@RequestBody PushPayPriceDTO pushPayPriceDTO){
        return payService.pushPayPrice(pushPayPriceDTO);
    }
}
