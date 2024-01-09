package com.dong.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

public enum CommonStatusEnum {

    /**
     *  验证码状态码：1000~1099
     */
    VERIFICATION_CODE_ERROR(1099,"验证码错误"),
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
