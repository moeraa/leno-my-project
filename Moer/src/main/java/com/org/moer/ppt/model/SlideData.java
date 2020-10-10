package com.org.moer.ppt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenxh
 * @date 2020/4/3 4:19 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideData {

    /**
     * ppt页码
     */
    private Integer slidePage;
    private Map<Integer, ShapeData> shapeDataMap = new HashMap<>();

    public void initShapeData(Integer shapePage, TableData tableData, ChartData chartData, HashMap<String, String> textMap, byte[] pictureData) {
        shapeDataMap.put(shapePage, new ShapeData(shapePage, tableData, chartData, textMap, pictureData, null, null,null));
    }

    public void initShapeData(Integer shapePage, TableData tableData, ChartData chartData, HashMap<String, String> textMap, byte[] pictureData, List<ChartData> chartDataList) {
        shapeDataMap.put(shapePage, new ShapeData(shapePage, tableData, chartData, textMap, pictureData, chartDataList, null,null));
    }

    public void initShapeData(Integer shapePage, TableData tableData, ChartData chartData, HashMap<String, String> textMap, byte[] pictureData, List<ChartData> chartDataList, Map<String, Double> ctValAxMap) {
        shapeDataMap.put(shapePage, new ShapeData(shapePage, tableData, chartData, textMap, pictureData, chartDataList, ctValAxMap,null));
    }
   public void initShapeData(Integer shapePage, TableData tableData, ChartData chartData, HashMap<String, String> textMap, byte[] pictureData, List<ChartData> chartDataList, Map<String, Double> ctValAxMap, List<Map<String, Object>> specialValList) {
        shapeDataMap.put(shapePage, new ShapeData(shapePage, tableData, chartData, textMap, pictureData, chartDataList, ctValAxMap,specialValList));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ShapeData {
        /**
         * 形状编号
         */
        private Integer shapePage;
        /**
         * 表格数据 支持多个表格 按照指定的位置渲染
         */
        private TableData tableData;
        /**
         * 图表数据 支持多个图表 按照指定的位置渲染
         */
        private ChartData chartData;
        /**
         * 文本数据 格式 key:car_model value：大众
         */
        private Map<String, String> textMap;
        private byte[] pictureData;
        /**
         * 针对折线图+柱状图
         */
        private List<ChartData> chartDataList;
        /**
         * 坐标轴设置
         * key ：STAxPos.L.toString() or STAxPos.B.toString()
         */
        Map<String, Double> ctValAxMap;

        /**
         * 图表特殊属性设置
         */
        List<Map<String, Object>> specialValList;


    }
}
