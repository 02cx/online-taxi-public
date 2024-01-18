package com.dong.servicemap.controller;

import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(@RequestBody AroundSearchTerminalDTO aroundSearchTerminalDTO){
        return terminalService.aroundSearchTerminal(aroundSearchTerminalDTO);
    }

}
