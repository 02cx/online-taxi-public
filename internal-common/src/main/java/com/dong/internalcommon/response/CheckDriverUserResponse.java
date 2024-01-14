package com.dong.internalcommon.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckDriverUserResponse {

    private String driverPhone;
    /**
     *  司机存在：0   司机不存在：1
     */
    private Integer ifExists;
}
