package com.dong.serviceorder.service;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceorder.domain.OrderInfo;
import com.dong.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public ResponseResult addOrderInfo(OrderDTO orderDTO){
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderDTO,orderInfo);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success();
    }
}
