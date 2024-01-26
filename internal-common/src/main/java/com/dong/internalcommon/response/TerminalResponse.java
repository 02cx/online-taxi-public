package com.dong.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalResponse {
    private String tid;

    private String name;

    private Long carId;

    // 车辆去接乘客时的经纬度
    private String latitude;
    private String longitude;
}
