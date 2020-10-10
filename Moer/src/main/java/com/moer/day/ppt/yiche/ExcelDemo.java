package com.moer.day.ppt.yiche;

import com.moer.day.ppt.yiche.input.CellInfo;
import com.moer.day.ppt.yiche.input.ExcelInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ExcelDemo为导出的简单例子，供参考
 *
 * @author Suds
 * @since 2019-08-16
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/")
public class ExcelDemo {

    /**
     * 导出excel
     *
     * @param response HttpServletResponse响应
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        //构造Excel导出数据
        List<Area> list = new ArrayList<Area>() {{
            add(new Area("1", "北京", true));
            add(new Area("2", "天津", false));
        }};

        //构造单个sheet信息
        ExcelInfo<Area> excelInfo = new ExcelInfo<>(list,
                //序号，默认从1开始自增
                new CellInfo<Area>("序号").setCellStyle((workbook, style) -> {
                    //以给单元格着色
                    style.setFillForegroundColor(IndexedColors.YELLOW.index);
                    //设置单元格填充样式 SOLID_FOREGROUND纯色使用前景颜色填充
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    //设置字体
                    Font font = workbook.createFont();
                    font.setFontHeightInPoints((short) 13);
                    style.setFont(font);
                }),
                new CellInfo<>("地区ID", Area::getId),
                new CellInfo<>("地区名", Area::getName),
                new CellInfo<>("是否是首都", area -> area.isCapital() ? "是" : "否")
        );
        //导出excel
        ExcelUtil.exportExcel(response, "地区表", excelInfo);
    }

    /**
     * 解析excel
     *
     * @param file excel文件
     * @return list实体
     * @throws IOException IO异常
     */
    @PostMapping("/parseExcel")
    public List<Area> parseExcel(@RequestParam("file") MultipartFile file) throws IOException {
        /* 解析excel格式如下：
        |序号|地区ID|地区名|是否是首都|
        |0  |1    |北京  |是       |
        |1  |2    |天津  |否       |
        */
        List<Area> list = ExcelUtil.parseExcel(file.getInputStream(),
                null,
                Area::setId,
                Area::setName,
                Area::setCapital
        );

        return list;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Area {
        private String id;
        private String name;
        private boolean isCapital;

        public void setId(Cell cell) {
            //通过cell.getRowIndex()可以设置行号
            this.id = cell.getStringCellValue();
        }

        public void setName(Cell cell) {
            this.name = cell.getStringCellValue();
        }

        public void setCapital(Cell cell) {
            isCapital = Objects.equals(cell.getStringCellValue(), "是");
        }
    }
}
