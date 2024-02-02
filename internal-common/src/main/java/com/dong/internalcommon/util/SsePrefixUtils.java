package com.dong.internalcommon.util;

public class SsePrefixUtils {

    public static final String sperator = "&";

    public static final String generatorsKey(Long userId,String identity){
        return userId + sperator + identity;
    }
}
