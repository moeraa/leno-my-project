//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.moer.day.ppt;

import com.moer.day.ppt.util.DateUtils;
import com.moer.day.ppt.util.PptUtils;
import com.moer.day.ppt.model.*;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenxh
 * @date 2020/7/7 6:17 下午
 */
public class TestForCxh {
    private static final Logger log = LoggerFactory.getLogger(TestForCxh.class);

    public TestForCxh() {
    }

    public static void main(String[] args) throws ParseException {
        patternFromEs();
    }

    public static void patternFromEs() {
        String regx = "\"sumNValue\": \\{\n {18}\"value\": (\\d*)\n {16}";
        String regx2 = "data=(\\d*)";
        String regx1 = "\"value\": (\\d*)";
        String regx3 = "levelOneId=modelLevel_(\\d*),";
        Pattern pattern = Pattern.compile(regx1);
        Matcher matcher = pattern.matcher("{\n" +
                "  \"took\": 5,\n" +
                "  \"timed_out\": false,\n" +
                "  \"_shards\": {\n" +
                "    \"total\": 5,\n" +
                "    \"successful\": 5,\n" +
                "    \"failed\": 0\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"total\": 75,\n" +
                "    \"max_score\": 0,\n" +
                "    \"hits\": []\n" +
                "  },\n" +
                "  \"aggregations\": {\n" +
                "    \"/dimen/dateTime\": {\n" +
                "      \"buckets\": [\n" +
                "        {\n" +
                "          \"key_as_string\": \"2020\",\n" +
                "          \"key\": 1577836800000,\n" +
                "          \"doc_count\": 75,\n" +
                "          \"/yiche_common/app_index_sales_leveltwo_nation_month/level_one_id\": {\n" +
                "            \"doc_count_error_upper_bound\": 0,\n" +
                "            \"sum_other_doc_count\": 0,\n" +
                "            \"buckets\": [\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_1\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 34659\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_2\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 162571\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_3\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 1738792\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_4\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 682281\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_5\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 246332\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_6\",\n" +
                "                \"doc_count\": 5,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_0\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 31095\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_7\",\n" +
                "                \"doc_count\": 25,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_1\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 519825\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_2\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 1508859\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_3\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 694422\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_4\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 178437\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_5\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 21089\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              {\n" +
                "                \"key\": \"modelLevel_8\",\n" +
                "                \"doc_count\": 20,\n" +
                "                \"/yiche_common/app_index_sales_leveltwo_nation_month/level_two_id\": {\n" +
                "                  \"doc_count_error_upper_bound\": 0,\n" +
                "                  \"sum_other_doc_count\": 0,\n" +
                "                  \"buckets\": [\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_14\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 84570\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_15\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 94101\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_16\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 71450\n" +
                "                      }\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"key\": \"secondModelLevel_17\",\n" +
                "                      \"doc_count\": 5,\n" +
                "                      \"/yiche_common/app_index_sales_leveltwo_nation_month/sales\": {\n" +
                "                        \"value\": 70760\n" +
                "                      }\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}");

        while(matcher.find()) {
            System.out.println(matcher.group(1));
        }

    }

    private static void deletePptPage(XMLSlideShow xmlSlideShow, int pageStep, List<Integer> pageBases) {
        for(int i = 0; i < pageBases.size(); ++i) {
            Integer pageBase = (Integer)pageBases.get(i) - pageStep * i;
            log.info("pageBase:{}", pageBase);

            for(int j = 0; j < pageStep; ++j) {
                log.info("删除页数 {}", pageBase + j);
                xmlSlideShow.removeSlide(pageBase - 1);
            }

            log.info("page size:{}", xmlSlideShow.getSlides().size());
        }

    }

    private static Map<Integer, SlideData> testData() throws ParseException {
        Map<Integer, SlideData> slideDataMap = new HashMap(40);
        page28ForSales(slideDataMap);
        return slideDataMap;
    }

