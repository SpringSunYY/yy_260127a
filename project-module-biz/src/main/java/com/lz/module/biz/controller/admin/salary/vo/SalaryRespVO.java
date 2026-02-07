package com.lz.module.biz.controller.admin.salary.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 工资信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SalaryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25589")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "工人编号", example = "22970")
    @ExcelProperty("工人编号")
    private Long workerId;

    @Schema(description = "工人姓名", example = "芋艿")
    @ExcelProperty("工人姓名")
    private String workerName;

    @Schema(description = "是否结算")
    @ExcelProperty(value = "是否结算", converter = DictConvert.class)
    @DictFormat("common_whether") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isSettlement;

    @Schema(description = "结算日期")
    @ExcelProperty("结算日期")
    private LocalDateTime settlementTime;

    @Schema(description = "出勤天数")
    @ExcelProperty("出勤天数")
    private Integer attendanceDays;

    @Schema(description = "加班天数")
    @ExcelProperty("加班天数")
    private Integer overtimeDays;

    @Schema(description = "劳务费金额")
    @ExcelProperty("劳务费金额")
    private BigDecimal laborFeeAmount;

    @Schema(description = "加班费")
    @ExcelProperty("加班费")
    private BigDecimal overtimeFee;

    @Schema(description = "补贴")
    @ExcelProperty("补贴")
    private BigDecimal allowanceAmount;

    @Schema(description = "小计")
    @ExcelProperty("小计")
    private BigDecimal subtotalAmount;

    @Schema(description = "社保")
    @ExcelProperty("社保")
    private BigDecimal socialInsurance;

    @Schema(description = "应发款项")
    @ExcelProperty("应发款项")
    private BigDecimal payableAmount;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}