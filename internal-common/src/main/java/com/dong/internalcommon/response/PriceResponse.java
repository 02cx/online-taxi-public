package com.dong.internalcommon.response;

import lombok.Data;

@Data
public class PriceResponse {

    private Double forecasePrice;

    private Double actualPrice;

    private String cityCode;

    private String VehicleType;
}
