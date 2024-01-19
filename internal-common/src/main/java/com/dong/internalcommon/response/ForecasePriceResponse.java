package com.dong.internalcommon.response;

import lombok.Data;

@Data
public class ForecasePriceResponse {

    private Double forecasePrice;

    private String cityCode;

    private String VehicleType;
}
