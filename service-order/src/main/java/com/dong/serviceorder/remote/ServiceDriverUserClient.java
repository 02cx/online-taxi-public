package com.dong.serviceorder.remote;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.response.OrderDriverResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 根据城市编码判断城市中是否有可用司机
     * @param cityCode
     * @return
     */
    @GetMapping("/city_driver/is_driver_available")
    public ResponseResult<Boolean> isDriverAvailable(@RequestParam String cityCode);

    /**
     * 根据carId查询创建订单所需要的司机信息-----司机ID，司机Phone
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable Long carId);

    /**
     *  根据车辆id查询车辆信息
     * @param carId
     * @return
     */
    @GetMapping("/car")
    public ResponseResult<CarDTO> getCarById(@RequestParam Long carId);
}
