package com.dong.internalcommon.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForecastPriceDTO {

    // 起始地经度
    private String depLongitude;
    // 起始地纬度
    private String depLatitude;
    // 目的地经度
    private String destLongitude;
    // 目的地纬度
    private String destLatitude;
    // 城市编码
    private String cityCode;
    // 车辆类型
    private String vehicleType;

}
