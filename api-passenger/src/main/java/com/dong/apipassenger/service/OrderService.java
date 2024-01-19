package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServiceOrderClient;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult generatorOrder(OrderDTO orderDTO){
        return serviceOrderClient.add(orderDTO);
    }
}
