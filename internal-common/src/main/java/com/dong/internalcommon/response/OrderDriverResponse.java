package com.dong.internalcommon.response;

import lombok.Data;

@Data
public class OrderDriverResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;

    private String vehicleType;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;

    /**
     * 车辆号牌
     */
    private String vehicleNo;

}
