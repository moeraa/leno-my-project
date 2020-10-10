package com.org.moer.ppt;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

/**
 * @author chenxh
 * @date 2020/7/28  11:22 上午
 * @func
 */
public class ExcelTest {
    public static void main(String[] args) {
        String filePath = "/Users/bita/Documents/code/a_yiche/new_index_ppt_1/ppt/src/main/resources/tmpl/区域数据模板初稿-20200713.xlsx";

        XSSFWorkbook workbook = inputPpt(filePath);
        XSSFSheet sheetAt = workbook.getSheetAt(2);
        generateChartBySheet(sheetAt);
        //analysizeExcelAndSetValue(sheetAt);
        outputExcel(workbook, "/Users/bita/Documents/code/a_yiche/new_index_ppt_1/ppt/src/main/resources/tmpl/aa.xlsx");

    }

    private static void analysizeExcelAndSetValue(Sheet sheetAt) {
        StringBuilder tableContent = new StringBuilder("表格内容为：\n");
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case STRING:
                        tableContent.append("|").append(cell.getStringCellValue());
                        cell.setCellValue("花花");
                        break;
                    case BOOLEAN:
                        tableContent.append("|").append(cell.getStringCellValue());
                        cell.setCellValue(true);
                        break;
                    case NUMERIC:
                        tableContent.append("|").append(cell.getNumericCellValue());
                        cell.setCellValue(1.0);
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
        System.out.println(tableContent.toString());
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
