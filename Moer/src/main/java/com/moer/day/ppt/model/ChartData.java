package com.moer.day.ppt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenxh
 * @date 2020/4/3 4:19 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartData {

    private String shapeType;
    /**
     * 所有系列
     */
    private int specialChartIndex;
    private List<ChartSeries> chartSeriesList;
}
