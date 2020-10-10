package com.org.moer.excel;

import com.sun.istack.internal.NotNull;

import java.io.InputStream;
import java.util.List;

/**
 * Excel解析类
 *
 * @author zhangjd
 * @since 2020-07-31
 */
@FunctionalInterface
public interface ExcelReader {

    /**
     * 流式读取excel 03或07版本，自动关闭输入流
     *
     * @param inStream excel文件输入流
     * @param listener 流式读取监听器
     * @throws Exception 读取中发生的异常
     */
    void read(@NotNull InputStream inStream, @NotNull Listener listener) throws Exception;

    /**
     * 流式读取监听器
     */
    interface Listener {
        /**
         * 处理sheet开头
         *
         * @param sheet sheet信息
         */
        void onSheetStart(@NotNull Excel.ExcelSheet sheet);

        /**
         * 处理行
         * <ul>
         *     <li>保证按顺序处理行，行号小的先处理，列号小的先处理</li>
         *     <li>不保证连续，遇到空行会跳过，遇到空单元格（BlankRecord）会跳过</li>
         *     <li>行内保证至少有一个单元格</li>
         *     <li>保证读取单元格的值不为null</li>
         * </ul>
         *
         * @param sheet sheet信息
         * @param row   row信息
         * @param cells cells信息
         */
        void onRow(@NotNull Excel.ExcelSheet sheet, @NotNull Excel.ExcelRow row, @NotNull List<Excel.ExcelCell> cells);

        /**
         * 处理sheet结尾
         *
         * @param sheet sheet信息
         */
        void onSheetEnd(@NotNull Excel.ExcelSheet sheet);
    }
}