    private static void page28ForSales(Map<Integer, SlideData> slideDataMap) {
        SlideData slideData = new SlideData();
        slideData.setSlidePage(17);
        ChartData barChart = new ChartData();
        barChart.setShapeType("bar");
        List<ChartSeries> seriesArrayList = new ArrayList();

        for(int j = 1; j <= 2; ++j) {
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.setSeriesName("正面/负面声量" + j);
            List<ChartCategory> chartCategories = new ArrayList();

            for(int i = 1; i <= 6; ++i) {
                ChartCategory chartCategory = new ChartCategory();
                chartCategory.setCategoryName("车名" + i);
                if (j == 1) {
                    chartCategory.setVal(17.98D + (double)(j * 2) + (double)i);
                } else {
                    chartCategory.setVal(-17.98D + (double)(j * 2) + (double)i);
                }

                chartCategories.add(chartCategory);
            }

            chartSeries.setChartCategoryList(chartCategories);
            seriesArrayList.add(chartSeries);
        }

        barChart.setChartSeriesList(seriesArrayList);
        ChartData radarChart = new ChartData();
        radarChart.setShapeType("line");
        List<ChartSeries> lineSeriesArrayList = new ArrayList();
        ChartSeries chartSeries = new ChartSeries();
        List<ChartCategory> chartCategories = new ArrayList();
        chartSeries.setSeriesName("占比");

        for(int j = 1; j <= 6; ++j) {
            ChartCategory bubbleChartCategory = new ChartCategory();
            bubbleChartCategory.setCategoryName("车名" + j);
            bubbleChartCategory.setVal(0.2D * (double)j);
            chartCategories.add(bubbleChartCategory);
        }

        chartSeries.setChartCategoryList(chartCategories);
        lineSeriesArrayList.add(chartSeries);
        radarChart.setChartSeriesList(lineSeriesArrayList);
        List<ChartData> list = new ArrayList();
        list.add(barChart);
        list.add(radarChart);
        slideData.initShapeData(4, (TableData)null, (ChartData)null, (HashMap)null, (byte[])null, list);
        slideDataMap.put(17, slideData);
    }

    private static void testStockForPage9(Map<Integer, SlideData> slideDataMap) {
        SlideData slideData = new SlideData();
        slideData.setSlidePage(9);
        ChartData radarChart = new ChartData();
        List<ChartSeries> seriesArrayList = new ArrayList();

        for(int j = 0; j < 2; ++j) {
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.setSeriesName("最高最低价" + j);
            List<ChartCategory> chartCategories = new ArrayList();

            for(int i = 0; i < 10; ++i) {
                ChartCategory chartCategory = new ChartCategory();
                chartCategory.setCategoryName("车名" + i + j);
                chartCategory.setVal(17.98D + (double)(j * 2) + (double)i);
                chartCategories.add(chartCategory);
            }

            chartSeries.setChartCategoryList(chartCategories);
            seriesArrayList.add(chartSeries);
        }

        radarChart.setChartSeriesList(seriesArrayList);
        slideData.initShapeData(4, (TableData)null, radarChart, (HashMap)null, (byte[])null, (List)null);
        slideDataMap.put(9, slideData);
    }

    private static void testRadarForPage24(Map<Integer, SlideData> slideDataMap) {
        SlideData slideData = new SlideData();
        slideData.setSlidePage(24);
        ChartData radarChart = new ChartData();
        List<ChartSeries> seriesArrayList = new ArrayList();

        for(int j = 0; j < 2; ++j) {
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.setSeriesName("车名" + j);
            List<ChartCategory> chartCategories = new ArrayList();

            for(int i = 0; i < 10; ++i) {
                ChartCategory chartCategory = new ChartCategory();
                chartCategory.setCategoryName("外观" + i + j);
                chartCategory.setVal((double)(85 + i) + Double.valueOf((double)(i / 10)) + (double)j);
                chartCategories.add(chartCategory);
            }

            chartSeries.setChartCategoryList(chartCategories);
            seriesArrayList.add(chartSeries);
        }

        radarChart.setChartSeriesList(seriesArrayList);
        slideData.initShapeData(4, (TableData)null, radarChart, (HashMap)null, (byte[])null, (List)null);
        slideDataMap.put(24, slideData);
    }

