package com.org.moer.ppt.yiche;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StopWatch;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxh
 * @date 2020/7/28  11:22 上午
 * @func
 */
public class ExcelTest {
    public static void main(String[] args) {
        String filePath = "/Users/bita/Library/Containers/com.tencent.xinWeChat/Data/Library/Application Support/com.tencent.xinWeChat/2.0b4.0.9/4978bb9997360b9221020f674f70d810/Message/MessageTemp/27bc46e6c1611747d3e865d7368788e8/File/会员数据.xlsx";
        StopWatch stopWatch = new StopWatch("整体");
        stopWatch.start();
        excelUtil(filePath);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
//        generateChartBySheet(sheetAt);

    }

    private static void excelUtil(String inputPath) {
        File file = new File(inputPath);
        XSSFWorkbook workbook = null;
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                List<DealerSerialUploadInput> list = CollUtil.newArrayList();
                list = ExcelUtil.parseExcel(fileInputStream,
                        null,
                        DealerSerialUploadInput::setId,
                        DealerSerialUploadInput::setFullName,
                        DealerSerialUploadInput::setShortName
//                        DealerSerialUploadInput::setAddress,
//                        DealerSerialUploadInput::setRegion,
//                        DealerSerialUploadInput::setProvinceId,
//                        DealerSerialUploadInput::setProvinceName,
//                        DealerSerialUploadInput::setCityId,
//                        null,
//                        null,
//                        DealerSerialUploadInput::setSerialId,
//                        DealerSerialUploadInput::setSerialName
                );
                System.out.println("list size = "+list.size());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        } else if (cell.getCellType() == CellType.STRING) {
            return StringUtils.trim(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.format(cell.getDateCellValue());
            }
            return String.valueOf((long) cell.getNumericCellValue());
        }
        return "";
    }

    private static void analysizeExcelAndSetValue(Sheet sheetAt) {
        List<String> list = new ArrayList();
        StringBuilder tableContent = new StringBuilder("表格内容为：\n");
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case STRING:
                        tableContent.append("|").append(cell.getStringCellValue());
                        list.add(cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        tableContent.append("|").append(cell.getStringCellValue());
                        list.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        tableContent.append("|").append(cell.getNumericCellValue());
                        list.add(String.valueOf(cell.getStringCellValue()));
                        break;
                    case FORMULA:
                        tableContent.append("|").append(cell.getCellFormula());
                        break;
                    case _NONE:
                        break; // 未知类型
                    case ERROR:
                        break; // 错误类型
                    case BLANK:
                        break; // 空白类型
                    default:
                        tableContent.append("default");

                }
            }
            tableContent.append("\n");

        }
        System.out.println("list size = " + list.size());
    }

    private static void generateChartBySheet(XSSFSheet sheetAt) {
        XSSFDrawing drawingPatriarch = sheetAt.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawingPatriarch.createAnchor(0, 0, 0, 0, 3, 1, 14, 20);
        XSSFChart chart = drawingPatriarch.createChart(anchor);
        //设置 legend
        XDDFChartLegend orAddLegend = chart.getOrAddLegend();
        orAddLegend.setPosition(LegendPosition.TOP);
        //设置 X轴
        XDDFCategoryAxis categoryAxis = chart.createCategoryAxis(AxisPosition.TOP);
        categoryAxis.setTitle("设置X轴");
        //设置Y轴
        XDDFValueAxis valueAxis = chart.createValueAxis(AxisPosition.LEFT);
        valueAxis.setTitle("设置Y轴");
        valueAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        XDDFChartData chartData = chart.createData(ChartTypes.BAR, categoryAxis, valueAxis);

        //数据列数：第一列为日期，其他列为对应数据
//        for (int col = 0; col < 4; col++) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 10, 0, 0);
        System.out.println("categroy：" + cellRangeAddress.toString());
        //横轴为车型名称
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheetAt, cellRangeAddress);
        CellRangeAddress dataCellRangeAddress = new CellRangeAddress(1, 10, 1, 1);
        System.out.println("values：" + dataCellRangeAddress.toString());
        //纵轴为各个数据
        XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheetAt, dataCellRangeAddress);
        XDDFBarChartData.Series series = (XDDFBarChartData.Series) chartData.addSeries(xs, ys);
        CellRangeAddress title = new CellRangeAddress(0, 0, 0, 0);
        CellReference cellReference = new CellReference(sheetAt.getSheetName(), 0, 0, true, true);
        series.setTitle(cellReference.formatAsString(), cellReference);
//        }


        System.out.println(chartData);
        System.out.println(chart);
        chart.plot(chartData);
        // in order to transform a bar chart into a column chart, you just need to change the bar direction
        XDDFBarChartData bar = (XDDFBarChartData) chartData;
        //设置柱子是彩色的
        bar.setVaryColors(true);
        bar.setBarDirection(BarDirection.BAR);

    }


    public static XSSFWorkbook inputPpt(String inputPath) {
        File file = new File(inputPath);
        XSSFWorkbook workbook = null;
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
            } else {
//                workbook = new XMLSlideShow();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }


    public static void outputExcel(XSSFWorkbook workbook, String outputPath) {
        System.out.println(outputPath);
        File file = new File(outputPath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
                assert fileOutputStream != null;
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
