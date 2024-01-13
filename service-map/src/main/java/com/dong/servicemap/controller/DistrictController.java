package com.dong.servicemap.controller;

import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/dic-district")
    public ResponseResult initDistrict(String keywords){

        return districtService.initDicDistrict(keywords);
    }
}
