package com.dong.internalcommon.constant;

public class OrderConstant {

    // 订单无效：0
    public static final Integer ORDER_INVALID = 0;
    // 订单开始：1
    public static final Integer ORDER_START = 1;
    // 司机接单：2
    public static final Integer DRIVER_TAKE_ORDER = 2;
    // 去接乘客：3
    public static final Integer ORDER_GOING_PASSENGER = 3;
    // 司机到达乘客上车点：4
    public static final Integer DRIVER_ARRIVE_PICK = 4;
    // 乘客上车，开始行程：5
    public static final Integer START_ITINERARY = 5;
    // 到达目的地，未支付：6
    public static final Integer ORDER_UNPAID = 6;
    // 发起收款：7 Initiate collection
    public static final Integer INITIATE_COLLECTION = 7;
    // 支付完成：8 Payment completed
    public static final Integer PAY_COMPLETED = 8;
    // 订单取消：9
    public static final Integer ORDER_CANCEL = 9;

    // 用户初始下单次数
    public static final String INIT_ORDER_COUNT = "1";
    // 黑名单设备号有效时间
    public static final Integer BLACK_DEVICE_TIME = 1;
}
