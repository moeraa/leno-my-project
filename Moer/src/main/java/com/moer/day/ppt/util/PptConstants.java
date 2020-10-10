package com.moer.day.ppt.util;


/**
 * @author chenxh
 * @date 2020/4/8  5:01 下午
 * @func
 */
public interface PptConstants extends Constants {
    /**
     * 生成ppt失败
     */
    public static int GENERATE_PPT_FAILED = 500;
    /**
     * 下载ppt失败
     */
    public static int DOWNLOAD_PPT_FAILED = 501;
    /**
     * 生成ppt的后缀
     */
    public static String PPT_SUFFIX = ".pptx";
    /**
     * ppt生成状态 0 成功  -1 失败 其他值 进度
     */
    public static int GENERTAE_STATUS_SUCCESS = 0;
    public static int GENERTAE_STATUS_FAILED = -1;
    /**
     * 周粒度
     */
    public static String WEEK = "week";

    public String WEBSOCKET_HEART_CHECK_SIGN = "heartCheck";

    public String AUTH_CODE = "poiuytrewQ_0";
    /**
     * 城市级别
     */
    public String CITY = "city";

    /**
     * ppt 坐标轴 相关参数
     */

    public static String L_SCALING_MAX = "L_SCALING_MAX";
    public static String L_SCALING_MIN = "L_SCALING_MIN";
    public static String L_MAJOR_UNIT = "L_MAJOR_UNIT";
    public static String B_SCALING_MAX = "B_SCALING_MAX";
    public static String B_SCALING_MIN = "B_SCALING_MIN";
    public static String B_MAJOR_UNIT = "B_MAJOR_UNIT";

    /**
     * ppt 填充图表相关颜色
     */
    public static String SOLID_FILL_SRGB_CLR = "SOLID_FILL_SRGB_CLR";
    public static String SOLID_FILL_VAL= "SOLID_FILL_VAL";
    /**
     * ppt 填充图表中的idx
     */
    public static String PPT_SER_IDX = "PPT_SER_IDX";

    public static String SEDAN="sedan";


}
