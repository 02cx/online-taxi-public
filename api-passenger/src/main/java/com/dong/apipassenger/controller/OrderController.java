package com.dong.apipassenger.controller;

import com.dong.apipassenger.service.OrderService;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderDTO orderDTO){
        System.out.println(orderDTO);
        return orderService.generatorOrder(orderDTO);
    }

    /**
     * 取消订单
     * @param orderId 订单id
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId){
        return orderService.cancel(orderId);
    }
}