    private static void getSlideDataForPage4(Map<Integer, SlideData> slideDataMap) throws ParseException {
        SlideData fillSlideData1 = new SlideData();
        fillSlideData1.setSlidePage(4);
        HashMap<String, String> fillTextMap5 = new HashMap(1);
        fillTextMap5.put("car_model", "宝马");
        String compareCloudPath = "/Users/bita/Documents/code/a_yiche/ppt/index_ppt/ppt/src/main/resources/images/CompareCloud_2019-11-thumbnail.png";
        log.info("compareCloudPath={}", compareCloudPath);
        byte[] picData = PptUtils.imageTobytes(compareCloudPath);
        fillSlideData1.initShapeData(4, (TableData)null, (ChartData)null, (HashMap)null, picData);
        fillSlideData1.initShapeData(28, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
        HashMap<String, String> fillTextMapTime = new HashMap(1);
        String monthStr = DateUtils.getMonthStr("2020-09-01");
        String s = DateUtils.formatDateForMonth(monthStr);
        fillTextMapTime.put("time", s);
        fillSlideData1.initShapeData(20, (TableData)null, (ChartData)null, fillTextMapTime, (byte[])null);
        slideDataMap.put(4, fillSlideData1);
    }

    private static void getSlideDataForPage52(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData = new SlideData();
        Map<Integer, List<Integer>> shapeMap = new HashMap(6);
        ArrayList<Integer> shapeList1 = new ArrayList();
        shapeList1.addAll(Arrays.asList(14, 3));
        ArrayList<Integer> shapeList2 = new ArrayList();
        shapeList2.addAll(Arrays.asList(15, 4));
        ArrayList<Integer> shapeList3 = new ArrayList();
        shapeList3.addAll(Arrays.asList(16, 5));
        ArrayList<Integer> shapeList4 = new ArrayList();
        shapeList4.addAll(Arrays.asList(17, 6));
        ArrayList<Integer> shapeList5 = new ArrayList();
        shapeList5.addAll(Arrays.asList(18, 7));
        ArrayList<Integer> shapeList6 = new ArrayList();
        shapeList6.addAll(Arrays.asList(19, 8));
        ArrayList<Integer> shapeList7 = new ArrayList();
        shapeList7.addAll(Arrays.asList(20, 9));
        shapeMap.put(2, shapeList2);
        shapeMap.put(1, shapeList1);
        shapeMap.put(3, shapeList3);
        shapeMap.put(4, shapeList4);
        shapeMap.put(5, shapeList5);
        shapeMap.put(6, shapeList6);
        shapeMap.put(7, shapeList7);
        int test = 0;
        Iterator var11 = shapeMap.values().iterator();

        while(var11.hasNext()) {
            List<Integer> shapeLocation = (List)var11.next();
            ++test;
            HashMap<String, String> textMap = new HashMap(1);
            textMap.put("name", "名字啊" + test);
            fillSlideData.initShapeData((Integer)shapeLocation.get(0), (TableData)null, (ChartData)null, textMap, (byte[])null);
            ChartData barData = new ChartData();
            List<ChartSeries> barChartSeries = new ArrayList();
            ChartSeries barSeries1 = new ChartSeries();
            barSeries1.setSeriesName("名字啊" + test);
            List<ChartCategory> barChartCategories = new ArrayList();

            for(int i = 0; i < 10; ++i) {
                ChartCategory barChartCategory = new ChartCategory();
                barChartCategory.setCategoryName("外观" + test);
                barChartCategory.setVal((double)(600 * i));
                barChartCategories.add(barChartCategory);
            }

            barSeries1.setChartCategoryList(barChartCategories);
            barChartSeries.add(barSeries1);
            barData.setChartSeriesList(barChartSeries);
            fillSlideData.initShapeData((Integer)shapeLocation.get(1), (TableData)null, barData, (HashMap)null, (byte[])null);
        }

        fillSlideData.setSlidePage(52);
        slideDataMap.put(52, fillSlideData);
    }

    private static void getSlideDataForPage17(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData16 = new SlideData();
        TableData fillTable = new TableData();
        ArrayList<TableRowData> tableRowDataArrayList = new ArrayList(10);
        TableRowData carNameRowData = new TableRowData();
        ArrayList<String> carNameList = new ArrayList();
        carNameList.addAll(Arrays.asList("aa", "bb", "vv", "rr", "sss"));
        carNameRowData.setDataList(carNameList);
        TableRowData rowData = new TableRowData();
        ArrayList<String> dataList = new ArrayList();
        dataList.addAll(Arrays.asList("11", "22", "33", "44", "55"));
        rowData.setDataList(dataList);
        tableRowDataArrayList.add(carNameRowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        tableRowDataArrayList.add(rowData);
        fillTable.setStartRowNum(0);
        fillTable.setStartColumn(0);
        fillTable.setTableRowDataList(tableRowDataArrayList);
        fillSlideData16.initShapeData(6, fillTable, (ChartData)null, (HashMap)null, (byte[])null);
        fillSlideData16.setSlidePage(17);
        slideDataMap.put(17, fillSlideData16);
    }

    private static void getSlideDataForPage56(Map<Integer, SlideData> slideDataMap) {
        byte[] negativePicData = null;
        SlideData fillSlideData = new SlideData();
        Map<Integer, List<Integer>> shapeMap = new HashMap(7);
        ArrayList<Integer> shapeList1 = new ArrayList();
        shapeList1.addAll(Arrays.asList(32, 6, 5));
        ArrayList<Integer> shapeList2 = new ArrayList();
        shapeList2.addAll(Arrays.asList(29, 4, 3));
        ArrayList<Integer> shapeList3 = new ArrayList();
        shapeList3.addAll(Arrays.asList(26, 2, 14));
        ArrayList<Integer> shapeList4 = new ArrayList();
        shapeList4.addAll(Arrays.asList(23, 13, 12));
        ArrayList<Integer> shapeList5 = new ArrayList();
        shapeList5.addAll(Arrays.asList(20, 11, 10));
        ArrayList<Integer> shapeList6 = new ArrayList();
        shapeList6.addAll(Arrays.asList(17, 9, 8));
        shapeMap.put(0, shapeList1);
        shapeMap.put(1, shapeList2);
        shapeMap.put(2, shapeList3);
        shapeMap.put(3, shapeList4);
        shapeMap.put(4, shapeList5);
        shapeMap.put(5, shapeList6);
        Iterator var10 = shapeMap.values().iterator();

        while(var10.hasNext()) {
            List<Integer> shapeLocation = (List)var10.next();
            HashMap<String, String> textMap15 = new HashMap(1);
            textMap15.put("name", "宝马");
            fillSlideData.initShapeData((Integer)shapeLocation.get(0), (TableData)null, (ChartData)null, textMap15, (byte[])null);
            String picturePath = System.getProperty("user.dir") + File.separator + "/src/main/resources/tmpl/logo.png";
            byte[] picData = PptUtils.imageTobytes(picturePath);
            fillSlideData.initShapeData((Integer)shapeLocation.get(1), (TableData)null, (ChartData)null, (HashMap)null, picData);
            fillSlideData.initShapeData((Integer)shapeLocation.get(2), (TableData)null, (ChartData)null, (HashMap)null, picData);
        }

        fillSlideData.setSlidePage(54);
        slideDataMap.put(54, fillSlideData);
    }

    private static void getSlideDataForPage55(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData = new SlideData();
        ChartData barData = new ChartData();
        List<ChartSeries> barChartSeries = new ArrayList();
        ChartSeries barSeries1 = new ChartSeries();
        barSeries1.setSeriesName("情感指数");
        List<ChartCategory> barChartCategories = new ArrayList();

        for(int k = 0; k < 6; ++k) {
            ChartCategory barChartCategory = new ChartCategory();
            barChartCategory.setCategoryName("宝马3系" + k);
            barChartCategory.setVal((double)(55 + k));
            barChartCategories.add(barChartCategory);
        }

        barSeries1.setChartCategoryList(barChartCategories);
        barChartSeries.add(barSeries1);
        barData.setChartSeriesList(barChartSeries);
        fillSlideData.initShapeData(4, (TableData)null, barData, (HashMap)null, (byte[])null);
        TableData fillTable = new TableData();
        ArrayList<TableRowData> tableRowDataArrayList = new ArrayList(10);

        for(int i = 0; i < 10; ++i) {
            TableRowData carNameRowData = new TableRowData();
            ArrayList<String> carNameList = new ArrayList();
            carNameList.addAll(Arrays.asList("aa"));
            carNameRowData.setDataList(carNameList);
            tableRowDataArrayList.add(carNameRowData);
        }

        fillTable.setStartRowNum(0);
        fillTable.setStartColumn(0);
        fillTable.setTableRowDataList(tableRowDataArrayList);
        fillSlideData.initShapeData(9, fillTable, (ChartData)null, (HashMap)null, (byte[])null);
        ChartData bubbleData = new ChartData();
        List<ChartSeries> bubbleChartSeries = new ArrayList();
        ChartSeries bubbleSeries1 = new ChartSeries();
        bubbleSeries1.setSeriesName("Y 值");
        List<ChartCategory> bubbleChartCategories = new ArrayList();

        for(int i = 0; i < 6; ++i) {
            for(int k = 0; k < 10; ++k) {
                ChartCategory bubbleChartCategory = new ChartCategory();
                bubbleChartCategory.setCategoryName(k + "");
                bubbleChartCategory.setVal((double)(k * i));
                bubbleChartCategory.setOtherVal(5);
                bubbleChartCategories.add(bubbleChartCategory);
            }
        }

        bubbleSeries1.setChartCategoryList(bubbleChartCategories);
        ChartSeries bubbleSeries2 = new ChartSeries();
        bubbleSeries2.setSeriesName("车名");
        List<ChartCategory> bubbleChartCategories1 = new ArrayList();

        for(int i = 0; i < 6; ++i) {
            ChartCategory bubbleChartCategory = new ChartCategory();
            bubbleChartCategory.setCategoryName(i * 2 + "");
            bubbleChartCategory.setVal((double)(100 * i));
            bubbleChartCategory.setOtherVal(5);
            bubbleChartCategories1.add(bubbleChartCategory);
        }

        bubbleSeries2.setChartCategoryList(bubbleChartCategories);
        bubbleChartSeries.add(bubbleSeries1);
        bubbleChartSeries.add(bubbleSeries2);
        bubbleData.setChartSeriesList(bubbleChartSeries);
        fillSlideData.initShapeData(3, (TableData)null, bubbleData, (HashMap)null, (byte[])null);
        fillSlideData.setSlidePage(55);
        slideDataMap.put(55, fillSlideData);
    }

    private static void getSlideDataForPage54(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData = new SlideData();
        ChartData barData = new ChartData();
        List<ChartSeries> barChartSeries = new ArrayList();
        String[] seriesName = new String[]{"正面声量", "负面声量"};

        for(int i = 0; i < seriesName.length; ++i) {
            ChartSeries barSeries1 = new ChartSeries();
            barSeries1.setSeriesName(seriesName[i]);
            List<ChartCategory> barChartCategories = new ArrayList();

            for(int k = 0; k < 6; ++k) {
                ChartCategory barChartCategory = new ChartCategory();
                barChartCategory.setCategoryName("宝马3系" + k);
                barChartCategory.setVal((double)('鱀' + k));
                barChartCategories.add(barChartCategory);
            }

            barSeries1.setChartCategoryList(barChartCategories);
            barChartSeries.add(barSeries1);
        }

        barData.setChartSeriesList(barChartSeries);
        fillSlideData.initShapeData(3, (TableData)null, barData, (HashMap)null, (byte[])null);
        TableData fillTable = new TableData();
        ArrayList<TableRowData> tableRowDataArrayList = new ArrayList(10);

        for(int i = 0; i < 6; ++i) {
            TableRowData carNameRowData = new TableRowData();
            ArrayList<String> carNameList = new ArrayList();
            carNameList.addAll(Arrays.asList("aa"));
            carNameRowData.setDataList(carNameList);
            tableRowDataArrayList.add(carNameRowData);
        }

        fillTable.setStartRowNum(0);
        fillTable.setStartColumn(0);
        fillTable.setTableRowDataList(tableRowDataArrayList);
        fillSlideData.initShapeData(10, fillTable, (ChartData)null, (HashMap)null, (byte[])null);
        TableData fillTable1 = new TableData();
        ArrayList<TableRowData> tableRowDataArrayList1 = new ArrayList(10);
        TableRowData carNameRowData = new TableRowData();
        ArrayList<String> carNameList = new ArrayList();
        carNameList.addAll(Arrays.asList("bb", "bb", "bb", "bb", "bb", "bb", "bb", "bb", "bb"));
        carNameRowData.setDataList(carNameList);
        tableRowDataArrayList1.add(carNameRowData);
        fillTable1.setStartRowNum(0);
        fillTable1.setStartColumn(0);
        fillTable1.setTableRowDataList(tableRowDataArrayList1);
        fillSlideData.initShapeData(11, fillTable1, (ChartData)null, (HashMap)null, (byte[])null);
        ChartData bubbleData = new ChartData();
        List<ChartSeries> bubbleChartSeries = new ArrayList();
        ChartSeries barSeries1 = new ChartSeries();
        barSeries1.setSeriesName("正面声量");
        List<ChartCategory> barChartCategories = new ArrayList();

        for(int i = 0; i < 6; ++i) {
            for(int k = 0; k < 10; ++k) {
                ChartCategory bubbleChartCategory = new ChartCategory();
                bubbleChartCategory.setCategoryName(k + "");
                bubbleChartCategory.setVal((double)(k * i));
                bubbleChartCategory.setOtherVal(3000 * i);
                barChartCategories.add(bubbleChartCategory);
            }
        }

        barSeries1.setChartCategoryList(barChartCategories);
        bubbleChartSeries.add(barSeries1);
        bubbleData.setChartSeriesList(bubbleChartSeries);
        fillSlideData.initShapeData(4, (TableData)null, bubbleData, (HashMap)null, (byte[])null);
        fillSlideData.setSlidePage(54);
        slideDataMap.put(54, fillSlideData);
    }

    private static void getSlideDataForPage53(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData = new SlideData();
        ChartData barData = new ChartData();
        barData.setSpecialChartIndex(1);
        List<ChartSeries> barChartSeries = new ArrayList();
        ChartSeries barSeries1 = new ChartSeries();
        barSeries1.setSeriesName("Y值");
        List<ChartCategory> barChartCategories = new ArrayList();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                ChartCategory bubbleChartCategory = new ChartCategory();
                bubbleChartCategory.setCategoryName(i * j + "");
                bubbleChartCategory.setVal((double)i);
                bubbleChartCategory.setOtherVal(5);
                bubbleChartCategory.setOtherVal1("toTime");
                bubbleChartCategory.setOtherVal2("publicPraiseVoiceInfo.getCarName()" + i);
                bubbleChartCategory.setOtherVal3("indicatorsName");
                barChartCategories.add(bubbleChartCategory);
            }
        }

        barSeries1.setChartCategoryList(barChartCategories);
        barChartSeries.add(barSeries1);
        barData.setChartSeriesList(barChartSeries);
        fillSlideData.initShapeData(3, (TableData)null, barData, (HashMap)null, (byte[])null);
        fillSlideData.setSlidePage(1);
        slideDataMap.put(1, fillSlideData);
    }

