package com.dong.internalcommon.request;

import lombok.Data;

@Data
public class PushPayPriceDTO {

    private Long orderId;

    private Double price;

    private Long passengerId;
}
