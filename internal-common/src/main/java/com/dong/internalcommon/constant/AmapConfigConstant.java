package com.dong.internalcommon.constant;


public class AmapConfigConstant {

    /**
     *  路径规划url
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";

    /**
     * 行政区域url
     */
    public static final String DISTRICT_URL = "https://restapi.amap.com/v3/config/district";

    /**
     *  新增服务url
     */
    public static final String SERVICE_URL = "https://tsapi.amap.com/v1/track/service/add";
    /**
     * 新增终端url
     */
    public static final String TERMINAL_URL = "https://tsapi.amap.com/v1/track/terminal/add";
    /**
     * 新增轨迹url
     */
    public static final String TRACK_URL = "https://tsapi.amap.com/v1/track/trace/add";
    /**
     * 上传轨迹点url
     */
    public static final String UPLOAD_POINT_URL = "https://tsapi.amap.com/v1/track/point/upload";




    /**
     *  路径规划 entity key
     */
    public static final String STATUS = "status";

    public static final String ROUTE = "route";

    public static final String PATHS = "paths";

    public static final String DISTANCE = "distance";

    public static final String DURATION = "duration";

    /**
     *  行政区域 entity key
     */
    public static final String DISTRICTS = "districts";
    public static final String ADCODE = "adcode";
    public static final String NAME = "name";
    public static final String LEVEL = "level";


}
