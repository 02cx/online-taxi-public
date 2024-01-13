package com.dong.servicemap.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName dic_district
 */
@TableName(value ="dic_district")
@Data
@Builder
public class DicDistrict implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 地区编码
     */
    private String addressCode;

    /**
     * 地区名称
     */
    private String addressName;

    /**
     * 父地区编码
     */
    private String parentAddressCode;

    /**
     * 级别
     */
    private Integer level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}