package com.moer.day.excel;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

/**
 * 通用Excel信息
 *
 * @author zhangjd
 * @since 2020-07-31
 */
@Data
public class Excel {


    /**
     * 通用Cell信息
     */
    @Data
    @AllArgsConstructor
    public static class ExcelCell implements Serializable {
        /**
         * Sheet编号，从0开始
         */
        private final int shtIndex;
        /**
         * Row编号，从0开始
         */
        private final int rowIndex;
        /**
         * Cell编号，从0开始
         */
        private final int colIndex;
        /**
         * Cell的字符串值，不为null
         */
        @NotNull
        private String strValue;
    }

    /**
     * 通用Row信息
     */
    @Data
    public static class ExcelRow implements Serializable {
        /**
         * Sheet编号，从0开始
         */
        private final int shtIndex;
        /**
         * Row编号，从0开始
         */
        private final int rowIndex;
        /**
         * Row第一个Cell的编号，从0开始
         */
        private int colFirst;
        /**
         * Row最后一个Cell的编号，从0开始
         */
        private int colLast;
    }

    /**
     * 通用Sheet信息
     */
    @Data
    public static class ExcelSheet implements Serializable {
        /**
         * Sheet编号，从0开始
         */
        private final int shtIndex;
        /**
         * Sheet名称
         */
        @NotNull
        private final String shtName;
    }
}
