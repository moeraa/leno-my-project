package com.moer.day.office;

import com.moer.day.office.util.PptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xslf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.File;
import java.util.List;

/**
 * @author chenxh
 * @date 2020/4/3 4:11 下午
 */
@Slf4j
public class AnalyticPpt {
    public static void main(String[] args) {

        //读取一个ppt
        String inputPath = System.getProperty("user.dir") +
                File.separator + "/src/main/resources/tmpl/V2.pptx";
        log.info("ppt path :{}", inputPath);
        XMLSlideShow ppt = PptUtils.inputPpt(inputPath);
        //删除指定ppt

        if (ppt != null) {

            List<XSLFSlide> slides = ppt.getSlides();
            int page = 28;
            shapeInfo(slides.get(page - 1));
            chartInfo(slides.get(page - 1));

            for (int i = 0; i < slides.size(); i++) {
                shapeInfo(slides.get(i));
                chartInfo(slides.get(i));
            }

        }
    }


    /**
     * @param xslfSlide
     * @return void
     * @Description: <br>
     * 〈打印文本框和表格的内容〉
     * @date 2020/3/30 chenxh
     */
    public static void shapeInfo(XSLFSlide xslfSlide) {
        int slideNum = xslfSlide.getSlideNumber();
        int tableNumber = 0;
        int textNumber = 0;
        //获取每一页的幻灯片的所有元素
        List<XSLFShape> shapes = xslfSlide.getShapes();
        String format = String.format("第 %d 页的幻灯片 形 状的数量是 %s", slideNum, shapes.size());
        log.info(format);
        //获取每页的形状详情
        for (int j = 0; j < shapes.size(); j++) {
            //遍历所有形状；
            XSLFShape xslfShape = shapes.get(j);
            String shapeInfo = "shape name =" + xslfShape.getShapeName() + " shape id = " + xslfShape.getShapeId() + " getPlaceholder = " + xslfShape.getPlaceholder();
            log.info(shapeInfo);
//                //文本框的内容
            if (xslfShape instanceof XSLFTextShape) {
                textNumber++;
                printTextContent(slideNum, textNumber, (XSLFTextShape) xslfShape);
            }
            //表格的内容
            if (xslfShape instanceof XSLFTable) {
                tableNumber++;
                printTableContent(slideNum, tableNumber, (XSLFTable) xslfShape);
            }
        }

    }


    /**
     * @param xslfSlide
     * @return void
     * @Description: <br>
     * 〈打印PPT中的图表内容  柱状图、折线图、气泡图、散点图、环形图〉
     * @date 2020/3/30 chenxh
     */
    public static void chartInfo(XSLFSlide xslfSlide) {
        //ppt的页数
        int slideNum = xslfSlide.getSlideNumber();
        List<POIXMLDocumentPart> documentParts = xslfSlide.getRelations();
        String format = String.format("第 %d 页的幻灯片relation的数量是 %s", slideNum, documentParts.size());
        log.info(format);
        int barChartNumber = 0;
        int lineChartNumber = 0;
        int scatterChartNumber = 0;
        int bubbleChartNumber = 0;
        int perChatNumber = 0;
        int radarChatNumber = 0;
        int stockChatNumber = 0;
        int picNumber = 0;
        for (int j = 0; j < documentParts.size(); j++) {
            POIXMLDocumentPart documentPart = documentParts.get(j);
            // 如果是图表，打印图表的内容
            if (documentPart instanceof XSLFChart) {
                XSLFChart xslfChart = (XSLFChart) documentPart;
                //获取图表的数据
                CTPlotArea plotArea = xslfChart.getCTChart().getPlotArea();
                //柱状
                if (plotArea.getBarChartList().size() != 0) {
                    barChartNumber++;
                    barChartNumber = getBarChartContent(slideNum, barChartNumber, plotArea);
                }
                //折线
                if (plotArea.getLineChartList().size() != 0) {
                    lineChartNumber++;
                    lineChartNumber = getLineChartContent(slideNum, lineChartNumber, plotArea);
                }
                //散点图
                if (plotArea.getScatterChartList().size() != 0) {
                    scatterChartNumber++;
                    scatterChartNumber = getScatterChartContent(slideNum, scatterChartNumber, plotArea);

                }
                //气泡图
                if (plotArea.getBubbleChartList().size() != 0) {
                    bubbleChartNumber++;
                    bubbleChartNumber = getBubbleChartContent(slideNum, bubbleChartNumber, plotArea);
                }
                if (plotArea.getDoughnutChartList().size() != 0) {
                    //环形图
                    perChatNumber = getPerChartContent(slideNum, perChatNumber, plotArea);

                }
                if (plotArea.getRadarChartList().size() != 0) {
                    radarChatNumber = getRadarChartContent(slideNum, radarChatNumber, plotArea);
                }
                if (plotArea.getStockChartList().size() != 0) {
                    stockChatNumber = getStockChartContent(slideNum, stockChatNumber, plotArea);
                }
            }
            if (documentPart instanceof XSLFPictureData) {
                picNumber++;
                log.info("---------------------分析环形图 第 {} 页，第 {} 张 图片----------------------", slideNum, picNumber);
                XSLFPictureData xslfPictureData = (XSLFPictureData) documentPart;
                log.info("图片名称 {}的content type {}", xslfPictureData.getFileName(), xslfPictureData.getContentType());
            }

        }

    }

