package com.dong.servicemap.controller;

import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    /**
     * 新增终端  name-车牌号   desc--car的id
     * @param terminalDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestBody TerminalDTO terminalDTO){
        return terminalService.addTerminal(terminalDTO);
    }

    /**
     *  周边搜索终端
     * @param aroundSearchTerminalDTO
     * @return
     */
    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(@RequestBody AroundSearchTerminalDTO aroundSearchTerminalDTO){
        ResponseResult<List<TerminalResponse>> listResponseResult = terminalService.aroundSearchTerminal(aroundSearchTerminalDTO);
        return listResponseResult;
    }

    /**
     * 查询轨迹信息
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    @GetMapping("/trsearch")
    public ResponseResult trsearch(String tid,Long starttime,Long endtime){
        return terminalService.trsearch(tid,starttime,endtime);
    }

}