    private static void getSlideDataForPage42(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData42 = new SlideData();
        ChartData lineChartData42 = new ChartData();
        List<ChartSeries> chartSeriesList131 = new ArrayList();

        for(int j = 0; j < 6; ++j) {
            ChartSeries lineChartSeries = new ChartSeries();
            lineChartSeries.setSeriesName("奥迪A4L" + j);
            List<ChartCategory> chartCategoryList1 = new ArrayList();

            for(int i = 1; i <= 6; ++i) {
                ChartCategory lineChartCategory = new ChartCategory();
                lineChartCategory.setCategoryName("2020-04-03");
                lineChartCategory.setVal((double)((i + j) * 10000 * 2));
                chartCategoryList1.add(lineChartCategory);
            }

            lineChartSeries.setChartCategoryList(chartCategoryList1);
            chartSeriesList131.add(lineChartSeries);
        }

        lineChartData42.setChartSeriesList(chartSeriesList131);
        fillSlideData42.initShapeData(4, (TableData)null, lineChartData42, (HashMap)null, (byte[])null);
        ChartData lineChartData171 = new ChartData();
        List<ChartSeries> chartSeriesList171 = new ArrayList();

        int i;
        ArrayList dungnutChartSeries;
        for(i = 0; i < 6; ++i) {
            ChartSeries lineChartSeries = new ChartSeries();
            lineChartSeries.setSeriesName("奥迪A4L" + i);
            dungnutChartSeries = new ArrayList();

            for(int j = 1; j <= 30; ++j) {
                ChartCategory lineChartCategory = new ChartCategory();
                lineChartCategory.setCategoryName("2020-04-03");
                lineChartCategory.setVal((double)(j + j * 1000));
                dungnutChartSeries.add(lineChartCategory);
            }

            lineChartSeries.setChartCategoryList(dungnutChartSeries);
            chartSeriesList171.add(lineChartSeries);
        }

        lineChartData171.setChartSeriesList(chartSeriesList171);
        fillSlideData42.initShapeData(5, (TableData)null, lineChartData171, (HashMap)null, (byte[])null);

        for(i = 0; i < 5; ++i) {
            ChartData dungnutChart = new ChartData();
            dungnutChartSeries = new ArrayList();
            ChartSeries dungnutSeries = new ChartSeries();
            List<ChartCategory> barChartCategories13 = new ArrayList();
            dungnutSeries.setSeriesName("花花");

            for(int j = 0; j < 2; ++j) {
                ChartCategory barChartCategory = new ChartCategory();
                barChartCategory.setCategoryName("专一度");
                barChartCategory.setVal(0.5D);
                barChartCategories13.add(barChartCategory);
            }

            dungnutSeries.setChartCategoryList(barChartCategories13);
            dungnutChartSeries.add(dungnutSeries);
            dungnutChart.setChartSeriesList(dungnutChartSeries);
            fillSlideData42.initShapeData(6 + i, (TableData)null, dungnutChart, (HashMap)null, (byte[])null);
        }

        ChartData dungnutChart = new ChartData();
        List<ChartSeries> dungnutChartSeries1 = new ArrayList();
        ChartSeries dungnutSeries = new ChartSeries();
        List<ChartCategory> barChartCategories13 = new ArrayList();
        dungnutSeries.setSeriesName("cc");

        for(int j = 0; j < 2; ++j) {
            ChartCategory barChartCategory = new ChartCategory();
            barChartCategory.setCategoryName("专一度");
            barChartCategory.setVal(0.5D);
            barChartCategories13.add(barChartCategory);
        }

        dungnutSeries.setChartCategoryList(barChartCategories13);
        dungnutChartSeries1.add(dungnutSeries);
        dungnutChart.setChartSeriesList(dungnutChartSeries1);
        fillSlideData42.initShapeData(2, (TableData)null, dungnutChart, (HashMap)null, (byte[])null);
        fillSlideData42.setSlidePage(42);
        slideDataMap.put(42, fillSlideData42);
    }

