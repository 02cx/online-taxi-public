package com.dong.apidriver.service;

import com.dong.apidriver.remote.ServiceDriverUserClient;
import com.dong.apidriver.remote.ServiceMapClient;
import com.dong.internalcommon.request.ApiDriverUploadTrackDTO;
import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.request.UploadPointDTO;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult uploadTrack(ApiDriverUploadTrackDTO trackDTO){
        // 获取carId
        ResponseResult<CarDTO> carDTOResponseResult = serviceDriverUserClient.getCarById(trackDTO.getCarId());
        CarDTO car = carDTOResponseResult.getData();
        // 根据carId获取tid、trid
        String tid = car.getTid();
        String trid = car.getTrid();
        // 上传轨迹点
        UploadPointDTO uploadPointDTO = new UploadPointDTO();
        uploadPointDTO.setTid(tid);
        uploadPointDTO.setTrid(trid);
        uploadPointDTO.setPoints(trackDTO.getPoints());

        serviceMapClient.upload(uploadPointDTO);

        return ResponseResult.success();
    }
}
