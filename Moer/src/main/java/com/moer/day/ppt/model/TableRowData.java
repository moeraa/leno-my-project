package com.moer.day.ppt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author chenxh
 * @date 2020/4/3 4:20 下午
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableRowData {

    /**
     * 每一行数据
     */
    private List<String> dataList;
    /**
     * 标志哪一行的数据是特殊的值，
     * 例如 <1,1> 标识 cell[1] 的特殊格式渲染是1号类型，1号类型对应的是红色
     * 0；绿色，1 红色， 默认 黑色
     *
     * 程序实例 ： fillTable.setSpecialFlag(true);
     *             Map<Integer,Integer> specialFlagLocation= new HashMap<>();
     *             specialFlagLocation.put(0,0);
     *             mounthNameRowData.setSpecialFlagLocation(specialFlagLocation);
     */

    private Map<Integer, Integer> specialFlagLocation;

}
