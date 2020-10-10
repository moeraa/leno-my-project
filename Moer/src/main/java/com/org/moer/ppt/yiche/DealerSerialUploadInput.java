package com.org.moer.ppt.yiche;

import cn.hutool.core.convert.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;


/**
 * @author zhangjd
 * @since 2020-07-29
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DealerSerialUploadInput {

    /**
     * 易车经销商ID
     */
    private Long dealerId;

    public void setId(Cell cell) {
        this.dealerId = Convert.toLong(ExcelTest.getCellValue(cell));
//        this.createTime = LocalDateTime.now();
    }

    /**
     * 经销商全称
     */
    private String fullName;

    public void setFullName(Cell cell) {
        this.fullName = ExcelTest.getCellValue(cell);
    }

    /**
     * 经销商简称
     */
    private String shortName;

    public void setShortName(Cell cell) {
        this.shortName = ExcelTest.getCellValue(cell);
    }

//    /**
//     * 销售地址
//     */
//    private String address;
//
//    public void setAddress(Cell cell) {
//        this.address = ExcelTest.getCellValue(cell);
//    }
//
//    /**
//     * 地区
//     */
//    private String region;
//
//    public void setRegion(Cell cell) {
//        this.region = ExcelTest.getCellValue(cell);
//    }
//
//    /**
//     * 省份ID
//     */
//    private Integer provinceId;
//
//    public void setProvinceId(Cell cell) {
//        this.provinceId = Convert.toInteger(ExcelTest.getCellValue(cell));
//    }
//
//    /**
//     * 省份名称
//     */
//    private String provinceName;
//
//    public void setProvinceName(Cell cell) {
//        this.provinceName = ExcelTest.getCellValue(cell);
//    }
//
//    /**
//     * 城市ID
//     */
//    private Integer cityId;
//
//    public void setCityId(Cell cell) {
//        this.cityId = Convert.toInteger(ExcelTest.getCellValue(cell));
//    }
//
//    /**
//     * 经营车系ID
//     */
//    private List<Long> serialIds;
//
//    public void setSerialId(Cell cell) {
//        String cellValue = ExcelTest.getCellValue(cell);
//        this.serialIds = Arrays.stream(cellValue.split("，")).map(Convert::toLong).collect(Collectors.toList());
//    }
//
//    /**
//     * 经营车系名称
//     */
//    private List<String> serialNames;
//
//    public void setSerialName(Cell cell) {
//        String cellValue = ExcelTest.getCellValue(cell);
//        this.serialNames = Arrays.stream(cellValue.split("，")).collect(Collectors.toList());
//    }
//
//    private LocalDateTime createTime;
//


}