    private static int getStockChartContent(int slideNum, int perChatNumber, CTPlotArea plotArea) {
        perChatNumber++;
        List<CTStockChart> stockChartList = plotArea.getStockChartList();
        log.info("---------------------分析股价图 第 {} 页，第 {} 张 ---------------------", slideNum, perChatNumber);
        for (CTStockChart ctStockChart : stockChartList) {
            List<CTLineSer> serList = ctStockChart.getSerList();
            for (CTLineSer ctLineSer : serList) {
                String ctBarSerInfo = String.format("ctBarSerInfo val = %s ,tx = %s ，cat = %s", ctLineSer.getVal(), ctLineSer.getTx(), ctLineSer.getCat());
                StringBuilder legend = new StringBuilder("股价图 Legend 轴的值 = ");
                for (CTStrVal ctStrVal : ctLineSer.getTx().getStrRef().getStrCache().getPtList()) {
                    legend.append("| ").append(ctStrVal.getV());
                }
                legend.append("|");
                log.info(legend.toString());
                StringBuilder xAxis = new StringBuilder("股价图 XAxis 轴的值 = ");
                for (CTStrVal ctStrVal : ctLineSer.getCat().getStrRef().getStrCache().getPtList()) {
                    xAxis.append("| ").append(ctStrVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder yAxis = new StringBuilder("股价图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctLineSer.getVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());
            }
        }
        return perChatNumber;
    }

    private static int getRadarChartContent(int slideNum, int perChatNumber, CTPlotArea plotArea) {
        perChatNumber++;
        List<CTRadarChart> doughnutChartList = plotArea.getRadarChartList();
        log.info("---------------------分析雷达图 第 {} 页，第 {} 张 环形图----------------------", slideNum, perChatNumber);
        for (CTRadarChart ctRadarChart : doughnutChartList) {
            List<CTRadarSer> serList = ctRadarChart.getSerList();
            for (CTRadarSer ctRadarSer : serList) {
                String ctBarSerInfo = String.format("ctBarSerInfo val = %s ,tx = %s ，cat = %s", ctRadarSer.getVal(), ctRadarSer.getTx(), ctRadarSer.getCat());
                StringBuilder legend = new StringBuilder("雷达图 Legend 轴的值 = ");
                for (CTStrVal ctStrVal : ctRadarSer.getTx().getStrRef().getStrCache().getPtList()) {
                    legend.append("| ").append(ctStrVal.getV());
                }
                legend.append("|");
                log.info(legend.toString());
                StringBuilder xAxis = new StringBuilder("雷达图 XAxis 轴的值 = ");
                for (CTStrVal ctStrVal : ctRadarSer.getCat().getStrRef().getStrCache().getPtList()) {
                    xAxis.append("| ").append(ctStrVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder yAxis = new StringBuilder("雷达图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctRadarSer.getVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());
            }
        }
        return perChatNumber;
    }

    private static int getPerChartContent(int slideNum, int perChatNumber, CTPlotArea plotArea) {
        perChatNumber++;
        List<CTDoughnutChart> doughnutChartList = plotArea.getDoughnutChartList();
        log.info("---------------------分析环形图 第 {} 页，第 {} 张 环形图----------------------", slideNum, perChatNumber);
        for (CTDoughnutChart ctDoughnutChart : doughnutChartList) {
            List<CTPieSer> serList = ctDoughnutChart.getSerList();
            for (CTPieSer ctPieSer : serList) {
                String ctBarSerInfo = String.format("ctBarSerInfo val = %s ,tx = %s ，cat = %s", ctPieSer.getVal(), ctPieSer.getTx(), ctPieSer.getCat());
                StringBuilder legend = new StringBuilder("环形图 Legend 轴的值 = ");
                for (CTStrVal ctStrVal : ctPieSer.getTx().getStrRef().getStrCache().getPtList()) {
                    legend.append("| ").append(ctStrVal.getV());
                }
                legend.append("|");
                log.info(legend.toString());
                StringBuilder xAxis = new StringBuilder("环形图 XAxis 轴的值 = ");
                for (CTStrVal ctStrVal : ctPieSer.getCat().getStrRef().getStrCache().getPtList()) {
                    xAxis.append("| ").append(ctStrVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder yAxis = new StringBuilder("环形图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctPieSer.getVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());
            }
        }
        return perChatNumber;
    }


    /**
     * @param slideNum
     * @param bubbleChartNumber
     * @param plotArea
     * @return int
     * @Description: <br>
     * 〈获取气泡图的内容〉
     * @date 2020/3/27 chenxh
     */
    private static int getBubbleChartContent(int slideNum, int bubbleChartNumber, CTPlotArea plotArea) {

        log.info("---------------------分析气泡图 第 {} 页，第 {} 张 气泡图----------------------", slideNum, bubbleChartNumber);
        for (CTBubbleChart ctBubbleChart : plotArea.getBubbleChartList()) {
            for (CTBubbleSer ctBubbleSer : ctBubbleChart.getSerList()) {
                System.out.println(ctBubbleSer.getExtLst());
//                ctBubbleSer.addNewExtLst();
                StringBuilder legend = new StringBuilder("气泡图 Legend 轴的值 = ");
                if (ctBubbleSer.getTx() != null) {
                    for (CTStrVal ctStrVal : ctBubbleSer.getTx().getStrRef().getStrCache().getPtList()) {
                        legend.append("| ").append(ctStrVal.getV());
                    }
                }
                legend.append("|");
                log.info(legend.toString());
                StringBuilder yAxis = new StringBuilder("气泡图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctBubbleSer.getYVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());

                StringBuilder xAxis = new StringBuilder("气泡图 XAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctBubbleSer.getXVal().getNumRef().getNumCache().getPtList()) {
                    xAxis.append("| ").append(ctNumVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder bubbleSize = new StringBuilder("气泡图 bubbleSize 的值 = ");
                if (ctBubbleSer.getBubbleSize().getNumRef() != null) {
                    for (CTNumVal ctNumVal : ctBubbleSer.getBubbleSize().getNumRef().getNumCache().getPtList()) {
                        bubbleSize.append("|").append(ctNumVal.getV());
                    }
                }
                bubbleSize.append("|");
                log.info(bubbleSize.toString());
            }
        }
        return bubbleChartNumber;
    }

    private static int getScatterChartContent(int slideNum, int scatterChartNumber, CTPlotArea plotArea) {
        log.info("---------------------分析散点图 第 {} 页，第 {} 张 散点图----------------------", slideNum, scatterChartNumber);
        for (CTScatterChart scatterChart : plotArea.getScatterChartList()) {
            List<CTScatterSer> serList = scatterChart.getSerList();
            for (CTScatterSer scatterSer : serList) {
                System.out.println(scatterSer.getExtLst());
                StringBuilder legend = new StringBuilder("散点图 Legend 轴的值 = ");
                if (scatterSer.getTx() != null) {
                    for (CTStrVal ctStrVal : scatterSer.getTx().getStrRef().getStrCache().getPtList()) {
                        legend.append("| ").append(ctStrVal.getV());
                    }
                }
                legend.append("|");
                log.info(legend.toString());

                StringBuilder yAxis = new StringBuilder("散点图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : scatterSer.getYVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());

                StringBuilder xAxis = new StringBuilder("散点图 XAxis 轴的值 = ");
                for (CTNumVal ctNumVal : scatterSer.getXVal().getNumRef().getNumCache().getPtList()) {
                    xAxis.append("| ").append(ctNumVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
            }
        }
        return scatterChartNumber;
    }

    private static int getLineChartContent(int slideNum, int lineCharNumber, CTPlotArea plotArea) {

        log.info("---------------------分析折线图 第 {} 页，第 {} 张 折线图----------------------", slideNum, lineCharNumber);
        //折线
        for (CTLineChart ctLineChart : plotArea.getLineChartList()) {
            for (CTLineSer ctLineSer : ctLineChart.getSerList()) {
                for (CTStrVal ctStrVal : ctLineSer.getTx().getStrRef().getStrCache().getPtList()) {
                    log.info("折线图的 Legend 值  = " + ctStrVal.getV());
                }
                StringBuilder xAxis = new StringBuilder("折线图的 XAxis 轴的值 = ");
                if (ctLineSer.getCat() != null && ctLineSer.getCat().getNumRef() != null) {
                    for (CTNumVal ctNumVal : ctLineSer.getCat().getNumRef().getNumCache().getPtList()) {
                        xAxis.append("| ").append(ctNumVal.getV());
                    }
                } else if (ctLineSer.getCat() != null && ctLineSer.getCat().getStrRef() != null) {
                    for (CTStrVal ctStrVal : ctLineSer.getCat().getStrRef().getStrCache().getPtList()) {
                        xAxis.append("| ").append(ctStrVal.getV());
                    }
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder yAxis = new StringBuilder("折线图的 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctLineSer.getVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());
            }
        }
        return lineCharNumber;
    }

    private static int getBarChartContent(int slideNum, int barCharNumber, CTPlotArea plotArea) {
        log.info("---------------------分析柱状图 第 {} 页，第 {} 张 柱状图----------------------", slideNum, barCharNumber);
        //柱状
        List<CTBarChart> barChartList = plotArea.getBarChartList();
        for (CTBarChart ctBarChart : barChartList) {
            List<CTBarSer> serList = ctBarChart.getSerList();
            for (CTBarSer ctBarSer : serList) {
                StringBuilder legend = new StringBuilder("柱状图 Legend 轴的值 = ");
                for (CTStrVal ctStrVal : ctBarSer.getTx().getStrRef().getStrCache().getPtList()) {
                    legend.append("| ").append(ctStrVal.getV());
                }
                legend.append("|");
                log.info(legend.toString());
                StringBuilder xAxis = new StringBuilder("柱状图 XAxis 轴的值 = ");
                for (CTStrVal ctStrVal : ctBarSer.getCat().getStrRef().getStrCache().getPtList()) {
                    xAxis.append("| ").append(ctStrVal.getV());
                }
                xAxis.append("|");
                log.info(xAxis.toString());
                StringBuilder yAxis = new StringBuilder("柱状图 yAxis 轴的值 = ");
                for (CTNumVal ctNumVal : ctBarSer.getVal().getNumRef().getNumCache().getPtList()) {
                    yAxis.append("| ").append(ctNumVal.getV());
                }
                yAxis.append("|");
                log.info(yAxis.toString());
            }
        }
        return barCharNumber;
    }

    /**
     * @param slideNum
     * @param textNumber
     * @param xslfShape
     * @return void
     * @Description: <br>
     * 〈打印文本框的内容，有些图形可能没有内容〉
     * @date 2020/3/30 chenxh
     */
    private static void printTextContent(int slideNum, int textNumber, XSLFTextShape xslfShape) {
        log.info("---------------------分析文本框 第 {} 页，第 {} 个文本框----------------------", slideNum, textNumber);
        XSLFTextShape textShape = xslfShape;
        StringBuilder textContent = new StringBuilder("文本框内容为：\n");
        for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
            textContent.append(textParagraph.getText());
        }
        textContent.append("\n");
        log.info(textContent.toString());
    }

    /**
     * @param slideNum
     * @param tableNumber
     * @param xslfShape
     * @return void
     * @Description: <br>
     * 〈打印表格的内容〉
     * @date 2020/3/30 chenxh
     */
    private static void printTableContent(int slideNum, int tableNumber, XSLFTable xslfShape) {
        log.info("---------------------分析 表格第 {} 页，第 {} 张表格----------------------", slideNum, tableNumber);
        XSLFTable xslfTable = xslfShape;
        StringBuilder tableContent = new StringBuilder("表格内容为：\n");
        for (XSLFTableRow row : xslfTable.getRows()) {
            for (XSLFTableCell cell : row.getCells()) {
                tableContent.append("|").append(cell.getText());
            }
            tableContent.append("\n");
        }
        log.info(tableContent.toString());
    }
}
