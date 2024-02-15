package com.dong.apipassenger.remote;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-order")
public interface ServiceOrderClient {

    /**
     * 生成订单----乘客端传递参数
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/add")
    public ResponseResult add(@RequestBody OrderDTO orderDTO);

    @GetMapping("/test-real-time-order/{orderId}")
    public String testRealTimeOrder(@PathVariable Long orderId);

    /**
     * 取消订单
     * @param orderId 订单id
     * @param identity 用户身份 乘客1  司机2
     * @return
     */
    @PostMapping("/order/cancel")
    public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity);
}
