package com.org.moer.ppt.yiche.input;

import lombok.Getter;

import java.util.List;


@SuppressWarnings("unused")
@Getter
public class ExcelInfo<T> {

    /**
     * 页签名称
     */
    private String sheetName;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 行数据获取Function及行宽度，Styles等
     */
    private CellInfo<T>[] cellInfos;

    @SafeVarargs
    public ExcelInfo(String sheetName, List<T> data, CellInfo<T>... cellInfos) {
        this.sheetName = sheetName;
        this.data = data;
        this.cellInfos = cellInfos;
    }

    @SafeVarargs
    public ExcelInfo(List<T> data, CellInfo<T>... cellInfos) {
        this.data = data;
        this.cellInfos = cellInfos;
    }
}
