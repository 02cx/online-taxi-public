package com.dong.internalcommon.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForecastPriceDTO {

    // 起始地经度
    public String depLongitude;
    // 起始地纬度
    public String depLatitude;
    // 目的地经度
    public String destLongitude;
    // 目的地纬度
    public String destLatitude;

}
