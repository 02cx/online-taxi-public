package com.dong.serviceorder.remote;

import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
}
