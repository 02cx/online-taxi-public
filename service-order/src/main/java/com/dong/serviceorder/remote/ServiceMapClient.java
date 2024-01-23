package com.dong.serviceorder.remote;

import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    @PostMapping("/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(@RequestBody AroundSearchTerminalDTO aroundSearchTerminalDTO);
}
