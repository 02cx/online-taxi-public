package com.dong.servicemap.service;

import com.dong.internalcommon.request.AroundSearchTerminalDTO;
import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    /**
     *  新增终端
     * @param terminalDTO
     * @return
     */
    public ResponseResult<TerminalResponse> addTerminal(TerminalDTO terminalDTO){

        String tid = terminalClient.addService(terminalDTO.getName(),terminalDTO.getDesc());
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    /**
     *  周边搜索终端
     * @param aroundSearchTerminalDTO
     * @return
     */
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(AroundSearchTerminalDTO aroundSearchTerminalDTO){
        return terminalClient.aroundSearchTerminal(aroundSearchTerminalDTO.getCenter(),aroundSearchTerminalDTO.getRadius());
    }
}
