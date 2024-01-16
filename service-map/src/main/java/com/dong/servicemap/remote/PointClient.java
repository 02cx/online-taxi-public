package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import com.dong.internalcommon.request.PointsDTO;
import com.dong.internalcommon.request.UploadPointDTO;
import com.dong.internalcommon.result.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class PointClient {

    @Value("${amap.key}")
    private String key;
    @Value("${amap.sid}")
    private String sid;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult uploadPoint(UploadPointDTO uploadPointDTO){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.UPLOAD_POINT_URL);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("sid=" + sid);
        urlBuilder.append("&");
        urlBuilder.append("tid=" + uploadPointDTO.getTid());
        urlBuilder.append("&");
        urlBuilder.append("trid=" + uploadPointDTO.getTrid());
        urlBuilder.append("&");
        urlBuilder.append("points=");
        urlBuilder.append("%5b");
        PointsDTO[] pointsDTOS = uploadPointDTO.getPoints();

        for(PointsDTO point : pointsDTOS){
            urlBuilder.append("%7b");
            urlBuilder.append("%22location%22");
            urlBuilder.append("%3a");
            urlBuilder.append("%22" + point.getLocation() + "%22");
            urlBuilder.append("%2c");
            urlBuilder.append("%22locatetime%22");
            urlBuilder.append("%3a");
            urlBuilder.append(point.getLocatetime());
            urlBuilder.append("%7d");
        }
        urlBuilder.append("%5d");

        System.out.println("高德地图请求地址："  + urlBuilder.toString());
        ResponseEntity<String> service = restTemplate.postForEntity(URI.create(urlBuilder.toString()),null, String.class);
        System.out.println("高德地图返回信息：" + service.getBody());
        return ResponseResult.success();
    }
}
