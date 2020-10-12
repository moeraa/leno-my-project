package com.moer.day.office.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenxh
 * @date 2020/3/31 11:46 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartCategory {

    /**
     * 类别名
     */
    private String categoryName;
    /**
     * 值
     */
    private double val;
    /**
     * 额外字段 例如：气泡图的bubbleSize
     * 注：
     *  1.气泡图：categoryName ->X轴; val ->Y轴  otherVal ->bubleSize   otherVal1 -> 数据标签的值，
     *  2.散点图：categoryName ->数据标签的值; val ->X轴  otherVal ->Y轴
     */
    private Object otherVal;
    private Object otherVal1;
    private Object otherVal2;
    private Object otherVal3;
    private Object otherVal4;

}
