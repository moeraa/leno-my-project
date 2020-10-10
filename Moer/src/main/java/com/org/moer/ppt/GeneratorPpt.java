package com.org.moer.ppt;

import com.org.moer.ppt.model.*;
import com.org.moer.ppt.util.Constants;
import com.org.moer.ppt.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.w3c.dom.Node;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.org.moer.ppt.util.PptConstants.*;


/**
 * @author chenxh
 * @date 2020/3/30  2:20 下午
 */
@Slf4j
public class GeneratorPpt {

    /**
     * @param xmlSlideShow ppt
     * @param slideData    页数据
     * @return void
     * @Description: <br>
     * 〈生成某页ppt〉
     * @date 2020/4/7 chenxh
     */
    public static void generatePptBySlideDataAndSlideShow(XMLSlideShow xmlSlideShow, SlideData slideData) throws IOException, InvalidFormatException, ParseException {
        List<XSLFSlide> slides = xmlSlideShow.getSlides();
        if (slides != null && slides.size() > 0) {
            Integer slidePage = slideData.getSlidePage();
            //获取指定页
            XSLFSlide xslfSlide = slides.get(slidePage - 1);
            //获取此页的所有数据信息
            for (Map.Entry<Integer, SlideData.ShapeData> shapeDataEntry : slideData.getShapeDataMap().entrySet()) {
                //形状编号
                Integer shapePage = shapeDataEntry.getKey();
                SlideData.ShapeData shapeData = shapeDataEntry.getValue();
                XSLFShape xslfShape = xslfSlide.getShapes().get(shapePage - 1);
                if (xslfShape != null) {
                    if (shapeData.getTableData() != null) {
                        log.info("生成第{}页的第{}个表", slideData.getSlidePage(), shapeData.getShapePage());
                        generateTableShape(shapeData, (XSLFTable) xslfShape);
                    }
                    if (shapeData.getTextMap() != null) {
                        log.info("生成第{}页的第{}个文本框", slideData.getSlidePage(), shapeData.getShapePage());
                        generateTextShape(shapeData, (XSLFTextShape) xslfShape);
                    }
                }
                List<POIXMLDocumentPart> relations = xslfSlide.getRelations();
                if (relations != null && relations.size() > 0) {
                    //替换指定位置的 图表  支持：柱状图，折线图，气泡图，散点图，环形图，雷达图，股价图
                    if (shapeData.getChartData() != null || shapeData.getChartDataList() != null) {
                        POIXMLDocumentPart documentPart = relations.get(shapePage - 1);
                        if (documentPart instanceof XSLFChart) {
                            log.info("生成第{}页的第{}个图表", slideData.getSlidePage(), shapeData.getShapePage());
                            XSLFChart xslfChart = (XSLFChart) documentPart;
                            generateChart(shapeData, xslfChart);
                        }
                    }
                    //替换指定位置的图片
                    if (shapeData.getPictureData() != null) {
                        POIXMLDocumentPart poixmlDocumentPart = relations.get(shapePage - 1);
                        if (poixmlDocumentPart instanceof XSLFPictureData) {
                            XSLFPictureData xslfPictureData = (XSLFPictureData) poixmlDocumentPart;
                            log.info("生成 第{}页的第{}个图片", xslfSlide.getSlideNumber(), shapeData.getShapePage());
                            replacePictureByAssignSlidePage(xmlSlideShow, shapeData, xslfPictureData);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param xmlSlideShow    指定页
     * @param shapeData       指定数据
     * @param xslfPictureData 此形状
     * @return void
     * @Description: <br>
     * 〈替换指定页，指定位置的图片〉
     * @date 2020/4/2 chenxh
     */
    private static void replacePictureByAssignSlidePage(XMLSlideShow xmlSlideShow, SlideData.ShapeData shapeData, XSLFPictureData xslfPictureData) throws IOException {
        if (shapeData.getPictureData() != null && shapeData.getPictureData().length > 0) {
            //设置图片
            xslfPictureData.setData(shapeData.getPictureData());
            log.info("ppt picture width " + xslfPictureData.getImageDimension().width);
            log.info("ppt picture height " + xslfPictureData.getImageDimension().height);
        } else {
            log.error("图片不存在");
        }

    }

    /**
     * @param shapeData 此图标的数据
     * @param xslfChart 此图标
     * @return void
     * @Description: <br>
     * 〈按照指定数据，替换指定图标〉
     * @date 2020/4/7 chenxh
     */
    private static void generateChart(SlideData.ShapeData shapeData, XSLFChart xslfChart) throws IOException, InvalidFormatException, ParseException {
        ChartData chartData = shapeData.getChartData();
        List<ChartData> chartDataList = shapeData.getChartDataList();
        boolean isNull = chartData != null || (chartDataList != null && chartDataList.size() > 0);
        if (isNull) {
            CTPlotArea plotArea = xslfChart.getCTChart().getPlotArea();
            List<CTBarChart> barChartList = plotArea.getBarChartList();
            List<CTLineChart> lineChartList = plotArea.getLineChartList();
            List<CTBubbleChart> bubbleChartList = plotArea.getBubbleChartList();
            List<CTScatterChart> scatterChartList = plotArea.getScatterChartList();
            List<CTDoughnutChart> doughnutChartList = plotArea.getDoughnutChartList();
            List<CTRadarChart> radarChartList = plotArea.getRadarChartList();
            List<CTValAx> valAxList = plotArea.getValAxList();
            List<CTStockChart> stockChartList = plotArea.getStockChartList();
            //  获取该图表的Excel数据 柱状图和折线图 创建新的sheet页，不沿用原来的sheet数据
            XSSFWorkbook workBook = xslfChart.getWorkbook();
            Sheet sheet = workBook.getSheetAt(0);
            //图表处理
            if (chartDataList != null && chartDataList.size() > 0) {
                if (barChartList != null && barChartList.size() > 0 && lineChartList != null && lineChartList.size() > 0) {
                    generatorBarAndLineGraph(xslfChart, chartDataList);
                }
            } else if (chartData.getChartSeriesList() != null) {
                List<ChartSeries> seriesDataList = chartData.getChartSeriesList();
                if (seriesDataList != null) {
                    if (barChartList != null && barChartList.size() > 0) {
                        generateBarChart(seriesDataList, barChartList, workBook);
                    } else if (lineChartList != null && lineChartList.size() > 0) {
                        generateLineChart(seriesDataList, lineChartList, workBook);
                    } else if (bubbleChartList != null && bubbleChartList.size() > 0) {
                        //特殊的气泡图处理
                        if (chartData.getSpecialChartIndex() != 0) {
                            int specialChartIndex = chartData.getSpecialChartIndex();
                            if (specialChartIndex == 1) {
                                log.info(" 周月报的ppt 类page53 特殊的 气泡图");
                                generateBubbleChartV1(seriesDataList, bubbleChartList, workBook, sheet);
                            }
                            if (specialChartIndex == 2) {
                                log.info(" 品牌促销ppt 类page5 特殊的 气泡图");
                                List<Map<String, Object>> specialValList = shapeData.getSpecialValList();
                                generateBubbleChartV2(seriesDataList, bubbleChartList, sheet, specialValList);
                            }

                        } else {
                            log.info(" 普通气泡图的生成 ");
                            generateBubbleChart(seriesDataList, bubbleChartList, workBook, sheet);

                        }
                    } else if (scatterChartList != null && scatterChartList.size() > 0) {
                        generateScatterChart(seriesDataList, scatterChartList, workBook, sheet);
                    } else if (doughnutChartList != null && doughnutChartList.size() > 0) {
                        generateDoughnut(seriesDataList, doughnutChartList, workBook);
                    } else if (radarChartList != null && radarChartList.size() > 0) {
                        generateRadarChart(seriesDataList, radarChartList, workBook);
                    } else if (stockChartList != null && stockChartList.size() > 0) {
                        generateStockChart(seriesDataList, stockChartList, workBook);
                    }
                }
            }
            //坐标轴横线处理
            //设置坐标轴的最大最小值单位等 属性
            if (shapeData.getCtValAxMap() != null && shapeData.getCtValAxMap().size() > 0) {
                Map<String, Double> ctValAxMap = shapeData.getCtValAxMap();
                log.info("测试坐标轴 {}", ctValAxMap);
                Double bDouble = 0D;
                Double lDouble = 0D;
                CTValAx btCValAx = valAxList.get(0);
                CTValAx lCtValAxt = valAxList.get(1);
                //坐标轴 线的处理
                if (btCValAx != null) {
                    log.info("btCValAx {}", btCValAx);
                    //  横坐标 设置线位置
                    btCValAx.getAxPos().setVal(STAxPos.B);
                    if (btCValAx.getCrossesAt() != null && ctValAxMap.get(STAxPos.B.toString()) != null) {
                        bDouble = ctValAxMap.get(STAxPos.B.toString());
                        //crossesAt
                        log.info("btCValAx old {}", btCValAx.getCrossesAt().getVal());
                        btCValAx.getCrossesAt().setVal(bDouble);
                        log.info("btCValAx new {}", btCValAx.getCrossesAt().getVal());
                    } else {
                        btCValAx.addNewCrossesAt().setVal(bDouble);
                    }
                    CTScaling bScaling = btCValAx.getScaling();

                    // X轴 最大最小值 处理
                    if (bScaling != null) {
                        if (ctValAxMap.get(B_SCALING_MAX) != null) {
                            CTDouble max = bScaling.getMax();
                            if (max != null) {
                                max.setVal(ctValAxMap.get(B_SCALING_MAX));
                            } else {
                                bScaling.addNewMax().setVal(ctValAxMap.get(B_SCALING_MAX));
                            }
                        }
                        if (ctValAxMap.get(B_SCALING_MIN) != null) {
                            CTDouble mix = bScaling.getMin();
                            if (mix != null) {
                                mix.setVal(ctValAxMap.get(B_SCALING_MIN));
                            } else {
                                bScaling.addNewMin().setVal(ctValAxMap.get(B_SCALING_MIN));
                            }
                        }
                    }
                    // X轴 单位 处理
                    CTAxisUnit bMajorUnit = btCValAx.getMajorUnit();
                    if (ctValAxMap.get(B_MAJOR_UNIT) != null) {
                        if (bMajorUnit != null) {
                            bMajorUnit.setVal(ctValAxMap.get(B_MAJOR_UNIT));
                        } else {
                            btCValAx.addNewMajorUnit().setVal(ctValAxMap.get(B_MAJOR_UNIT));
                        }
                    }

                }

                if (lCtValAxt != null) {
                    log.info("lCtValAxt {}", lCtValAxt);
                    // 纵坐标 设置线位置
                    lCtValAxt.getAxPos().setVal(STAxPos.L);
                    if (lCtValAxt.getCrossesAt() != null && ctValAxMap.get(STAxPos.L.toString()) != null) {
                        lDouble = ctValAxMap.get(STAxPos.L.toString());
                        log.info("lCtValAxt old {}", lCtValAxt.getCrossesAt().getVal());
                        lCtValAxt.getCrossesAt().setVal(lDouble);
                        log.info("lCtValAxt new {}", lCtValAxt.getCrossesAt().getVal());
                    } else {
                        lCtValAxt.addNewCrossesAt().setVal(lDouble);
                    }
                    CTScaling lScaling = lCtValAxt.getScaling();

                    if (ctValAxMap.get(L_SCALING_MAX) != null) {
                        CTDouble max = lScaling.getMax();
                        if (max != null) {
                            max.setVal(ctValAxMap.get(L_SCALING_MAX));
                        } else {
                            lScaling.addNewMax().setVal(ctValAxMap.get(L_SCALING_MAX));
                        }
                    }
                    if (ctValAxMap.get(L_SCALING_MIN) != null) {
                        CTDouble mix = lScaling.getMin();
                        if (mix != null) {
                            mix.setVal(ctValAxMap.get(L_SCALING_MIN));
                        } else {
                            lScaling.addNewMin().setVal(ctValAxMap.get(L_SCALING_MIN));
                        }
                    }

                    // Y轴 单位 处理
                    CTAxisUnit lMajorUnit = lCtValAxt.getMajorUnit();
                    if (ctValAxMap.get(L_MAJOR_UNIT) != null) {
                        if (lMajorUnit != null) {
                            lMajorUnit.setVal(ctValAxMap.get(L_MAJOR_UNIT));
                        } else {
                            lCtValAxt.addNewMajorUnit().setVal(ctValAxMap.get(L_MAJOR_UNIT));
                        }
                    }
                }
            }
            // 更新嵌入的workbook
            writeToGraphExcel(workBook, xslfChart);
        } else {
            log.error("此页的元素{} 不是一个图表 ", shapeData.getShapePage());
        }

    }

    private static void generatorBarAndLineGraph(XSLFChart chart, List<ChartData> chartDataList) throws IOException, InvalidFormatException {
        XSSFWorkbook workBook = chart.getWorkbook();
        for (int i = 0; i < chartDataList.size(); i++) {
            ChartData chartData = chartDataList.get(i);
            Sheet sheet = createGraphWorkBook(workBook, chartData.getChartSeriesList());
            CTPlotArea plotArea = chart.getCTChart().getPlotArea();
            // 获取折线图表
            CTLineChart lineChart = plotArea.getLineChartArray(0);
            // 获取柱状图表
            CTBarChart barChart = plotArea.getBarChartArray(0);
            // 获取图表的系列
            List<CTLineSer> lineSerList = lineChart.getSerList();
            List<CTBarSer> barSerList = barChart.getSerList();
            // 刷新柱状图数据
            if (Constants.SHAPE_BAR.equals(chartData.getShapeType())) {
                List<ChartSeries> chartSeriesListBar = chartData.getChartSeriesList();
                int colNum = 0;
                for (int j = 0; j < chartSeriesListBar.size(); j++) {
                    CTBarSer barSer = barSerList.get(j);
                    CTSerTx barTx = barSer.getTx();
                    CTAxDataSource barCat = barSer.getCat();
                    CTNumDataSource barVal = barSer.getVal();
                    ChartSeries chartSeries = chartSeriesListBar.get(j);
                    log.info("bar chartSeries {}", chartSeries);
                    colNum = j + 1;
                    refreshSeriesData(sheet, barTx, barCat, barVal, chartSeries, colNum);
                }
                //删除多余系列
                if (barSerList.size() > chartSeriesListBar.size()) {
                    for (int j = barSerList.size() - 1; j > chartSeriesListBar.size() - 1; j--) {
                        barChart.removeSer(j);
                    }
                }
            }
            // 刷新折线图的数据
            if (Constants.SHAPE_LINE.equals(chartData.getShapeType())) {
                List<ChartSeries> chartSeriesListLine = chartData.getChartSeriesList();
                int colNum = 0;
                for (int j = 0; j < chartSeriesListLine.size(); j++) {
                    colNum = j + 1;
                    ChartSeries chartSeries = chartSeriesListLine.get(j);
                    log.info("line chartSeries {}", chartSeries);
                    CTLineSer lineSer = lineSerList.get(j);
                    CTSerTx lineTx = lineSer.getTx();
                    CTAxDataSource lineCat = lineSer.getCat();
                    CTNumDataSource lineVal = lineSer.getVal();
                    refreshSeriesDataForLineChartForBar(sheet, lineTx, lineCat, lineVal, chartSeries, colNum);
                }
                //删除多余系列
                if (lineSerList.size() > chartSeriesListLine.size()) {
                    for (int j = lineSerList.size() - 1; j > chartSeriesListLine.size() - 1; j--) {
                        lineChart.removeSer(j);
                    }
                }
            }

        }

    }


    private static void generateDoughnut(List<ChartSeries> seriesDataList, List<CTDoughnutChart> doughnutChartList, XSSFWorkbook workBook) {
        log.info("-----------------生成环形图-----------------");
        XSSFSheet sheet = createGraphWorkBook(workBook, seriesDataList);
        for (CTDoughnutChart ctDoughnutChart : doughnutChartList) {
            List<CTPieSer> serList = ctDoughnutChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTPieSer ctPieSer = serList.get(i);
                CTAxDataSource cat = ctPieSer.getCat();
                CTSerTx tx = ctPieSer.getTx();
                CTNumDataSource val = ctPieSer.getVal();
                int colNum = i + 1;
                refreshSeriesData(sheet, tx, cat, val, seriesData, colNum);
            }
        }
    }

    private static void generateRadarChart(List<ChartSeries> seriesDataList, List<CTRadarChart> radarChartList, XSSFWorkbook workBook) {
        log.info("-----------------生成雷达图-----------------");
        XSSFSheet sheet = createGraphWorkBook(workBook, seriesDataList);
        for (CTRadarChart radarChart : radarChartList) {
            List<CTRadarSer> serList = radarChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTRadarSer ctRadarSer = serList.get(i);
                CTAxDataSource cat = ctRadarSer.getCat();
                CTSerTx tx = ctRadarSer.getTx();
                CTNumDataSource val = ctRadarSer.getVal();
                int colNum = i + 1;
                refreshSeriesData(sheet, tx, cat, val, seriesData, colNum);
            }
        }
    }

    private static void generateStockChart(List<ChartSeries> seriesDataList, List<CTStockChart> stockChartList, XSSFWorkbook workBook) {
        log.info("-----------------生成 股价图-----------------");
        XSSFSheet sheet = createGraphWorkBook(workBook, seriesDataList);
        for (CTStockChart ctStockChart : stockChartList) {
            List<CTLineSer> serList = ctStockChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTLineSer ctLineSer = serList.get(i);
                CTAxDataSource cat = ctLineSer.getCat();
                CTSerTx tx = ctLineSer.getTx();
                CTNumDataSource val = ctLineSer.getVal();
                int colNum = i + 1;
                refreshSeriesData(sheet, tx, cat, val, seriesData, colNum);
            }
        }
    }

    private static void generateScatterChart(List<ChartSeries> seriesDataList, List<CTScatterChart> scatterChartList, XSSFWorkbook workBook, Sheet sheet) {
        log.info("-----------------生成散点图-----------------");
        for (CTScatterChart scatterChart : scatterChartList) {
            List<CTScatterSer> serList = scatterChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTScatterSer scatterSer = serList.get(i);
                CTSerTx tx = scatterSer.getTx();
                CTAxDataSource xVal = scatterSer.getXVal();
                CTNumDataSource yVal = scatterSer.getYVal();
                List<CTExtension> infoLs = scatterSer.getExtLst().getExtList();
                CTDLbls dLbls = scatterSer.getDLbls();
                updateChartCatAndNumByScatter(sheet, seriesData, tx, xVal, yVal, infoLs, dLbls);
            }
        }
    }

    private static void generateBubbleChart(List<ChartSeries> seriesDataList, List<CTBubbleChart> bubbleChartList, XSSFWorkbook workBook, Sheet sheet) {
        log.info("-----------------生成气泡图-----------------");
        for (CTBubbleChart ctBubbleChart : bubbleChartList) {
            List<CTBubbleSer> serList = ctBubbleChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTBubbleSer ctBubbleSer = serList.get(i);
                CTSerTx tx = ctBubbleSer.getTx();
                CTAxDataSource xVal = ctBubbleSer.getXVal();
                CTNumDataSource yVal = ctBubbleSer.getYVal();
                CTNumDataSource bubbleSize = ctBubbleSer.getBubbleSize();
                int colNum = i + 1;
                CTDLbls dLbls = ctBubbleSer.getDLbls();
                CTExtensionList extLst = ctBubbleSer.getExtLst();
                refreshBubbleChartSeriesData(sheet, tx, xVal, yVal, bubbleSize, seriesData, colNum, dLbls, extLst);
            }
        }
    }


    /**
     * 创建并初始化工作簿.
     */
    private static XSSFSheet createGraphWorkBook(XSSFWorkbook workBook, List<ChartSeries> seriesDataList) {
        XSSFSheet newSheet = workBook.createSheet();
        XSSFCellStyle oldCellStyle = workBook.getSheetAt(0).getRow(0).getCell(0).getCellStyle();
        int startSize = seriesDataList.get(0).getChartCategoryList().size();
        for (int i = 0; i < seriesDataList.size(); i++) {
            ChartSeries chartSeries = seriesDataList.get(i);
            List<ChartCategory> chartCategoryList = chartSeries.getChartCategoryList();
            int size = chartCategoryList.size();
            if (startSize < size) {
                startSize = size;
            }
        }
        log.info("startSize：{}", startSize);
        for (int i = 0; i <= startSize; i++) {
            newSheet.createRow(i);
            for (int j = 0; j <= seriesDataList.size(); j++) {
                newSheet.getRow(i).createCell(j);
                if (j == 0) {
                    //设置 将第一行的格式替换成原图的格式
                    newSheet.getRow(i).createCell(j).setCellStyle(oldCellStyle);
                }
                log.info("------j:{}", j);
            }
        }

        return newSheet;
    }

    private static void generateLineChart(List<ChartSeries> seriesDataList, List<CTLineChart> lineChartList, XSSFWorkbook workBook) throws ParseException {
        log.info("-----------------生成折线图-----------------");
        //初始化sheet页
        XSSFSheet sheet = createGraphWorkBook(workBook, seriesDataList);
        for (CTLineChart ctLineChart : lineChartList) {
            List<CTLineSer> serList = ctLineChart.getSerList();

            //添加系列
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTLineSer ctLineSer = serList.get(i);
                //Legend
                CTSerTx tx = ctLineSer.getTx();
                //XAxis
                CTAxDataSource category = ctLineSer.getCat();
                //yAxis
                CTNumDataSource val = ctLineSer.getVal();
                int colNum = i + 1;
                List<ChartCategory> chartCategoryList = seriesData.getChartCategoryList();
                if (chartCategoryList != null && chartCategoryList.size() > 0) {
                    for (ChartCategory chartCategory : chartCategoryList) {
                        String categoryName = chartCategory.getCategoryName();
                        String diffDay = DateUtils.getDiffDay(categoryName);
                        chartCategory.setCategoryName(diffDay);
                    }
                }
                refreshSeriesDataForLineChart(sheet, tx, category, val, seriesData, colNum);
            }

            //删除多余系列
            if (serList.size() > seriesDataList.size()) {
                for (int i = serList.size() - 1; i > seriesDataList.size() - 1; i--) {
                    ctLineChart.removeSer(i);
                }
            }
        }
    }

