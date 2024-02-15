package com.dong.apipassenger.service;

import com.dong.apipassenger.remote.ServiceOrderClient;
import com.dong.internalcommon.constant.IdentityConstant;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class OrderService {
    @Autowired
    private ServiceOrderClient serviceOrderClient;

    /**
     * 生成订单
     * @param orderDTO
     * @return
     */
    public ResponseResult generatorOrder(OrderDTO orderDTO){
        return serviceOrderClient.add(orderDTO);
    }


    /**
     * 乘客取消订单
     * @param orderId 订单id
     * @return
     */
    public ResponseResult cancel( Long orderId){
        return serviceOrderClient.cancel(orderId, IdentityConstant.PASSENGER_IDENTITY);
    }
}
