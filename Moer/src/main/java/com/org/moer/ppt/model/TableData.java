package com.org.moer.ppt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenxh
 * @date 2020/4/3 4:20 下午
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableData {
    /**
     * 从第几行，开始填充，表头
     */
    private int startRowNum;
    /**
     * 从第几列 开始填充，列头
     */
    private int startColumn;
    /**
     * 表格一行数据
     */
    private List<TableRowData> tableRowDataList;
    /**
     * 是否有定制化数据 默认是false
     */
    private boolean specialFlag;

}