    private static void getSlideDataForPage1(Map<Integer, SlideData> slideDataMap) {
        SlideData fillSlideData1 = new SlideData();
        fillSlideData1.setSlidePage(1);
        HashMap<String, String> fillTextMap5 = new HashMap(1);
        fillTextMap5.put("car_model", "宝马");
        fillTextMap5.put("monthly|weekly_zh", "月度|周度");
        fillSlideData1.initShapeData(18, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
        HashMap<String, String> fillTextMap4 = new HashMap(1);
        fillTextMap4.put("monthly|weekly_en", "monthly|weekly_en");
        fillTextMap4.put("car_model_en", "baoma");
        fillSlideData1.initShapeData(17, (TableData)null, (ChartData)null, fillTextMap4, (byte[])null);
        HashMap<String, String> fillTextMap6 = new HashMap(1);
        fillTextMap6.put("generate_time", "2020-04-07");
        fillSlideData1.initShapeData(16, (TableData)null, (ChartData)null, fillTextMap6, (byte[])null);
        slideDataMap.put(1, fillSlideData1);
    }

    private static void getSlideDataForPage5(Map<Integer, SlideData> slideDataMap) throws ParseException {
        SlideData fillSlideData5 = new SlideData();
        HashMap<String, String> fillTextMap5 = new HashMap(1);
        fillTextMap5.put("area", "花花");
        fillSlideData5.initShapeData(1, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
        fillSlideData5.initShapeData(35, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
        HashMap<String, String> fillTextMapTime = new HashMap(1);
        String monthStr = DateUtils.getMonthStr("2020-01-03");
        String preMonthByDay = DateUtils.getPreMonthByDay("2020-01-03");
        String s = DateUtils.formatDateForMonth(monthStr);
        String s1 = DateUtils.formatDateForMonth(preMonthByDay);
        String fillTime = s1 + "&" + s;
        fillTextMapTime.put("time", fillTime);
        fillSlideData5.initShapeData(31, (TableData)null, (ChartData)null, fillTextMapTime, (byte[])null);
        ChartData barData = new ChartData();
        List<ChartSeries> barChartSeries = new ArrayList();
        ChartSeries barSeries1 = new ChartSeries();
        barSeries1.setSeriesName("系列 1");
        List<ChartCategory> barChartCategories = new ArrayList();

        for(int i = 0; i < 15; ++i) {
            ChartCategory barChartCategory = new ChartCategory();
            barChartCategory.setCategoryName("宝马3系" + i);
            barChartCategory.setVal((double)(16333 + i));
            barChartCategories.add(barChartCategory);
        }

        barSeries1.setChartCategoryList(barChartCategories);
        barChartSeries.add(barSeries1);
        barData.setChartSeriesList(barChartSeries);
        fillSlideData5.initShapeData(3, (TableData)null, barData, (HashMap)null, (byte[])null);
        ChartData barData2 = new ChartData();
        List<ChartSeries> barChartSeries2 = new ArrayList();
        ChartSeries barSeries2 = new ChartSeries();
        barSeries2.setSeriesName("系列 1");
        List<ChartCategory> barChartCategories2 = new ArrayList();

        for(int i = 0; i < 15; ++i) {
            ChartCategory barChartCategory = new ChartCategory();
            barChartCategory.setCategoryName("宝马3系" + i);
            barChartCategory.setVal((double)(16333 + i));
            barChartCategories2.add(barChartCategory);
        }

        barSeries2.setChartCategoryList(barChartCategories2);
        barChartSeries2.add(barSeries2);
        barData2.setChartSeriesList(barChartSeries2);
        fillSlideData5.initShapeData(5, (TableData)null, barData2, (HashMap)null, (byte[])null);
        ChartData scatterChartData = new ChartData();
        List<ChartSeries> scatterchartSeriesList1 = new ArrayList();

        for(int j = 0; j < 1; ++j) {
            ChartSeries scatterChartSeries = new ChartSeries();
            scatterChartSeries.setSeriesName("系列1" + j);
            List<ChartCategory> scatterchartCategoryList1 = new ArrayList();

            for(int i = 0; i < 15; ++i) {
                ChartCategory bubbleCategory = new ChartCategory();
                bubbleCategory.setCategoryName(i + "名字");
                bubbleCategory.setVal(Double.valueOf((double)(i * 3)));
                bubbleCategory.setOtherVal((double)(i * 3));
                scatterchartCategoryList1.add(bubbleCategory);
            }

            scatterChartSeries.setChartCategoryList(scatterchartCategoryList1);
            scatterchartSeriesList1.add(scatterChartSeries);
        }

        scatterChartData.setChartSeriesList(scatterchartSeriesList1);
        fillSlideData5.initShapeData(4, (TableData)null, scatterChartData, (HashMap)null, (byte[])null);
        ChartData scatterChartData2 = new ChartData();
        List<ChartSeries> scatterchartSeriesList2 = new ArrayList();

        for(int j = 0; j < 1; ++j) {
            ChartSeries scatterChartSeries = new ChartSeries();
            scatterChartSeries.setSeriesName("系列1" + j);
            List<ChartCategory> scatterchartCategoryList1 = new ArrayList();

            for(int i = 0; i < 15; ++i) {
                ChartCategory scatterCategory = new ChartCategory();
                scatterCategory.setCategoryName(i + "名字");
                scatterCategory.setVal(Double.valueOf((double)(i * 2)));
                scatterCategory.setOtherVal((double)(i * 2));
                scatterchartCategoryList1.add(scatterCategory);
            }

            scatterChartSeries.setChartCategoryList(scatterchartCategoryList1);
            scatterchartSeriesList2.add(scatterChartSeries);
        }

        scatterChartData2.setChartSeriesList(scatterchartSeriesList2);
        fillSlideData5.initShapeData(6, (TableData)null, scatterChartData2, (HashMap)null, (byte[])null);
        fillSlideData5.setSlidePage(5);
        slideDataMap.put(5, fillSlideData5);
    }

    private static void getSlideDataForPage15(Map<Integer, SlideData> slideDataMap) throws ParseException {
        SlideData fillSlideData15 = new SlideData();
        ChartData lineChartData = new ChartData();
        List<ChartSeries> chartSeriesList1 = new ArrayList();

        for(int j = 0; j < 4; ++j) {
            ChartSeries lineChartSeries = new ChartSeries();
            lineChartSeries.setSeriesName("奥迪A4L" + j);
            List<ChartCategory> chartCategoryList1 = new ArrayList();
            ChartCategory lineChartCategory = new ChartCategory();
            lineChartCategory.setCategoryName("2020-04-03");
            lineChartCategory.setVal(3638.0D);
            chartCategoryList1.add(lineChartCategory);
            lineChartSeries.setChartCategoryList(chartCategoryList1);
            chartSeriesList1.add(lineChartSeries);
        }

        lineChartData.setChartSeriesList(chartSeriesList1);
        fillSlideData15.setSlidePage(15);
        fillSlideData15.initShapeData(3, (TableData)null, lineChartData, (HashMap)null, (byte[])null);
    }

    private static void getSlideDataForPage16(Map<Integer, SlideData> slideDataMap) throws ParseException {
        for(int i = 0; i < 7; ++i) {
            SlideData fillSlideData16 = new SlideData();
            HashMap<String, String> fillTextMap5 = new HashMap(1);
            fillTextMap5.put("area", "东区");
            fillSlideData16.initShapeData(1, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
            fillSlideData16.initShapeData(10, (TableData)null, (ChartData)null, fillTextMap5, (byte[])null);
            TableData fillTable = new TableData();
            ArrayList<TableRowData> tableRowDataArrayList = new ArrayList(10);
            TableRowData carNameRowData = new TableRowData();
            ArrayList<String> carNameList = new ArrayList();
            carNameList.addAll(Arrays.asList("aa", "", "vv", "rr", "sss", "ss", "gg"));
            carNameRowData.setDataList(carNameList);
            TableRowData mounthNameRowData = new TableRowData();
            ArrayList<String> mounthList = new ArrayList();
            mounthList.addAll(Arrays.asList("一月", "二月", "三月", "", "", ""));
            mounthNameRowData.setDataList(mounthList);
            TableRowData rowData = new TableRowData();
            ArrayList<String> dataList = new ArrayList();
            dataList.addAll(Arrays.asList("11", "22", "33", "44", "55", "66", "77"));
            carNameRowData.setDataList(carNameList);
            mounthNameRowData.setDataList(mounthList);
            rowData.setDataList(dataList);
            tableRowDataArrayList.add(carNameRowData);
            tableRowDataArrayList.add(mounthNameRowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            tableRowDataArrayList.add(rowData);
            fillTable.setStartRowNum(0);
            fillTable.setStartColumn(0);
            fillTable.setSpecialFlag(true);
            Map<Integer, Integer> specialFlagLocation = new HashMap();
            specialFlagLocation.put(1, 0);
            rowData.setSpecialFlagLocation(specialFlagLocation);
            fillTable.setTableRowDataList(tableRowDataArrayList);
            fillSlideData16.initShapeData(6, fillTable, (ChartData)null, (HashMap)null, (byte[])null);
            HashMap<String, String> fillTextMapTime = new HashMap(1);
            String monthStr = DateUtils.getMonthStr("2020-01-03");
            String preMonthByDay = DateUtils.getPreMonthByDay("2020-01-03");
            String s = DateUtils.formatDateForMonth(monthStr);
            String s1 = DateUtils.formatDateForMonth(preMonthByDay);
            String fillTime = s1 + "&" + s;
            fillTextMapTime.put("time", fillTime);
            fillSlideData16.initShapeData(3, (TableData)null, (ChartData)null, fillTextMapTime, (byte[])null);
            fillSlideData16.setSlidePage(18 + i);
            slideDataMap.put(18 + i, fillSlideData16);
        }

    }

    private static void getSlideDataForPage25(Map<Integer, SlideData> slideDataMap) throws ParseException {
        SlideData fillSlideData25 = new SlideData();
        ChartData lineChartData131 = new ChartData();
        List<ChartSeries> chartSeriesList131 = new ArrayList();

        for(int j = 0; j < 7; ++j) {
            ChartSeries lineChartSeries = new ChartSeries();
            lineChartSeries.setSeriesName("奥迪A4L" + j);
            List<ChartCategory> chartCategoryList1 = new ArrayList();

            for(int i = 1; i <= 6; ++i) {
                ChartCategory lineChartCategory = new ChartCategory();
                lineChartCategory.setCategoryName("2020-04-03");
                lineChartCategory.setVal((double)((i + j) * 10000 * 2));
                chartCategoryList1.add(lineChartCategory);
            }

            lineChartSeries.setChartCategoryList(chartCategoryList1);
            chartSeriesList131.add(lineChartSeries);
        }

        lineChartData131.setChartSeriesList(chartSeriesList131);
        fillSlideData25.setSlidePage(25);
        fillSlideData25.initShapeData(4, (TableData)null, lineChartData131, (HashMap)null, (byte[])null);
    }
}
