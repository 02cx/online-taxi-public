package com.dong.servicemap.service;

import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult addTerminal(String name,String sid){
        String tid = terminalClient.addService(name, sid);
        return ResponseResult.success(new TerminalResponse(tid));
    }
}
