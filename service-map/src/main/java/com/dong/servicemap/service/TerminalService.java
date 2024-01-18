package com.dong.servicemap.service;

import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> addTerminal(TerminalDTO terminalDTO){

        String tid = terminalClient.addService(terminalDTO.getName(),terminalDTO.getDesc());
        return ResponseResult.success(new TerminalResponse(tid));
    }
}
