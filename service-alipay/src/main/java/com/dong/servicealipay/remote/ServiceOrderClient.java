package com.dong.servicealipay.remote;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 支付成功
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/pay")
    public ResponseResult pay(@RequestBody OrderDTO orderDTO);
}
