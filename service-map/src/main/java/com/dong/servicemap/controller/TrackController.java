package com.dong.servicemap.controller;

import com.dong.internalcommon.request.TrackDTO;
import com.dong.internalcommon.response.TrackResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/add")
    public ResponseResult<TrackResponse> addTrack(@RequestBody TrackDTO trackDTO){
        return trackService.addTrack(trackDTO.getTid());
    }
}
