package com.dong.internalcommon.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName price_rule
 */
@Data
public class PriceRuleDTO {
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
}