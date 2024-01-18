package com.dong.apidriver.controller;

import com.dong.apidriver.service.TrackService;
import com.dong.internalcommon.request.ApiDriverUploadTrackDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;


    @PostMapping("/upload-track")
    public ResponseResult uploadTrack(@RequestBody ApiDriverUploadTrackDTO trackDTO){
        return trackService.uploadTrack(trackDTO);
    }
}
