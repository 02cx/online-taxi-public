package com.dong.servicemap.remote;

import com.dong.internalcommon.constant.AmapConfigConstant;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.response.TrsearchResponse;
import com.dong.internalcommon.result.ResponseResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TerminalClient {
    @Value("${amap.key}")
    private String key;
    @Value("${amap.sid}")
    private String sid;
    @Autowired
    private RestTemplate restTemplate;

    /**
     *  新增终端
     * @param name
     * @param desc
     * @return
     */
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

    /**
     *  周边终端搜索
     * @param center
     * @param radius
     * @return
     */
    public ResponseResult<List<TerminalResponse>> aroundSearchTerminal(String center,Integer radius){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.AROUND_SEARCH_URL);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("sid=" + sid);
        urlBuilder.append("&");
        urlBuilder.append("center=" + center);
        urlBuilder.append("&");
        urlBuilder.append("radius=" + radius);

        System.out.println("周边终端搜索url：" + urlBuilder.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(urlBuilder.toString(), null, String.class);
        String body = stringResponseEntity.getBody();
        System.out.println("周边终端搜索响应：" + body);

        // 解析body
        JSONObject jsonObject = JSONObject.fromObject(body);

        List<TerminalResponse> list = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("results");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject result = jsonArray.getJSONObject(i);
            String name = result.getString("name");

            //WYD TODO 2024-01-26: Long carId = result.getLong("desc");   直接用getLong获取可能出现BUG
            String carIdStr = result.getString("desc");
            Long carId = Long.parseLong(carIdStr);
            Integer tid = result.getInt("tid");

            // 获取司机去接乘客时的经纬度
            JSONObject location = result.getJSONObject("location");
            String latitude = location.getString("latitude");
            String longitude = location.getString("longitude");

            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setName(name);
            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid + "");
            terminalResponse.setLatitude(latitude);
            terminalResponse.setLongitude(longitude);
            list.add(terminalResponse);
        }

        return ResponseResult.success(list);
    }

    /**
     *  查询轨迹信息
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    public ResponseResult<TrsearchResponse> terminalTrsearch(String tid, Long starttime, Long endtime){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstant.TERMINAL_TRSEARCH);
        urlBuilder.append("?");
        urlBuilder.append("key=" + key);
        urlBuilder.append("&");
        urlBuilder.append("sid=" + sid);
        urlBuilder.append("&");
        urlBuilder.append("tid=" + tid);
        urlBuilder.append("&");
        urlBuilder.append("starttime=" + starttime);
        urlBuilder.append("&");
        urlBuilder.append("endtime=" + endtime);

        System.out.println("查询轨迹信息URL：" + urlBuilder.toString());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        System.out.println("查询轨迹信息响应信息：" + forEntity.getBody());

        JSONObject jsonObject = JSONObject.fromObject(forEntity.getBody());
        JSONObject data = jsonObject.getJSONObject("data");
        int counts = data.getInt("counts");
        if(counts == 0){
            return ResponseResult.fail("轨迹条数为0");
        }

        Long driveMile = 0L;
        Long driveTime = 0L;
        JSONArray tracks = data.getJSONArray("tracks");
        for (int i = 0; i < tracks.size(); i++) {
            JSONObject track = tracks.getJSONObject(i);
            int distance = track.getInt("distance");
            driveMile += distance;

            int time = track.getInt("time");
            driveTime += time / (60 * 1000);
        }

        TrsearchResponse trsearchResponse = new TrsearchResponse();
        trsearchResponse.setDriveMile(driveMile);
        trsearchResponse.setDriveTime(driveTime);
        return ResponseResult.success(trsearchResponse);
    }
}
