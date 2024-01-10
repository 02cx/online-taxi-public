package com.dong.internalcommon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResult {

    private String phone;

    private String identity;
}
