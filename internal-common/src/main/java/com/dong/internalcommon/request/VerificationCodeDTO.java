package com.dong.internalcommon.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeDTO {

    private String driverPhone;

    private String passengerPhone;

    private String verificationCode;
}
