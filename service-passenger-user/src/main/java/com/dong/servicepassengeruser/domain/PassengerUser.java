package com.dong.servicepassengeruser.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName passenger_user
 */
@TableName(value ="passenger_user")
@Data
public class PassengerUser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String passengerPhone;

    /**
     * 
     */
    private String passengerName;

    /**
     * 0：女  1：男
     */
    private byte passengerGender;

    /**
     * 0:有效  1:失效
     */
    private byte state;

    /**
     * 
     */
    private LocalDateTime gmtCreate;

    /**
     * 
     */
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}