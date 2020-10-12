package com.moer.day.office.yiche.input;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


@Getter
public class CellInfo<T> {

    /**
     * 第一行的标题
     */
    private String name;

    /**
     * 该列的VALUE值，用于做自定义转化
     */
    private Function<T, Object> value;

    /**
     * 该列的样式，用于自定义设置边框、居中、对齐等。没有特殊需求自适配即可。
     */
    @Deprecated
    private Consumer<CellStyle> style;

    /**
     * 该列的样式，用于自定义设置边框、居中、对齐、字体等。没有特殊需求自适配即可。
     */
    private BiConsumer<Workbook, CellStyle> cellStyle;

    /**
     * 设置该列的宽度,不设置为0的情况下默认自适应
     */
    private int width;

    /**
     * 内部SDK维护变量
     */
    private CellStyle innerCellStyle;

    public CellInfo(String name) {
        this.name = name;
    }

    public CellInfo(String name, Function<T, Object> value) {
        this.name = name;
        this.value = value;
    }

    public CellInfo<T> setStyle(Consumer<CellStyle> style) {
        this.style = style;
        return this;
    }

    public CellInfo<T> setWidth(int width) {
        this.width = width;
        return this;
    }

    public CellInfo<T> setCellStyle(BiConsumer<Workbook, CellStyle> cellStyle) {
        this.cellStyle = cellStyle;
        return this;
    }

    public void setInnerCellStyle(CellStyle innerCellStyle) {
        this.innerCellStyle = innerCellStyle;
    }
}
