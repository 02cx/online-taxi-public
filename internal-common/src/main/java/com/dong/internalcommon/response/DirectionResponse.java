package com.dong.internalcommon.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectionResponse {

    public String distance;
    public String duration;
}
