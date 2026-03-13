package com.lz.module.biz.controller.admin.worker.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 工人信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class WorkerImportVO {

    @Schema(description = "工人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("工人姓名")
    private String workerName;

    @Schema(description = "性别")
    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("system_user_sex")
    private String gender;

    @Schema(description = "身份证号")
    @ExcelProperty("身份证号")
    private String idCardNo;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "工人类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "工人类型", converter = DictConvert.class)
    @DictFormat("biz_worker_worker_type")
    private String workerType;

    @Schema(description = "工种", example = "2")
    @ExcelProperty(value = "工种", converter = DictConvert.class)
    @DictFormat("biz_worker_work_type")
    private String workType;

    @Schema(description = "日薪")
    @ExcelProperty("日薪")
    private BigDecimal dailySalary;

    @Schema(description = "月薪")
    @ExcelProperty("月薪")
    private BigDecimal monthlySalary;

    @Schema(description = "薪资说明")
    @ExcelProperty("薪资说明")
    private String salaryDesc;

    @Schema(description = "技能等级")
    @ExcelProperty(value = "技能等级", converter = DictConvert.class)
    @DictFormat("biz_worker_skill_level")
    private String skillLevel;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("biz_worker_status")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "应付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应付金额")
    private BigDecimal payableAmount;

    @Schema(description = "付款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款金额")
    private BigDecimal paymentAmount;

    @Schema(description = "欠款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("欠款金额")
    private BigDecimal debtAmount;

}
