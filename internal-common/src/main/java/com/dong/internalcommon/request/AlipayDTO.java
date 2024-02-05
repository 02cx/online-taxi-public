package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class AlipayDTO {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
}
