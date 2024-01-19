package com.dong.serviceorder.controller;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 生成订单----乘客端传递参数
     * @param orderDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderDTO orderDTO){
        log.info(orderDTO.toString());
        return orderInfoService.addOrderInfo(orderDTO);
    }
}