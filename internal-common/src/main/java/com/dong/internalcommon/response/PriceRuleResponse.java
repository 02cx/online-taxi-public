package com.dong.internalcommon.response;

import lombok.Data;

@Data
public class PriceRuleResponse {
    /**
     *
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private Double startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 一公里价格
     */
    private Double unitPricePerMile;

    /**
     * 每分钟价格
     */
    private Double unitPricePerMinute;

    /**
     * 计价类型 = cityCode + vehicleType
     */
    private String fareType;
    /**
     * 计价版本
     */
    private Integer fareVersion;

}
