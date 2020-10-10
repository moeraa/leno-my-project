package com.moer.day.ppt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenxh
 * @date 2020/3/31 11:46 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartSeries {

    /**
     * 系列名字
     */
    private String seriesName;
    /**
     * 该系列图表类别+值
     */
    private List<ChartCategory> chartCategoryList;

    /**
     * 是否有特殊值
     */
    private Object other;

}
