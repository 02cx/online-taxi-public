package com.dong.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

public enum CommonStatusEnum {

    /**
     *  验证码状态码：1000~1099
     */
    VERIFICATION_CODE_ERROR(1099,"验证码错误"),
    /**
     * token类提示：1100~1199
     */
    TOKEN_ERROR(1199,"token错误"),
    /**
     * 用户提示信息：1200~1299
     */
    USER_NOT_EXISTS(1200,"用户不存在"),
    /**
     * 计价规则信息：1300~1399
     */
    PRICE_RULE_NOT_EXISTS(1300,"计价规则不存在"),
    PRICE_RULE_EXISTS(1301,"计价规则已存在"),
    PRICE_RULE_NOT_CHANGE(1302,"修改的计价规则与最新的计价规则相同"),
    FARE_VERSION_NOT_NEW(1303,"计价规则不是最新"),
    /**
     *  行政区域信息：1400~1499
     */
    QUERY_DISTRICT_ERROR(1400,"查询行政区域错误"),
    /**
     * 司机——车辆关系信息：1500~1500
     */
    DRIVER_CAR_BINGDING(1500,"司机和车辆已绑定"),

    CAR_BINDING(1501,"车辆已被绑定"),

    DRIVER_BINDING(1502,"司机已被绑定"),

    DRIVER_CAR_BIND_NOT_EXISTS(1503,"司机车辆绑定关系不存在"),

    DRIVER_CAR_UNBINDING(1504,"司机车辆已处于解绑状态"),

    DRIVER_USER_NOT_EXISTS(1505,"司机不存在"),
    /**
     *  订单信息：1600~1699
     */
    ORDER_GOING_ON(1600,"有正在进行的订单"),
    DEVICE_INVALID(1601,"用户设备号超出下单限制次数"),
    CITY_SERVICE_NOT_EXISTS(1602,"该城市或车辆类型服务未开通"),


    /**
     * 验证码已过期
     */
    VERIFICATION_CODE_EXPIRED(1098,"验证码已过期"),

    /**
     * 成功
     */
    SUCCESS(1,"success"),
    /**
     * 失败
     */
    FAIL(0,"fail")

    ;

    @Getter
    private int code;
    @Getter
    private String message;

    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
