package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class CalculatePriceDTO {

    // 距离
    private Integer distance;
    // 时间
    private Integer duration;
    // 城市编码
    private String cityCode;
    // 车辆类型
    private String vehicleType;
}
