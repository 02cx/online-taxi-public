package com.dong.apidriver.remote;

import com.dong.internalcommon.request.TrackDTO;
import com.dong.internalcommon.request.UploadPointDTO;
import com.dong.internalcommon.response.TrackResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-map")
public interface ServiceMapClient {
    @PostMapping("/point/upload")
    public ResponseResult upload(@RequestBody UploadPointDTO uploadPointDTO);
}
