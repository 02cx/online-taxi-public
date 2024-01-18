package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TerminalClient {
    @Value("${amap.key}")
    private String key;
    @Value("${amap.sid}")
    private String sid;
    @Autowired
    private RestTemplate restTemplate;

    public String addService(String name,String desc){
        //https://tsapi.amap.com/v1/track/service/add
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.TERMINAL_URL);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("sid=" + sid);
        urlBuilder.append("&");
        urlBuilder.append("name=" + name);
        urlBuilder.append("&");
        urlBuilder.append("desc=" + desc);
        System.out.println("创建终端的请求url：" + urlBuilder.toString());
        ResponseEntity<String> service = restTemplate.postForEntity(urlBuilder.toString(),null, String.class);
        System.out.println("创建终端的响应：" + service.getBody());
        String body = service.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String tid = data.getString("tid");
        return tid;
    }
}