    private static void generateBarChart(List<ChartSeries> seriesDataList, List<CTBarChart> barChartList, XSSFWorkbook workBook) throws IOException {
        XSSFSheet sheet = createGraphWorkBook(workBook, seriesDataList);
        for (CTBarChart ctBarChart : barChartList) {
            //图表的所有系列 serieata的数据要和serList一致
            List<CTBarSer> serList = ctBarChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTBarSer ctBarSer = serList.get(i);
                //更新系列名称 Legend
                CTSerTx tx = ctBarSer.getTx();
                //获取类别和值的数据源操作对象
                //XAxis
                CTAxDataSource category = ctBarSer.getCat();
                //yAxis
                CTNumDataSource val = ctBarSer.getVal();

                if (seriesData.getOther() != null) {
                    Integer other = (Integer) seriesData.getOther();
                    ctBarSer.getDPtList().get(0).getIdx().setVal(other.longValue());
                    log.info("-------位置{} ", other.doubleValue());
                }

                int colNum = i + 1;
                refreshSeriesData(sheet, tx, category, val, seriesData, colNum);
            }
            //删除多余系列
            if (serList.size() > seriesDataList.size()) {
                for (int i = serList.size() - 1; i > seriesDataList.size() - 1; i--) {
                    ctBarChart.removeSer(i);
                }
            }
        }
    }


    private final static String PATTERN = "(:\\$[A-Z]+\\$)(\\d+)";

    /**
     * 替换 形如： Sheet1!$A$2:$A$4 的字符
     *
     * @param range
     * @return
     */
    public static String replaceRowEnd(String range, long oldSize, long newSize) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(range);
        if (matcher.find()) {
            long old = Long.parseLong(matcher.group(2));
            return range.replaceAll(PATTERN, "$1" + Long.toString(old - oldSize + newSize));
        }
        return range;
    }

    /**
     * 更新 chart 的缓存数据
     *
     * @param data          数据
     * @param serTitle      系列的标题缓存
     * @param catDataSource 条目的数据缓存
     * @param numDataSource 数据的缓存
     *                      /*
     */

    protected static void updateChartCatAndNumByScatter(Sheet sheet, ChartSeries data, CTSerTx serTitle, CTAxDataSource catDataSource,
                                                        CTNumDataSource numDataSource, List<CTExtension> infoLs, CTDLbls dLbls) {

        long ptNumCnt = numDataSource.getNumRef().getNumCache().getPtCount().getVal();
        long ptCatCnt = catDataSource.getNumRef().getNumCache().getPtCount().getVal();

        int dataSize = data.getChartCategoryList().size();
        List<ChartCategory> chartCategoryList = data.getChartCategoryList();
        for (int i = 0; i < dataSize; i++) {
            ChartCategory chartCategory = chartCategoryList.get(i);
            CTNumVal cat = ptCatCnt > i ? catDataSource.getNumRef().getNumCache().getPtArray(i)
                    : catDataSource.getNumRef().getNumCache().addNewPt();
            cat.setIdx(i);
            cat.setV(String.format("%.2f", chartCategory.getVal()));
            CTNumVal val = ptNumCnt > i ? numDataSource.getNumRef().getNumCache().getPtArray(i)
                    : numDataSource.getNumRef().getNumCache().addNewPt();
            val.setIdx(i);
            val.setV(String.format("%.2f", (Double) chartCategory.getOtherVal()));
        }

        log.info("数据标签的填充");
        if (dLbls != null) {
            log.info("数据标签的填充  for ctdLbl");
            if (data != null) {
                int categorySize = data.getChartCategoryList().size();
                List<CTDLbl> dLblList = dLbls.getDLblList();
                if (dLblList != null && dLblList.size() > 0) {
                    for (int i = 0; i < dLblList.size(); i++) {
                        CTDLbl ctdLbl = dLblList.get(i);
                        if (ctdLbl.getTx() != null) {
                            CTTextBody rich = ctdLbl.getTx().getRich();
                            if (rich != null) {
                                if (rich.getPList() != null && rich.getPList().size() > 0) {
                                    for (int i1 = 0; i1 < rich.getPList().size(); i1++) {
                                        CTTextParagraph ctTextParagraph = rich.getPList().get(i1);
                                        if (ctTextParagraph != null) {
                                            if (ctTextParagraph.getRList() != null && ctTextParagraph.getRList().size() > 0) {
                                                for (int i2 = 0; i2 < ctTextParagraph.getRList().size(); i2++) {
                                                    CTRegularTextRun ctRegularTextRun = ctTextParagraph.getRList().get(i2);
                                                    if (i2 == 0) {
                                                        String t = ctRegularTextRun.getT();
                                                        String newT = "";
                                                        if (i < categorySize) {
                                                            if (data.getChartCategoryList().get(i).getOtherVal1() != null) {
                                                                newT = (String) data.getChartCategoryList().get(i).getCategoryName();
                                                            }
                                                            ctRegularTextRun.setT(newT);
                                                        } else {
                                                            ctRegularTextRun.setT("");
                                                        }
                                                        log.info(" old text = {} , new text = {}", t, newT);
                                                    } else {
                                                        ctRegularTextRun.setT("");
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }


        for (CTExtension info : infoLs) {

            int size = info.getDomNode().getChildNodes().getLength();
            for (int i = 0; i < size; i++) {
                //etx
                Node node = info.getDomNode().getChildNodes().item(i);
                int size1 = node.getChildNodes().getLength();
                for (int k = 0; k < size1; k++) {
                    //<c15:datalabelsRange>
                    Node node1 = node.getChildNodes().item(k);
                    int size2 = node1.getChildNodes().getLength();
                    for (int j = 0; j < size2; j++) {
                        //<c15:
                        Node node2 = node1.getChildNodes().item(j);
                        int size3 = node2.getChildNodes().getLength();
                        for (int l = 0; l < size3; l++) {
                            //<pt:
                            Node node3 = node2.getChildNodes().item(l);
                            for (int m = 0; m < size3; m++) {
                                //<pt:
                                Node node4 = node3.getChildNodes().item(l);
                                if (j <= dataSize) {
                                    String v = data.getChartCategoryList().get(j - 1).getCategoryName();
                                    node4.setNodeValue(v);
                                } else {
                                    node4.setNodeValue("");
                                    node3.removeChild(node4);
                                }
                            }
                            node3.getLocalName();


                        }
                    }
                }
            }


        }


        // 更新对应 excel 的range
        catDataSource.getNumRef().setF(replaceRowEnd(catDataSource.getNumRef().getF(),
                ptCatCnt,
                dataSize));
        numDataSource.getNumRef().setF(replaceRowEnd(numDataSource.getNumRef().getF(),
                ptNumCnt,
                dataSize));
        // 删除多的
        if (ptNumCnt > dataSize) {
            for (int idx = dataSize; idx < ptNumCnt; idx++) {
                catDataSource.getNumRef().getNumCache().removePt(dataSize);
                numDataSource.getNumRef().getNumCache().removePt(dataSize);
            }
        }
        // 更新个数
        catDataSource.getNumRef().getNumCache().getPtCount().setVal(dataSize);
        numDataSource.getNumRef().getNumCache().getPtCount().setVal(dataSize);
        refreshScatterChartExcelData(1, sheet, data, true);
    }

    private static void refreshSeriesDataForLineChartForBar(Sheet sheet, CTSerTx tx, CTAxDataSource category, CTNumDataSource val,
                                                            ChartSeries seriesData, int colNum) {

        //更新系列名称 Legend
        tx.getStrRef().getStrCache().getPtArray(0).setV(seriesData.getSeriesName());
        String titleRef = new CellReference(sheet.getSheetName(), 0, colNum, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);

        //系列值
        CTNumData ctNumData = val.getNumRef().getNumCache();
        ctNumData.setPtArray(null);
        //类别数据
        CTNumData categoryNumData = null;
        CTStrData categoryStrData = null;
        CTStrRef strRef = category.getStrRef();
        if (strRef == null) {
            categoryNumData = category.getNumRef().getNumCache();
            categoryNumData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryNumData, ctNumData, seriesData);
            categoryNumData.getPtCount().setVal(seriesData.getChartCategoryList().size());
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0)
                    .formatAsString(sheet.getSheetName(), true);
            category.getNumRef().setF(axisDataRange);

        } else {
            categoryStrData = category.getStrRef().getStrCache();
            categoryStrData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryStrData, ctNumData, seriesData);
            categoryStrData.getPtCount().setVal(seriesData.getChartCategoryList().size());
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0).formatAsString(sheet.getSheetName(), true);
            category.getStrRef().setF(axisDataRange);
            log.info("x:{}", axisDataRange);

        }
        //图数据绑定EXCEL表格数据
        ctNumData.getPtCount().setVal(seriesData.getChartCategoryList().size());
        String numDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), colNum, colNum).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        log.info("y:{}", numDataRange);
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        //更新Excel表格每一列的数据
        sheet.getRow(0).getCell(colNum).setCellValue(seriesData.getSeriesName());
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            sheet.getRow(i + 1).getCell(colNum).setCellValue(categoryData == null ? 0 : categoryData.getVal());
        }
        log.info("更新第 {} 列数据成功", colNum);
        if (colNum == 1) {
            // 初始化excel的类别那一列
            for (int i = 0; i < categoryDataList.size(); i++) {
                sheet.getRow(i + 1).getCell(0).setCellValue(categoryDataList.get(i).getCategoryName());
            }
            log.info("更新 excel 第一列值");
        }
    }

    private static void refreshSeriesDataForLineChart(Sheet sheet, CTSerTx tx, CTAxDataSource category, CTNumDataSource val,
                                                      ChartSeries seriesData, int colNum) {

        //更新系列名称 Legend
        tx.getStrRef().getStrCache().getPtArray(0).setV(seriesData.getSeriesName());
        String titleRef = new CellReference(sheet.getSheetName(), 0, colNum, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);

        //系列值
        CTNumData ctNumData = val.getNumRef().getNumCache();
        ctNumData.setPtArray(null);
        //类别数据
        CTNumData categoryNumData = null;
        CTStrData categoryStrData = null;
        CTStrRef strRef = category.getStrRef();
        if (strRef == null) {
            categoryNumData = category.getNumRef().getNumCache();
            categoryNumData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryNumData, ctNumData, seriesData);
            categoryNumData.getPtCount().setVal(seriesData.getChartCategoryList().size() - 1);
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0)
                    .formatAsString(sheet.getSheetName(), true);
            category.getNumRef().setF(axisDataRange);

        } else {
            categoryStrData = category.getStrRef().getStrCache();
            categoryStrData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryStrData, ctNumData, seriesData);
            categoryStrData.getPtCount().setVal(seriesData.getChartCategoryList().size() - 1);
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0).formatAsString(sheet.getSheetName(), true);
            category.getStrRef().setF(axisDataRange);
            log.info("x:{}", axisDataRange);

        }
        //图数据绑定EXCEL表格数据
        ctNumData.getPtCount().setVal(seriesData.getChartCategoryList().size() - 1);
        String numDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), colNum, colNum).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        log.info("y:{}", numDataRange);
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        //更新Excel表格每一列的数据
        sheet.getRow(0).getCell(colNum).setCellValue(seriesData.getSeriesName());
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            sheet.getRow(i + 1).getCell(colNum).setCellValue(categoryData == null ? 0 : categoryData.getVal());
        }
        log.info("更新第 {} 列数据成功", colNum);
        if (colNum == 1) {
            // 初始化excel的类别那一列
            for (int i = 0; i < categoryDataList.size(); i++) {
                sheet.getRow(i + 1).getCell(0).setCellValue(Double.valueOf(categoryDataList.get(i).getCategoryName()));
            }
            log.info("更新 excel 第一列值");
        }
    }

    private static void refreshSeriesData(Sheet sheet, CTSerTx tx, CTAxDataSource category, CTNumDataSource val,
                                          ChartSeries seriesData, int colNum) {

        //更新系列名称 Legend
        tx.getStrRef().getStrCache().getPtArray(0).setV(seriesData.getSeriesName());
        //绑定系列名为excel对应的列名   XAxis
        String titleRef = new CellReference(sheet.getSheetName(), 0, colNum, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);

        //系列值
        CTNumData ctNumData = val.getNumRef().getNumCache();


        ctNumData.setPtArray(null);
        //类别数据
        CTNumData categoryNumData = null;
        CTStrData categoryStrData = null;
        CTStrRef strRef = category.getStrRef();
        if (strRef == null) {
            categoryNumData = category.getNumRef().getNumCache();
            categoryNumData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryNumData, ctNumData, seriesData);
            categoryNumData.getPtCount().setVal(seriesData.getChartCategoryList().size());
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0)
                    .formatAsString(sheet.getSheetName(), true);
            category.getNumRef().setF(axisDataRange);
            log.info("x:{}", axisDataRange);

        } else {
            categoryStrData = category.getStrRef().getStrCache();
            categoryStrData.setPtArray(null);
            //更新图表数据
            refreshCategoryData(categoryStrData, ctNumData, seriesData);
            categoryStrData.getPtCount().setVal(seriesData.getChartCategoryList().size());
            String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0)
                    .formatAsString(sheet.getSheetName(), true);
            category.getStrRef().setF(axisDataRange);
            log.info("x:{}", axisDataRange);

        }


        //更新Excel表格每一列的数据
        refreshExcelData(colNum, sheet, seriesData);
        //图数据绑定EXCEL表格数据
        ctNumData.getPtCount().setVal(seriesData.getChartCategoryList().size());


        String numDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), colNum, colNum)
                .formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        log.info("y:{}", numDataRange);
    }

    /**
     * @param sheet
     * @param tx
     * @param category
     * @param val
     * @param seriesData
     * @param colNum
     * @return void
     * @Description: <br>
     * 〈刷新图表和表格的数据〉
     * @date 2020/3/31 chenxh
     */
    private static void refreshBubbleChartSeriesData(Sheet sheet, CTSerTx tx, CTAxDataSource category, CTNumDataSource val, CTNumDataSource bubbleSize,
                                                     ChartSeries seriesData, int colNum, CTDLbls ctdLbl, CTExtensionList extLst) {

        //更新系列名称 Legend
        //类别数据
        CTNumData categoryData = category.getNumRef().getNumCache();
        //系列值
        CTNumData ctNumData = val.getNumRef().getNumCache();
        CTNumData bubbleData = null;
        // 气泡大小
        if (bubbleSize != null && bubbleSize.getNumRef() != null && bubbleSize.getNumRef().getNumCache() != null) {
            bubbleData = bubbleSize.getNumRef().getNumCache();
            bubbleData.setPtArray(null);
        }
        int dataSize = seriesData.getChartCategoryList().size();
        log.info("数据标签的填充");
        if (ctdLbl != null) {
            log.info("数据标签的填充  for ctdLbl");
            fillDlblList(seriesData, ctdLbl);
        }
        if (extLst != null) {
            log.info("数据标签的填充  for extLst");
            fillextList(seriesData, extLst, dataSize);
        }

        categoryData.setPtArray(null);
        ctNumData.setPtArray(null);


        //更新图表数据
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory chartCategoryData = categoryDataList.get(i);
            //该系列的类别名字
            CTNumVal ctNucmVal1 = categoryData.addNewPt();
            ctNucmVal1.setIdx(i);
            ctNucmVal1.setV(chartCategoryData.getCategoryName());

            //该系列对应类别的值
            CTNumVal ctNumVal = ctNumData.addNewPt();
            ctNumVal.setIdx(i);
            ctNumVal.setV(String.valueOf(chartCategoryData.getVal()));
            //该系列的气泡大小
            if (bubbleData != null && bubbleSize.getNumRef() != null && bubbleSize.getNumRef().getNumCache() != null) {
                CTNumVal bubbleVal = bubbleData.addNewPt();
                bubbleVal.setIdx(i);
                Object otherVal = chartCategoryData.getOtherVal();
                if (otherVal != null) {
                    if (otherVal instanceof Integer) {
                        Integer bubbSize = (Integer) otherVal;
                        log.info("bubble size is {}", bubbSize.intValue());
                        bubbleVal.setV(String.valueOf(bubbSize));
                    }
                    if (otherVal instanceof Double) {
                        Double bubbSize = (Double) otherVal;
                        log.info("bubble size is {}", bubbSize.doubleValue());
                        bubbleVal.setV(String.valueOf(bubbSize));
                    }

                }

            }

        }
        log.info("更新图形的类别数据成功");

        //更新Excel表格每一列的数据
        refreshBubbleChartExcelData(colNum, sheet, seriesData, true);
        //图数据绑定EXCEL表格数据
        ctNumData.getPtCount().setVal(seriesData.getChartCategoryList().size() - 1);
        categoryData.getPtCount().setVal(seriesData.getChartCategoryList().size() - 1);

        String axisDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), 0, 0)
                .formatAsString(sheet.getSheetName(), true);
        category.getNumRef().setF(axisDataRange);
        log.info("x:{}", axisDataRange);
        String numDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), colNum, colNum)
                .formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        log.info("y:{}", numDataRange);
        if (bubbleSize != null && bubbleSize.getNumRef() != null && bubbleSize.getNumRef().getNumCache() != null) {
            String bubbleDataRange = new CellRangeAddress(1, seriesData.getChartCategoryList().size(), colNum + 1, colNum + 1)
                    .formatAsString(sheet.getSheetName(), true);
            bubbleSize.getNumRef().setF(bubbleDataRange);
            log.info("bubble:{}", bubbleDataRange);
        }
    }

    private static void fillextList(ChartSeries seriesData, CTExtensionList extLst, int dataSize) {
        if (extLst != null) {
            List<CTExtension> extList = extLst.getExtList();
            if (extList != null && extList.size() > 0) {
                for (CTExtension info : extList) {
                    int size = info.getDomNode().getChildNodes().getLength();
                    for (int i = 0; i < size; i++) {
                        //etx
                        Node node = info.getDomNode().getChildNodes().item(i);
                        int size1 = node.getChildNodes().getLength();
                        for (int k = 0; k < size1; k++) {
                            //<c15:datalabelsRange>
                            Node node1 = node.getChildNodes().item(k);
                            int size2 = node1.getChildNodes().getLength();
                            for (int j = 0; j < size2; j++) {
                                //<c15:
                                Node node2 = node1.getChildNodes().item(j);
                                int size3 = node2.getChildNodes().getLength();
                                for (int l = 0; l < size3; l++) {
                                    //<pt:
                                    Node node3 = node2.getChildNodes().item(l);
                                    for (int m = 0; m < size3; m++) {
                                        //<pt:
                                        Node node4 = node3.getChildNodes().item(l);
                                        if (j <= dataSize) {
                                            String v = (String) seriesData.getChartCategoryList().get(j - 1).getOtherVal1();
                                            node4.setNodeValue(v);
                                        } else {
                                            node4.setNodeValue("");
                                            node3.removeChild(node4);
                                        }
                                    }
                                    node3.getLocalName();


                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param seriesData
     * @param seriesCtlbl
     * @return void
     * @Description: <br>
     * 〈数据标签 填充〉
     * @date 2020/5/13 chenxh
     */
    private static void fillDlblList(ChartSeries seriesData, CTDLbls seriesCtlbl) {
        if (seriesCtlbl != null) {
            int categorySize = seriesData.getChartCategoryList().size();
            List<CTDLbl> dLblList = seriesCtlbl.getDLblList();
            if (dLblList != null && dLblList.size() > 0) {
                for (int i = 0; i < dLblList.size(); i++) {
                    CTDLbl ctdLbl = dLblList.get(i);
                    if (ctdLbl.getTx() != null) {
                        CTTextBody rich = ctdLbl.getTx().getRich();
                        if (rich != null) {
                            if (rich.getPList() != null && rich.getPList().size() > 0) {
                                for (int i1 = 0; i1 < rich.getPList().size(); i1++) {
                                    CTTextParagraph ctTextParagraph = rich.getPList().get(i1);
                                    if (ctTextParagraph != null) {
                                        if (ctTextParagraph.getRList() != null && ctTextParagraph.getRList().size() > 0) {
                                            for (int i2 = 0; i2 < ctTextParagraph.getRList().size(); i2++) {
                                                CTRegularTextRun ctRegularTextRun = ctTextParagraph.getRList().get(i2);
                                                if (i2 == 0) {
                                                    String t = ctRegularTextRun.getT();
                                                    String newT = "";
                                                    if (i < categorySize) {
                                                        if (seriesData.getChartCategoryList().get(i).getOtherVal1() != null) {
                                                            newT = (String) seriesData.getChartCategoryList().get(i).getOtherVal1();
                                                        }
                                                        ctRegularTextRun.setT(newT);
                                                    } else {
                                                        ctRegularTextRun.setT("");
                                                    }
                                                    log.info(" old text = {} , new text = {}", t, newT);
                                                } else {
                                                    ctRegularTextRun.setT("");
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private static void refreshScatterChartExcelData(int colNum, Sheet sheet, ChartSeries seriesData, boolean fillOtherVal) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        //更新Excel表格每一列的数据
        //如果系列名，可能会导致Excel出错
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            sheet.getRow(i + 1).getCell(colNum).setCellValue(categoryData.getVal());
        }
        if (fillOtherVal) {
            //支持多填充一列数据（）
            for (int i = 0; i < categoryDataList.size(); i++) {
                ChartCategory categoryData = categoryDataList.get(i);
                sheet.getRow(i + 1).getCell(colNum + 1).setCellValue((Double) categoryData.getOtherVal());
            }
        }
        log.info("更新第 {} 列数据成功", colNum);
        if (colNum == 1) {
            // 初始化excel的类别那一列
            for (int i = 0; i < categoryDataList.size(); i++) {
                String serName = categoryDataList.get(i).getCategoryName();
                sheet.getRow(i + 1).getCell(0).setCellValue(serName);
            }
            log.info("初始化excel 类别成功");
        }
    }


    private static void refreshBubbleChartExcelData(int colNum, Sheet sheet, ChartSeries seriesData, boolean fillOtherVal) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        //更新Excel表格每一列的数据
        //如果系列名，可能会导致Excel出错
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            sheet.getRow(i + 1).getCell(colNum).setCellValue(categoryData.getVal());
        }
        if (fillOtherVal) {
            //支持多填充一列数据（）
            for (int i = 0; i < categoryDataList.size(); i++) {
                ChartCategory categoryData = categoryDataList.get(i);
                Object otherVal = categoryData.getOtherVal();
                if (otherVal != null) {
                    if (otherVal instanceof Integer) {
                        Integer bubbSize = (Integer) otherVal;
                        sheet.getRow(i + 1).getCell(colNum + 1).setCellValue(bubbSize);
                    }
                    if (otherVal instanceof Double) {
                        Double bubbSize = (Double) otherVal;
                        sheet.getRow(i + 1).getCell(colNum + 1).setCellValue((bubbSize));
                    }
                }
                if (categoryData.getOtherVal1() != null) {
                    sheet.getRow(i + 1).getCell(colNum + 2).setCellValue((String) categoryData.getOtherVal1());
                }
                if (categoryData.getOtherVal2() != null) {
                    sheet.getRow(i + 1).getCell(colNum + 3).setCellValue((String) categoryData.getOtherVal2());
                }
                if (categoryData.getOtherVal3() != null) {
                    sheet.getRow(i + 1).getCell(colNum + 4).setCellValue((String) categoryData.getOtherVal3());
                }
                if (categoryData.getOtherVal4() != null) {
                    sheet.getRow(i + 1).getCell(colNum + 5).setCellValue((String) categoryData.getOtherVal4());
                }
            }
        }
        log.info("更新第 {} 列数据成功", colNum);
        if (colNum == 1) {
            // 初始化excel的类别那一列 气泡图 x轴 修改 替换
            for (int i = 0; i < categoryDataList.size(); i++) {
                String serName = categoryDataList.get(i).getCategoryName();
                sheet.getRow(i + 1).getCell(0).setCellValue(Double.valueOf(serName));
            }
            log.info("初始化excel 类别成功");
        }
    }

    /**
     * @param colNum
     * @param sheet
     * @param seriesData
     * @return void
     * @Description: <br>
     * 〈更新Excel表格每一列的数据,此处会影响ppt导出后的图表的excel数据〉
     * @date 2020/4/1 chenxh
     */
    private static void refreshExcelData(int colNum, Sheet sheet, ChartSeries seriesData) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        //更新Excel表格每一列的数据
        //如果系列名，可能会导致Excel出错
        sheet.getRow(0).getCell(colNum).setCellValue(seriesData.getSeriesName());
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            sheet.getRow(i + 1).getCell(colNum).setCellValue(categoryData == null ? 0 : categoryData.getVal());
        }
        log.info("更新第 {} 列数据成功", colNum);
        if (colNum == 1) {
            // 初始化excel的类别那一列
            for (int i = 0; i < categoryDataList.size(); i++) {
                String serName = categoryDataList.get(i).getCategoryName();
                sheet.getRow(i + 1).getCell(0).setCellValue(serName);
            }
            log.info("初始化excel 类别成功");
        }
    }

    /**
     * @param xAxisNumData
     * @param yAxisNumData
     * @param bubbleNumData
     * @param seriesData
     * @return void
     * @Description: <br>
     * 〈此处针对气泡图做的重载，原因：气泡图有气泡大小的特殊设置〉
     * @date 2020/4/1 chenxh
     */
    private static void refreshCategoryData(CTNumData xAxisNumData, CTNumData yAxisNumData, CTNumData
            bubbleNumData, ChartSeries seriesData) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            //该系列的类别名字
            CTNumVal ctNucmVal1 = xAxisNumData.addNewPt();
            ctNucmVal1.setIdx(i);
            ctNucmVal1.setV(categoryData.getCategoryName());

            //该系列对应类别的值
            CTNumVal ctNumVal = yAxisNumData.addNewPt();
            ctNumVal.setIdx(i);
            ctNumVal.setV(String.valueOf(categoryData.getVal()));
            //该系列的气泡大小
            CTNumVal bubbleVal = bubbleNumData.addNewPt();
            bubbleVal.setIdx(i);
            Integer bubbSize = (Integer) categoryData.getOtherVal();
            log.info("bubble size is {}", bubbSize.intValue());
            bubbleVal.setV(String.valueOf(bubbSize));
        }
        log.info("更新图形的类别数据成功");
    }

    private static void refreshCategoryData(CTNumData xAxisNumData, CTNumData yAxisNumData, ChartSeries seriesData) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            //该系列的类别名字
            CTNumVal xctNumVal = xAxisNumData.addNewPt();
            xctNumVal.setIdx(i);
            xctNumVal.setV(categoryData.getCategoryName());

            //该系列对应类别的值
            CTNumVal ctNumVal = yAxisNumData.addNewPt();
            ctNumVal.setIdx(i);
            ctNumVal.setV(String.valueOf(categoryData.getVal()));
        }
        log.info("更新图形的类别数据成功");
    }

    private static void refreshCategoryData(CTStrData xAxixStrData, CTNumData yAxisNumData, ChartSeries seriesData) {
        List<ChartCategory> categoryDataList = seriesData.getChartCategoryList();
        for (int i = 0; i < categoryDataList.size(); i++) {
            ChartCategory categoryData = categoryDataList.get(i);
            //该系列的类别名字
            CTStrVal ctStrVal = xAxixStrData.addNewPt();
            ctStrVal.setIdx(i);
            ctStrVal.setV(categoryData.getCategoryName());

            //该系列对应类别的值
            CTNumVal ctNumVal = yAxisNumData.addNewPt();
            ctNumVal.setIdx(i);
            ctNumVal.setV(String.valueOf(categoryData.getVal()));
        }
        log.info("更新图形的类别数据成功");
    }

    /**
     * @param workbook
     * @param chart
     * @return void
     * @Description: <br>
     * 〈将工作簿的内容写到对应的Excel中〉
     * @date 2020/3/31 chenxh
     */
    private static void writeToGraphExcel(Workbook workbook, XSLFChart chart) {
        OutputStream xlsOut = chart.getPackagePart().getOutputStream();
        try {
            workbook.write(xlsOut);
        } catch (IOException e) {
            log.info(" the stream for writeToGraphExcel fail fail", e);
        } finally {
        }
        log.info("表格嵌入图表并更新成功");
    }


    /**
     * @param shapePage 第几个形状
     * @param slideData 存储当前页的数据
     * @param slide     当前页的slide
     * @return void
     * @Description: <br>
     * 〈替换当前页的 的第几个形状的文本数据〉
     * @date 2020/3/30 chenxh
     */
    private static void generatoShapeBySildeAndShapePage(int shapePage, SlideData slideData, XSLFSlide slide) {
        if (slide != null) {
            //第几页的第几个文本框
            int slidePageNum = slide.getSlideNumber();
            log.info("第 {} 页 ppt 第{} 形状", slidePageNum, shapePage);
            List<XSLFShape> shapes = slide.getShapes();
            //填充指定的形状的数据
            if (shapes != null && shapes.size() > 0) {
                //构造数据数据不为空
                if (slideData != null) {
                    //获取指定的形状 shape
                    XSLFShape xslfShape = shapes.get(shapePage);
                    //如果是文本框
                    if (xslfShape instanceof XSLFTextShape) {
                        generateTextShape(shapePage, slideData, (XSLFTextShape) xslfShape);
                    } else if (xslfShape instanceof XSLFTable) {
                        generateTableShape(shapePage, slideData, (XSLFTable) xslfShape);
                    } else {
                        log.error("第 {} 页 ppt 的 第 {} 几个形状不是文本框 也不是 表格", slidePageNum, shapePage);
                    }
                } else {
                    log.error("第 {} 页 ppt 的 第 {} 个文本框 或者 表格的的填充数据为空", slidePageNum, shapePage);
                }
            } else {
                log.error("第 {} 页 ppt 没有任何形状", slidePageNum);
            }
        }
    }

    /**
     * @param slideData
     * @param slide
     * @return void
     * @Description: <br>
     * 〈按照构造的某页的ppt的数据，填充数据〉
     * @date 2020/3/31 chenxh
     */
    private static void generateShapeBySildeAndSlideData(SlideData slideData, XSLFSlide slide) {
        if (slide != null) {
            int slidePageNum = slide.getSlideNumber();
            int shapePage = 0;
            List<XSLFShape> shapes = slide.getShapes();
            //填充指定的形状的数据
            if (shapes != null && shapes.size() > 0) {
                //构造数据数据不为空
                if (slideData != null) {
                    Map<Integer, SlideData.ShapeData> shapeDataMap = slideData.getShapeDataMap();
                    if (shapeDataMap != null && shapeDataMap.size() > 0) {
                        for (Map.Entry<Integer, SlideData.ShapeData> entry : shapeDataMap.entrySet()) {
                            log.info("第 {} 页 ppt 第{} 形状", slidePageNum, entry.getKey());
                            shapePage = entry.getKey();
                            SlideData.ShapeData shapeData = entry.getValue();
                            XSLFShape xslfShape = shapes.get(shapePage);
                            //如果是文本框
                            if (xslfShape instanceof XSLFTextShape) {
                                generateTextShape(shapeData, (XSLFTextShape) xslfShape);
                            } else if (xslfShape instanceof XSLFTable) {
                                generateTableShape(shapeData, (XSLFTable) xslfShape);
                            } else {
                                log.error("第 {} 页 ppt 的 第 {} 几个形状不是文本框 也不是 表格", slidePageNum, shapePage);
                            }
                        }
                    }
                } else {
                    log.error("第 {} 页 ppt 的 第 {} 个文本框 或者 表格的的填充数据为空", slidePageNum, shapePage);
                }
            } else {
                log.error("第 {} 页 ppt 没有任何形状", slidePageNum);
            }
        }
    }


    /**
     * @param shapePage
     * @param slideData
     * @param xslfShape
     * @return void
     * @Description: <br>
     * 〈填充表格，当前页的第几个形状的表格  保留样式〉
     * @date 2020/3/30 chenxh
     */
    private static void generateTableShape(int shapePage, SlideData slideData, XSLFTable xslfShape) {
        XSLFTable xslfTable = xslfShape;
        //获取对应的table数据
        TableData tableData = slideData.getShapeDataMap().get(shapePage).getTableData();
        if (tableData != null) {
            List<XSLFTableRow> rows = xslfTable.getRows();
            int rowSize = rows.size();
            List<TableRowData> tableRowDataList = tableData.getTableRowDataList();
            for (int i = 0; i < tableRowDataList.size(); i++) {
                //为了保证样式的不变判断数据量比模板的row多的时候，不在原来表格基础上新增row
                // TODO: 2020/3/30  表格可能需要定制化，因为不同的表格，会有不同的表格样式，方式覆盖样式
                if (i < rowSize) {
                    // 不填充表头，及第一行数据
                    List<XSLFTableCell> cells = rows.get(i + 1).getCells();
                    List<String> cellDataList = tableRowDataList.get(i).getDataList();
                    for (int j = 0; j < cellDataList.size(); j++) {
                        String s = cellDataList.get(j);
                        for (XSLFTextParagraph textParagraph : cells.get(j).getTextParagraphs()) {
                            if (textParagraph != null) {
                                List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
                                for (XSLFTextRun textRun : textRuns) {
                                    //清空原来的数据
                                    textRun.setText("");
                                }
                                if (textRuns.size() > 0) {
                                    //设置对应文本的内容
                                    textRuns.get(0).setText(s);
                                    System.out.println("replace success");
                                }
                            }
                        }

                    }
                }
            }
            log.info("replace table success ");
            //清空我的配置文件（根据自己解析数据的业务清空配置文件）
            /*xslfTable.getCell(0, 0).setText("");*/
        } else {
            log.error("");
        }

    }

    /**
     * @param shapeData
     * @param xslfShape
     * @return void
     * @Description: <br>
     * 〈生成指定形状的数据〉
     * @date 2020/3/31 chenxh
     */
    private static void generateTableShape(SlideData.ShapeData shapeData, XSLFTable xslfShape) {
        XSLFTable xslfTable = xslfShape;
        //获取对应的table数据
        TableData tableData = shapeData.getTableData();
        //从第几行开始填充
        int startColumn = tableData.getStartColumn();
        //从第几列 开始填充
        int startRowNum = tableData.getStartRowNum();
        if (tableData != null) {
            List<XSLFTableRow> rows = xslfTable.getRows();
            int rowSize = rows.size();
            List<TableRowData> tableRowDataList = tableData.getTableRowDataList();
            for (int i = 0; i < tableRowDataList.size(); i++) {
                //含有特殊数据的 单元格数据
                if (tableData.isSpecialFlag() && tableRowDataList.get(i).getSpecialFlagLocation() != null && tableRowDataList.get(i).getSpecialFlagLocation().size() > 0) {
                    Map<Integer, Integer> specialFlagLocation = tableRowDataList.get(i).getSpecialFlagLocation();
                    if (i < rowSize) {
                        // 不填充表头，及第一行数据
                        List<XSLFTableCell> cells = rows.get(i + startRowNum).getCells();
                        List<String> cellDataList = tableRowDataList.get(i).getDataList();
                        for (int j = 0; j < cellDataList.size(); j++) {
                            //此列的数据
                            String cellText = cellDataList.get(j);
                            //此列的数据样式，颜色， 0；绿色，1 红色， 默认 黑色
                            Integer location = specialFlagLocation.get(j);
                            Color color = Color.BLACK;
                            if (location != null) {
                                switch (location) {
                                    case 0:
                                        color = new Color(35, 192, 70);
                                        break;
                                    case 1:
                                        color = new Color(255, 59, 75);
                                        break;
                                    default:
                                        color = Color.BLACK;
                                }
                            }
                            for (XSLFTextParagraph textParagraph : cells.get(j + startColumn).getTextParagraphs()) {
                                if (textParagraph != null) {
                                    List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
                                    for (XSLFTextRun textRun : textRuns) {
                                        //清空原来的数据
                                        textRun.setText("");
                                    }
                                    if (textRuns.size() > 0) {
                                        //设置对应文本的内容
                                        textRuns.get(0).setText(cellText);
                                        textRuns.get(0).setFontColor(color);

                                    }
                                }
                            }

                        }
                    }

                } else {
                    //为了保证样式的不变判断数据量比模板的row多的时候，不在原来表格基础上新增row
                    // TODO: 2020/3/30  表格可能需要定制化，因为不同的表格，会有不同的表格样式，方法保留原来文本的样式
                    if (i < rowSize) {
                        // 不填充表头，及第一行数据
                        List<XSLFTableCell> cells = rows.get(i + startRowNum).getCells();
                        List<String> cellDataList = tableRowDataList.get(i).getDataList();
                        for (int j = 0; j < cellDataList.size(); j++) {
                            String s = cellDataList.get(j);
                            for (XSLFTextParagraph textParagraph : cells.get(j + startColumn).getTextParagraphs()) {
                                if (textParagraph != null) {
                                    List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
                                    for (XSLFTextRun textRun : textRuns) {
                                        //清空原来的数据
                                        textRun.setText("");
                                    }
                                    if (textRuns.size() > 0) {
                                        //设置对应文本的内容
                                        textRuns.get(0).setText(s);
                                    }
                                }
                            }

                        }
                    }
                }

            }
            log.info("replace table success ");
            //清空我的配置文件（根据自己解析数据的业务清空配置文件）
            /*xslfTable.getCell(0, 0).setText("");*/
        } else {
            log.error("");
        }

    }

    private static void generateTextShape(int shapePage, SlideData slideData, XSLFTextShape xslfShape) {
        XSLFTextShape textShape = xslfShape;
        Map<String, String> textMap = slideData.getShapeDataMap().get(shapePage).getTextMap();
        for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
            //正则表达式匹配${}标识符的text
            String text = textParagraph.getText();
            List<String> keys = getMatchKeys(text);
            if (keys.size() > 0) {
                //替换所有的${}的数据
                for (String key : keys) {
                    String textKey = key.substring(2, key.length() - 1);
                    text = text.replace(key, textMap.get(textKey) == null ? " " : textMap.get(textKey));
                }
            }
            List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
            for (XSLFTextRun textRun : textRuns) {
                //清空原来的数据
                textRun.setText("");
            }
            if (textRuns.size() > 0) {
                //设置对应文本的内容
                textRuns.get(0).setText(text);
                System.out.println("replace success");
            }
        }
    }


    private static void generateTextShape(SlideData.ShapeData shapeData, XSLFTextShape xslfShape) {
        XSLFTextShape textShape = xslfShape;
        Map<String, String> textMap = shapeData.getTextMap();
        for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
            //正则表达式匹配${}标识符的text
            String text = textParagraph.getText();
            List<String> keys = getMatchKeys(text);
            if (keys.size() > 0) {
                //替换所有的${}的数据
                for (String key : keys) {
                    String textKey = key.substring(2, key.length() - 1);
                    text = text.replace(key, textMap.get(textKey) == null ? " " : textMap.get(textKey));
                }
            }
            List<XSLFTextRun> textRuns = textParagraph.getTextRuns();
            for (XSLFTextRun textRun : textRuns) {
                //清空原来的数据
                textRun.setText("");
            }
            if (textRuns.size() > 0) {
                //设置对应文本的内容
                textRuns.get(0).setText(text);
            }
        }
        log.info("replace text success ");
    }

    /**
     * @param text
     * @return java.util.List<java.lang.String>
     * @Description: <br>
     * 〈正则表达式匹配${}标识符的text〉
     * @date 2020/3/30 chenxh
     */
    private static List<String> getMatchKeys(String text) {
        String regex = "\\$\\{.*?\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> keys = new ArrayList<>();
        while (matcher.find()) {
            keys.add(matcher.group());
        }
        return keys;
    }

    /**
     * @param dataTime 时间
     * @param png      星云 地址
     * @return java.lang.String
     * @Description: <br>
     * 〈替换星云图地址〉
     * @date 2020/4/16 chenxh
     */
    public static String getCompareCloudPath(String dataTime, String png) {
        if (StringUtils.isNotEmpty(png)) {
            StringBuffer stringBuffer = new StringBuffer(png);
            return stringBuffer.replace(png.indexOf("{"), png.indexOf("}") + 1, dataTime).toString();
        }
        return "";
    }


    private static void generateBubbleChartV1(List<ChartSeries> seriesDataList, List<CTBubbleChart> bubbleChartList, XSSFWorkbook workBook, Sheet sheet) {
        log.info("-----------------生成气泡图 v2-----------------");
        for (CTBubbleChart ctBubbleChart : bubbleChartList) {
            List<CTBubbleSer> serList = ctBubbleChart.getSerList();
            ChartSeries seriesData = seriesDataList.get(0);
            int colNum1 = 1;
            //更新Excel表格每一列的数据
            refreshBubbleChartExcelData(colNum1, sheet, seriesData, true);
            log.info("刷新 page 53 excel success");
            Set<Object> carNameSet = new LinkedHashSet<>();
            for (ChartCategory chartCategory : seriesData.getChartCategoryList()) {
                carNameSet.add(chartCategory.getOtherVal2());
            }

            //按照 车型分组
            Map<Object, List<ChartCategory>> collect = seriesData.getChartCategoryList().stream().collect(Collectors.groupingBy(ChartCategory::getOtherVal2));
            if (collect != null && collect.size() > 0) {
                log.info("分组的结果 {}", collect);
                //计算sheet 的位置上
                int rowStart = 0;
                int rowEnd = 0;
                // 获取小的 minSize
                int collectSize = collect.size();
                int minSize = serList.size();
                int maxSize = collectSize;
                if (serList.size() > collectSize) {
                    minSize = maxSize;
                    maxSize = serList.size();
                }
                log.info("获取小的 minSize {} ", serList.size());
                int i = 0;

                for (Object carName : carNameSet) {
                    if (i > minSize) {
                        break;
                    }
                    List<ChartCategory> groupChartCategories = collect.get(carName);
                    int groupSize = groupChartCategories.size();
                    //挪动sheet的位置
                    rowStart = rowEnd + 1;
                    rowEnd += groupSize;
                    //替换结果值
                    CTBubbleSer ctBubbleSer1 = serList.get(i);
                    CTSerTx tx1 = ctBubbleSer1.getTx();
                    CTAxDataSource xVal1 = ctBubbleSer1.getXVal();
                    CTNumDataSource yVal1 = ctBubbleSer1.getYVal();
                    CTNumDataSource bubbleSize1 = ctBubbleSer1.getBubbleSize();
                    //更新系列名称 Legend
                    CTStrData txCtStrData = null;
                    if (tx1 != null && tx1.getStrRef() != null && tx1.getStrRef().getStrCache() != null) {
                        txCtStrData = tx1.getStrRef().getStrCache();
                        txCtStrData.setPtArray(null);
                    }
                    //类别数据
                    CTNumData categoryData = xVal1.getNumRef().getNumCache();
                    //系列值
                    CTNumData ctNumData = yVal1.getNumRef().getNumCache();
                    CTNumData bubbleData = null;
                    if (bubbleSize1 != null && bubbleSize1.getNumRef() != null && bubbleSize1.getNumRef().getNumCache() != null) {
                        bubbleData = bubbleSize1.getNumRef().getNumCache();
                        bubbleData.setPtArray(null);
                    }
                    categoryData.setPtArray(null);
                    ctNumData.setPtArray(null);
                    log.info("更新tx值 为 {}", (String) carName);
                    if (txCtStrData != null) {
                        CTStrVal ctStrVal = txCtStrData.addNewPt();
                        ctStrVal.setIdx(0);
                        ctStrVal.setV((String) carName);
                    }
                    //更新图表数据
                    log.info("开始 更新图形的类别数据");
                    for (int k = 0; k < groupChartCategories.size(); k++) {
                        ChartCategory chartCategoryData = groupChartCategories.get(k);
                        //该系列的类别名字
                        CTNumVal ctNucmVal1 = categoryData.addNewPt();
                        ctNucmVal1.setIdx(k);
                        ctNucmVal1.setV(chartCategoryData.getCategoryName());
                        //该系列对应类别的值
                        CTNumVal ctNumVal = ctNumData.addNewPt();
                        ctNumVal.setIdx(k);
                        ctNumVal.setV(String.valueOf(chartCategoryData.getVal()));
                        //该系列的气泡大小
                        if (bubbleSize1 != null && bubbleSize1.getNumRef() != null && bubbleSize1.getNumRef().getNumCache() != null) {
                            CTNumVal bubbleVal = bubbleData.addNewPt();
                            bubbleVal.setIdx(k);
                            if (chartCategoryData.getOtherVal() != null) {
                                Integer bubbSize = (Integer) chartCategoryData.getOtherVal();
                                log.info("bubble size is {}", bubbSize.intValue());
                                bubbleVal.setV(String.valueOf(bubbSize));
                            }
                        }
                    }
                    log.info("结束 更新图形的类别数据");


                    //图数据绑定EXCEL表格数据
                    ctNumData.getPtCount().setVal(groupChartCategories.size() - 1);
                    categoryData.getPtCount().setVal(groupChartCategories.size() - 1);


                    String numDataRange = new CellRangeAddress(rowStart, rowEnd, 1, 1)
                            .formatAsString(sheet.getSheetName(), true);
                    yVal1.getNumRef().setF(numDataRange);
                    log.info("y:{}", numDataRange);
                    String axisDataRange = new CellRangeAddress(rowStart, rowEnd, 0, 0)
                            .formatAsString(sheet.getSheetName(), true);
                    xVal1.getNumRef().setF(axisDataRange);
                    log.info("x:{}", axisDataRange);

                    if (bubbleSize1 != null && bubbleSize1.getNumRef() != null && bubbleSize1.getNumRef().getNumCache() != null) {
                        String bubbleDataRange = new CellRangeAddress(rowStart, rowEnd, 2, 2)
                                .formatAsString(sheet.getSheetName(), true);
                        bubbleSize1.getNumRef().setF(bubbleDataRange);
                        log.info("bubble:{}", bubbleDataRange);
                    }
                    if (tx1 != null && tx1.getStrRef() != null && tx1.getStrRef().getStrCache() != null) {
                        //绑定系列名为excel对应的列名   XAxis
                        String titleRef = new CellReference(sheet.getSheetName(), rowStart, 4, true, true).formatAsString();
                        tx1.getStrRef().setF(titleRef);
                        log.info("tx:{}", titleRef);
                    }

                    i++;
                }

                for (int k = minSize; k < maxSize; k++) {
                    log.info("删除未填充的数据");
                    ctBubbleChart.getSerList().remove(minSize);
                }
            }
        }
    }

    private static void generateBubbleChartV2(List<ChartSeries> seriesDataList, List<CTBubbleChart> bubbleChartList, Sheet sheet, List<Map<String, Object>> specialValList) {
        log.info("-----------------生成气泡图-----------------");
        for (CTBubbleChart ctBubbleChart : bubbleChartList) {
            List<CTBubbleSer> serList = ctBubbleChart.getSerList();
            for (int i = 0; i < seriesDataList.size(); i++) {
                ChartSeries seriesData = seriesDataList.get(i);
                CTBubbleSer ctBubbleSer = serList.get(i);
                CTSerTx tx = ctBubbleSer.getTx();
                CTAxDataSource xVal = ctBubbleSer.getXVal();
                CTNumDataSource yVal = ctBubbleSer.getYVal();
                CTNumDataSource bubbleSize = ctBubbleSer.getBubbleSize();
                int colNum = i + 1;
                if (specialValList != null && specialValList.size() > 0) {
                    //填充气泡图的气泡的颜色
                    fillSoildSrgColorForBubble(specialValList, ctBubbleSer);
                }
                CTDLbls dLbls = ctBubbleSer.getDLbls();
                CTExtensionList extLst = ctBubbleSer.getExtLst();
                refreshBubbleChartSeriesData(sheet, tx, xVal, yVal, bubbleSize, seriesData, colNum, dLbls, extLst);
            }
        }
    }

    private static void fillSoildSrgColorForBubble(List<Map<String, Object>> specialValList, CTBubbleSer ctBubbleSer) {
        for (int j = 0; j < specialValList.size(); j++) {
            Map<String, Object> solidFillMap = specialValList.get(j);
            if (solidFillMap != null && solidFillMap.size() > 0) {
                Color solidFillSrgbClr = (Color) solidFillMap.get(SOLID_FILL_SRGB_CLR);
                String serIdx = (String) solidFillMap.get(PPT_SER_IDX);
                //填充的值不为空
                if ((solidFillSrgbClr) != null && StringUtils.isNotEmpty(serIdx)) {
                    List<CTDPt> dPtList = ctBubbleSer.getDPtList();
                    //如果存在颜色的xml 则 修改改图形的值。否则不做修改，因此 图表填充颜色，需要原来的图标就有颜色
                    if (dPtList != null && dPtList.size() > 0) {
                        //以 填充的值为准
                        CTDPt ctdPt = dPtList.get(j);
                        if (ctdPt != null && ctdPt.getSpPr() != null && ctdPt.getSpPr().getSolidFill() != null) {
                            CTSRgbColor srgbClr = ctdPt.getSpPr().getSolidFill().getSrgbClr();
                            if (srgbClr != null) {
                                byte[] val = srgbClr.getVal();
                                log.info("old Val{}", val);
                                byte[] bytes = hexToByte(String.format("%02x%02x%02x", solidFillSrgbClr.getRed(), solidFillSrgbClr.getGreen(), solidFillSrgbClr.getBlue()));
                                srgbClr.setVal(bytes);
                                log.info("new idx = {} \t new Val{}", serIdx, bytes);
                                ctdPt.getIdx().setVal(Long.parseLong(serIdx));
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * hex转byte数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        int m = 0, n = 0;
        // 每两个字符描述为一个字节
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }
}
