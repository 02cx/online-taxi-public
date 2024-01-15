package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String key;
    @Autowired
    private RestTemplate restTemplate;

    public String addService(String name){
        //https://tsapi.amap.com/v1/track/service/add
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.SERVICE_URL);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("name=" + name);
        ResponseEntity<String> service = restTemplate.postForEntity(urlBuilder.toString(),null, String.class);
        String body = service.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String sid = data.getString("sid");
        return sid;
    }
}
