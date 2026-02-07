package com.lz.module.biz.controller.admin.salary.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import com.lz.module.biz.enums.DictTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class SalaryImportExcelVO {

    @ExcelProperty("工人编号")
    private Long workerId;

    @ExcelProperty("工人姓名")
    private String workerName;

    @ExcelProperty(value = "是否结算", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_WHETHER)
    private String isSettlement;

    @ExcelProperty("结算日期")
    @ContentStyle(dataFormat = 22)
    private LocalDateTime settlementTime;

    @ExcelProperty("出勤天数")
    private Integer attendanceDays;

    @ExcelProperty("加班天数")
    private Integer overtimeDays;

    @ExcelProperty("劳务费金额")
    private BigDecimal laborFeeAmount;

    @ExcelProperty("加班费")
    private BigDecimal overtimeFee;

    @ExcelProperty("补贴")
    private BigDecimal allowanceAmount;

    @ExcelProperty("小计")
    private BigDecimal subtotalAmount;

    @ExcelProperty("社保")
    private BigDecimal socialInsurance;

    @ExcelProperty("应发款项")
    private BigDecimal payableAmount;

    @ExcelProperty("备注")
    private String remark;


}
