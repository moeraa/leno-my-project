package com.moer.day.office.yiche;

import com.moer.day.office.yiche.input.CellInfo;
import com.moer.day.office.yiche.input.ExcelInfo;
import com.moer.day.office.yiche.typetools.TypeResolver;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Excel生成工具
 *
 * @author Suds
 * @since 2019-08-16
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ExcelUtil {
    private static final String XLS_POSTFIX = ".xls";
    private static final String XLSX_POSTFIX = ".xlsx";

    private static final String EXCEL_XLS_CONTENT_TYPE = "application/vnd.ms-excel;charset=utf-8";
    private static final String EXCEL_XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";
    private static final String EXCEL_CONTENT_DISPOSITION_NAME = "Content-Disposition";
    private static final String EXCEL_CONTENT_DISPOSITION_VALUE = "attachment;filename=";

    /**
     * 使用SXSSFWorkBook的阀值
     */
    private static final int SIZE_SXSSF_WORKBOOK_THRESHOLD = 1000;

    /**
     * 使用SXSSFWorkBook的内存条数
     */
    private static final int SIZE_SXSSF_WORKBOOK_MEMORY = 500;

    /**
     * 导出xlsx格式的excel并返回前端响应
     *
     * @param response controller响应
     * @param fileName 前端显示的文件名
     * @param infos    导出数据及格式等信息，每个ExcelInfo对应excel中的一个sheet表格
     */
    @SafeVarargs
    public static <T> void exportExcel(HttpServletResponse response, String fileName, ExcelInfo<T>... infos) {
        exportExcel(response, fileName, true, infos);
    }

    /**
     * 导出excel并返回前端响应
     *
     * @param response controller响应
     * @param fileName 前端显示的文件名
     * @param xlsx     指定excel导出格式,支持xlsx/xls
     * @param infos    导出数据及格式等信息，每个ExcelInfo对应excel中的一个sheet表格
     */
    @SafeVarargs
    public static <T> void exportExcel(HttpServletResponse response, String fileName, boolean xlsx, ExcelInfo<T>... infos)  {
        Workbook workbook = null;
        try {
            workbook = createWorkBook(xlsx, infos);
            exportExcel(response, workbook, fileName);
        } catch (Exception e) {
            try {
                if (Objects.nonNull(workbook)) {
                    workbook.close();
                    if (workbook instanceof SXSSFWorkbook) {
                        ((SXSSFWorkbook) workbook).dispose();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 导出excel并返回前端响应
     *
     * @param response controller响应
     * @param fileName 前端显示的文件名
     */
    public static <T> void exportExcel(HttpServletResponse response, Workbook workbook, String fileName) {
        try {
            boolean isXlsx = !(workbook instanceof HSSFWorkbook);
            // 告诉浏览器用什么软件可以打开此文件。
            response.setContentType(isXlsx ? EXCEL_XLSX_CONTENT_TYPE : EXCEL_XLS_CONTENT_TYPE);
            response.setHeader(EXCEL_CONTENT_DISPOSITION_NAME, EXCEL_CONTENT_DISPOSITION_VALUE +
                    URLEncoder.encode(fileName, "UTF-8") + (isXlsx ? XLSX_POSTFIX : XLS_POSTFIX));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(workbook)) {
                    workbook.close();
                    if (workbook instanceof SXSSFWorkbook) {
                        ((SXSSFWorkbook) workbook).dispose();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析单sheet的excel并返回可读的list(从第二行开始解析)
     *
     * @param in          excel数据inputStream流
     * @param biConsumers 从第一列开始每列对应的获取数据Function
     */
    @SafeVarargs
    public static <T> List<T> parseExcel(InputStream in, BiConsumer<T, Cell>... biConsumers) {
        if (biConsumers.length == 0) {
            return new ArrayList<>();
        }

        //解决数据>=2000行报Zip bomb detected的错误
        ZipSecureFile.setMinInflateRatio(-1.0d);

        Workbook workbook = null;
        try {
            StopWatch stopWatch1 = new StopWatch("获取 workbook");
            stopWatch1.start();
            workbook = WorkbookFactory.create(in);
            stopWatch1.stop();
            System.out.println(stopWatch1.prettyPrint());

            Sheet sheet = workbook.getSheetAt(0);
            StopWatch stopWatch = new StopWatch("parse excel");
            stopWatch.start();
            List<T> ts = parseSheet(sheet, biConsumers);
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(workbook)) {
                    workbook.close();
                    if (workbook instanceof SXSSFWorkbook) {
                        ((SXSSFWorkbook) workbook).dispose();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * 解析excel中某个sheet页
     *
     * @param sheet       sheet页
     * @param biConsumers 从第一列开始每列对应的获取数据Function
     * @return List实体
     * @throws Exception 异常
     */
    public static <T> List<T> parseSheet(Sheet sheet, BiConsumer<T, Cell>[] biConsumers) throws Exception {
        List<T> list = new ArrayList<>();
        Class<T> cls = getClassByLambda(biConsumers, 0);

        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();

        for (int i = rowStart + 1; i <= rowEnd; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            T t = cls.newInstance();
            for (int j = 0; j < biConsumers.length; j++) {
                if (Objects.isNull(biConsumers[j])) {
                    continue;
                }
                Cell cell = row.getCell(j);

                if (cell == null || cell.getCellType() == CellType._NONE || cell.toString().isEmpty()) {
                    continue;
                }

                biConsumers[j].accept(t, cell);
            }
            list.add(t);
        }
        return list;
    }

    @SafeVarargs
    private static <T> Workbook createWorkBook(boolean xlsx, ExcelInfo<T>... infos) throws IOException {
        boolean shouldSxssf = false;
        for (ExcelInfo<T> info : infos) {
            if (info.getData().size() > SIZE_SXSSF_WORKBOOK_THRESHOLD) {
                shouldSxssf = true;
                break;
            }
        }
        Workbook workbook = WorkbookFactory.create(xlsx);
        if (xlsx && shouldSxssf) {
            //这样表示SXSSFWorkbook只会保留500条数据在内存中，其它的数据都会写到磁盘里，这样的话占用的内存就会很少
            workbook = new SXSSFWorkbook((XSSFWorkbook) workbook, SIZE_SXSSF_WORKBOOK_MEMORY);
        }

        for (ExcelInfo<T> info : infos) {
            createSheet(workbook, info);
        }
        return workbook;
    }

    /**
     * 创建sheet页
     *
     * @param workbook poi
     * @param info     sheet页数据
     */
    private static <T> void createSheet(Workbook workbook, ExcelInfo<T> info) {
        Sheet sheet;
        if (StringUtils.isEmpty(info.getSheetName())) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(info.getSheetName());
        }

        CellInfo<T>[] cellInfos = info.getCellInfos();
        List<T> data = info.getData();

        Row firstRow = sheet.createRow(0);
        for (int i = 0; i < cellInfos.length; i++) {
            CellInfo<T> cellInfo = cellInfos[i];
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(cellInfo.getName());
            setCellStyle(cellInfo, workbook, cell);
        }

        if (Objects.nonNull(data)) {
            for (int i = 0; i < data.size(); i++) {
                T object = data.get(i);
                Row row = sheet.createRow(i + 1);

                for (int j = 0; j < cellInfos.length; j++) {
                    CellInfo<T> input = cellInfos[j];
                    Cell cell = row.createCell(j);
                    //设置第一列序号
                    if (input.getValue() == null) {
                        cell.setCellValue(i);
                    } else {
                        Object value = input.getValue().apply(object);
                        if (value == null || value.toString().isEmpty()) {
                            continue;
                        }
                        if (value instanceof Date) {
                            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                        } else if (value instanceof Double) {
                            cell.setCellValue((double) value);
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((boolean) value);
                        } else if (value instanceof RichTextString) {
                            cell.setCellValue((RichTextString) value);
                        } else if (value instanceof Calendar) {
                            cell.setCellValue((Calendar) value);
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }

                    setCellStyle(input, workbook, cell);
                }
            }
        }

        if (sheet instanceof SXSSFSheet) {
            ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        }
        for (int i = 0; i < cellInfos.length; i++) {
            if (cellInfos[i].getWidth() != 0) {
                sheet.setColumnWidth(i, cellInfos[i].getWidth() * 256);
            } else {
                sheet.autoSizeColumn(i);
            }
        }
    }

    /**
     * 设置单元格的自定义样式
     *
     * @param cellInfo 用户设置的自定义样式
     * @param workbook poi
     * @param cell     单元格
     */
    @SuppressWarnings("deprecation")
    private static <T> void setCellStyle(CellInfo<T> cellInfo, Workbook workbook, Cell cell) {
        //已经初始化过的style，直接设置即可
        if (Objects.nonNull(cellInfo.getInnerCellStyle())) {
            cell.setCellStyle(cellInfo.getInnerCellStyle());
            return;
        }

        CellStyle style = workbook.createCellStyle();
        if (Objects.nonNull(cellInfo.getStyle())) {
            cellInfo.getStyle().accept(style);
        }
        if (Objects.nonNull(cellInfo.getCellStyle())) {
            cellInfo.getCellStyle().accept(workbook, style);
        }
        cell.setCellStyle(style);
        cellInfo.setInnerCellStyle(style);
    }

    /**
     * 查询BiConsumer中第一个泛型Class
     *
     * @param biConsumers 方法列表
     * @param index       序列
     * @return Class类
     */
    @SuppressWarnings("unchecked")
    private static <T> Class<T> getClassByLambda(BiConsumer<T, Cell>[] biConsumers, int index) {
        if (Objects.isNull(biConsumers[index])) {
            return getClassByLambda(biConsumers, index + 1);
        }
        return (Class<T>) TypeResolver.resolveRawArguments(BiConsumer.class, biConsumers[index].getClass())[0];
    }
}
