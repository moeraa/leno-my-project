package com.org.moer.ppt.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : hepw
 * @date : 2020/4/18 0:51
 * @func : 车型级别
 */
public class CarLevelUtils {

    /**
     * 全部级别映射
     */
    static Map<String, String> levels = new HashMap<>();

    public static Map<String, String> getNameMaps() {
        synchronized (levels) {
            if (levels.size() <= 0) {
                levels = new LinkedHashMap<>(23);

                levels.put("1", "微型车");
                levels.put("2", "小型车");
                levels.put("3", "紧凑型车");
                levels.put("4", "中型车");
                levels.put("5", "中大型车");
                levels.put("6", "豪华车");
                levels.put("7", "SUV");
                levels.put("7_1", "小型SUV");
                levels.put("7_2", "紧凑型SUV");
                levels.put("7_3", "中型SUV");
                levels.put("7_4", "大型SUV");
                levels.put("7_5", "全尺寸SUV");
                levels.put("8", "MPV");
                levels.put("8_14", "小型MPV");
                levels.put("8_15", "紧凑型MPV");
                levels.put("8_16", "中型MPV");
                levels.put("8_17", "大型MPV");
                levels.put("9", "跑车");
                levels.put("9_18", "小型跑车");
                levels.put("9_19", "中型跑车");
                levels.put("9_20", "大型跑车");
                levels.put("11", "微面");
                levels.put("12", "皮卡");
            }
            return levels;
        }
    }

    /**
     * 根据id获取二级级别
     *
     * @param id
     * @return
     */
    public static String getSecondLevelNameById(String id) {
        return getNameMaps().get(id);
    }
}
