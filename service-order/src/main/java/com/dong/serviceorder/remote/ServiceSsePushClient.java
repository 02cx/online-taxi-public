package com.dong.serviceorder.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    /**
     * 推送消息
     * @param userId
     * @param identity
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content);
}
