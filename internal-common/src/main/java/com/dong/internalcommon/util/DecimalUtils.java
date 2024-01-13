package com.dong.internalcommon.util;

import java.math.BigDecimal;

public class DecimalUtils {


    public static double add(double d1,double d2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(d1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(d2);
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    public static double divide(double d1,double d2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(d1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(d2);
        if(d2 == 0){
            throw new IllegalArgumentException("除数不能为0");
        }
        return bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static double subtract(double d1,double d2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(d1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(d2);
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    public static double multiply(double d1,double d2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(d1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(d2);
        return bigDecimal1.multiply(bigDecimal2).doubleValue();
    }

}
