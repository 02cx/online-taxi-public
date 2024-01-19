package com.dong.serviceprice.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName price_rule
 */
@TableName(value ="price_rule")
@Data
public class PriceRule implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}