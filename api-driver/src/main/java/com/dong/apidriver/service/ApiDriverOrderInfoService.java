package com.dong.apidriver.service;

import com.dong.apidriver.remote.ServiceOrderClient;
import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ApiDriverOrderInfoService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    /**
     * 司机去接乘客
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderDTO orderDTO) {
        return serviceOrderClient.toPickUpPassenger(orderDTO);
    }

    /**
     * 司机到达乘客上车点
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult driverArrivedDeparture(OrderDTO orderDTO) {
        return serviceOrderClient.driverArrivedDeparture(orderDTO);
    }

    /**
     * 乘客上车
     *
     * @param orderDTO
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(OrderDTO orderDTO) {
        return serviceOrderClient.pickUpPassenger(orderDTO);
    }

    /**
     * 乘客到达目的地，下车
     *
     * @param orderDTO
     * @return
     */
    public ResponseResult passengerGetoff(OrderDTO orderDTO) {
        return serviceOrderClient.passengerGetoff(orderDTO);
    }
}
