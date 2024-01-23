package com.dong.servicedriveruser.controller;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/city_driver")
public class CityDriverController {
    @Autowired
    private CityDriverUserService cityDriverUserService;

    /**
     * 根据城市编码判断城市中是否有可用司机
     * @param cityCode
     * @return
     */
    @GetMapping("/is_driver_available")
    public ResponseResult<Boolean> isDriverAvailable(@RequestParam String cityCode){
        return cityDriverUserService.isAvailableDriver(cityCode);
    }
}
