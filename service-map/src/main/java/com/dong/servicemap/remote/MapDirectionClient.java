package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import com.dong.internalcommon.response.DirectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDirectionClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    /**
     *  真正的调用高德地图的api
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public DirectionResponse direction(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        /**
         * ?
         * &extensions=base
         * &output=json&key=8b584607cf3806da46d0e29e4e4d8453
         */
        // 组装请求调用url
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&");
        urlBuilder.append("extensions=base");
        urlBuilder.append("&");
        urlBuilder.append("output=json");
        urlBuilder.append("&");
        urlBuilder.append("key=" + key);
        // 发起调用
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);

        System.out.println(directionEntity.getBody());
        return null;
    }
}
