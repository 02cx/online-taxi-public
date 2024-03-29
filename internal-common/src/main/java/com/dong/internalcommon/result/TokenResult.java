package com.dong.internalcommon.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResult {

    private String phone;

    private String identity;

    private String tokenType;
}
