package com.dong.ssedriverclientweb.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class SseController {

    public static HashMap<String,SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param driverId
     * @return
     */
    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId){
        System.out.println("司机ID：" + driverId);

        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitterMap.put(driverId,sseEmitter);
        return sseEmitter;
    }


    /**
     * 推送消息
     * @param driverId
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam String driverId, @RequestParam String content){
        try {
            if (sseEmitterMap.containsKey(driverId)) {
                sseEmitterMap.get(driverId).send(content);
            }else{
                return "推送失败";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "给用户：" + driverId + "发送了消息：" + content;
    }

    /**
     * 关闭连接
     * @param driverId
     * @return
     */
    @GetMapping("/close/{driverId}")
    public String close(@PathVariable String driverId){
        System.out.println("关闭连接：" + driverId);
        if (sseEmitterMap.containsKey(driverId)) {
            sseEmitterMap.remove(driverId);
        }

        return "关闭成功：" + driverId;
    }
}
