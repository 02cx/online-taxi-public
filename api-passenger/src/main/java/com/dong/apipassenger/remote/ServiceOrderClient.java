package com.dong.apipassenger.remote;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 生成订单----乘客端传递参数
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/add")
    public ResponseResult add(@RequestBody OrderDTO orderDTO);
}
