package com.dong.servicessepush.controller;

import com.dong.internalcommon.util.SsePrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin
@Slf4j
public class SseController {

    public static HashMap<String,SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param userId
     * @param identity
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId,@RequestParam String identity){
        String mapKey = SsePrefixUtils.generatorsKey(userId, identity);
        log.info("用户id：" + userId);

        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitterMap.put(mapKey,sseEmitter);
        return sseEmitter;
    }


    /**
     * 推送消息
     * @param userId
     * @param identity
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam Long userId,@RequestParam String identity,@RequestParam String content){
        String mapKey = SsePrefixUtils.generatorsKey(userId, identity);
        try {
            if (sseEmitterMap.containsKey(mapKey)) {
                sseEmitterMap.get(mapKey).send(content);
            }else{
                return "推送失败";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "给用户：" + mapKey + "发送了消息：" + content;
    }

    /**
     * 关闭连接
     * @param userId
     * @param identity
     * @return
     */
    @GetMapping("/close")
    public String close(@RequestParam Long userId,@RequestParam String identity){
        String mapKey = SsePrefixUtils.generatorsKey(userId, identity);
        log.info("关闭连接：" + mapKey);
        if (sseEmitterMap.containsKey(mapKey)) {
            sseEmitterMap.remove(mapKey);
        }

        return "关闭成功：" + mapKey;
    }
}
