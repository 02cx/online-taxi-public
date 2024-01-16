package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import com.dong.internalcommon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrackClient {
    @Value("${amap.key}")
    private String key;
    @Value("${amap.sid}")
    private String sid;
    @Autowired
    private RestTemplate restTemplate;

    public TrackResponse addService(String tid){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.TRACK_URL);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("sid=" + sid);
        urlBuilder.append("&");
        urlBuilder.append("tid=" + tid);
        ResponseEntity<String> service = restTemplate.postForEntity(urlBuilder.toString(),null, String.class);
        String body = service.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String trid = data.getString("trid");
        String trname = "";
        if(data.has("trname")){
            trname = data.getString("trname");
        }
        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setName(trname);
        return trackResponse;
    }

}
