package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class AroundSearchTerminalDTO {

    // 示例：  “经度,纬度”
    private String center;
    // 单位：米
    private Integer radius;
}
