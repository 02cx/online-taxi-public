package com.dong.servicemap.controller;

import com.dong.internalcommon.request.UploadPointDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody UploadPointDTO uploadPointDTO){
        return pointService.uploadPoint(uploadPointDTO);
    }
}
