package com.dong.apidriver.remote;

import com.dong.internalcommon.request.OrderDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface ServiceOrderClient {
    /**
     * 司机去接乘客
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderDTO orderDTO);

    /**
     * 司机到达乘客上车点
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/driver-arrived-departure")
    public ResponseResult driverArrivedDeparture(@RequestBody OrderDTO orderDTO);

    /**
     * 乘客上车
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderDTO orderDTO);

    /**
     * 乘客到达目的地，下车
     * @param orderDTO
     * @return
     */
    @PostMapping("/order/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderDTO orderDTO);
}
