package com.dong.internalcommon.response;

import lombok.Data;

@Data
public class TrsearchResponse {

    // 载客里程（米）
    private Long driveMile;
    // 载客时间(分)
    private Long driveTime;
}
