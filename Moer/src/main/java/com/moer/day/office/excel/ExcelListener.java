package com.moer.day.office.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel解析监听器
 *
 * @author zhangjd
 * @since 2020-07-31
 */
public abstract class ExcelListener implements ExcelReader.Listener {
    private final ArrayList<SheetListener<?>> listeners = new ArrayList<>();

    private Excel.ExcelSheet sheet = null;

    @Override
    public final void onSheetStart(Excel.ExcelSheet sheet) {
        this.sheet = sheet;
        while (this.sheet.getShtIndex() >= this.listeners.size()) {
            this.listeners.add(sheetListener(this.sheet));
        }
        SheetListener<?> sheetListener = this.listeners.get(this.sheet.getShtIndex());
        if (sheetListener != null) {
            sheetListener.onSheetStart(this.sheet);
        }
    }

    @Override
    public final void onRow(Excel.ExcelSheet sheet,  Excel.ExcelRow row,  List<Excel.ExcelCell> cells) {
        SheetListener<?> sheetListener = this.listeners.get(this.sheet.getShtIndex());
        if (sheetListener != null) {
            sheetListener.onRow(sheet, row, cells);
        }
    }

    @Override
    public final void onSheetEnd(Excel.ExcelSheet sheet) {
        SheetListener<?> sheetListener = this.listeners.get(this.sheet.getShtIndex());
        if (sheetListener != null) {
            sheetListener.onSheetEnd(this.sheet);
        }
    }

    /**
     * 获取对应sheet的监听器
     *
     * @param sheet sheet信息
     * @return 对应sheet的监听器
     */
    public abstract SheetListener<?> sheetListener(Excel.ExcelSheet sheet);
}
