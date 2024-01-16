package com.dong.servicedriveruser.remote;

import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.request.TrackDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.response.TrackResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {


    @PostMapping("/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestBody TerminalDTO terminalDTO);

    @PostMapping("/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestBody TrackDTO trackDTO);
}
