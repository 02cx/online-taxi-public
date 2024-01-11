package com.dong.internalcommon.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoVO {

    public String passengerName;

    public String profilePhoto;
}
