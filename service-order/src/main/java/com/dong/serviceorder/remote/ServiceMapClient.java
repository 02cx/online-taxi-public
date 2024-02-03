package com.dong.serviceorder.remote;

import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.response.TrsearchResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     *  周边搜索终端
     * @param aroundSearchTerminalDTO
     * @return
     */
    @PostMapping("/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(@RequestBody AroundSearchTerminalDTO aroundSearchTerminalDTO);


    /**
     * 查询轨迹信息
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    @PostMapping("/terminal/trsearch")
    public ResponseResult<TrsearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime,@RequestParam Long endtime);
}
