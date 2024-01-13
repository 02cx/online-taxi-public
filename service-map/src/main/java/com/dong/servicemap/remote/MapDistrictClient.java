package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDistrictClient {
    @Value("${amap.key}")
    private String key;
    @Autowired
    private RestTemplate restTemplate;


    public String district(String keywords){
        /**
         * https://restapi.amap.com/v3/config/district?
         * keywords=%E4%B8%AD%E5%9B%BD&
         * subdistrict=2&key=8b584607cf3806da46d0e29e4e4d8453
         */
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.DISTRICT_URL);
        urlBuilder.append("?");
        urlBuilder.append("keywords=" + keywords);
        urlBuilder.append("&");
        urlBuilder.append("subdistrict=3");
        urlBuilder.append("&");
        urlBuilder.append("key=" + key);

        ResponseEntity<String> districtEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        return districtEntity.getBody();
    }


}
