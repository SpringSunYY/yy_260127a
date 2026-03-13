package com.lz.module.biz.controller.admin.salary.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import com.lz.module.biz.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class SalaryImportExcelVO {

    @Schema(description = "名称", example = "王五")
    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("工人编号")
    private Long workerId;

    @ExcelProperty("工人姓名")
    private String workerName;

    @ExcelProperty(value = "是否结算", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_WHETHER)
    private String isSettlement;

    @ContentStyle(dataFormat = 22)
    @Schema(description = "工资周期")
    @ExcelProperty("工资周期")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime salaryCycleTime;

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

    @Schema(description = "扣款")
    @ExcelProperty("扣款")
    private BigDecimal deduction;

    @ExcelProperty("应发款项")
    private BigDecimal payableAmount;

    @ExcelProperty("备注")
    private String remark;


}
