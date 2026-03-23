package com.lz.module.biz.controller.admin.project.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 项目信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class ProjectImportExcelVO {

    @Schema(description = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("项目编号")
    private String projectNo;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("项目名称")
    private String name;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_project_type")
    private String projectType;

    @Schema(description = "合同编号")
    @ExcelProperty("合同编号")
    private String contractNumber;

    @Schema(description = "工程类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "工程类型", converter = DictConvert.class)
    @DictFormat("biz_project_engineering_type")
    private String engineeringType;

    @Schema(description = "属于PMC", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "属于PMC", converter = DictConvert.class)
    @DictFormat("BIZ_PROJECT_IS_PMC")
    private String isPmc;

    @Schema(description = "服务商编号", example = "17881")
    @ExcelProperty("服务商编号")
    private Long customerId;

    @Schema(description = "服务商名称", example = "王五")
    @ExcelProperty("服务商名称")
    private String customerName;

    @Schema(description = "交底时间")
    @ExcelProperty("交底时间")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime deliverTime;

    @Schema(description = "完工移交时间")
    @ExcelProperty("完工移交时间")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime completedTime;

    @Schema(description = "财年")
    @ExcelProperty("财年")
    private Integer fiscalYear;

    @Schema(description = "工程阶段", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "工程阶段", converter = DictConvert.class)
    @DictFormat("biz_project_project_progress")
    private String projectProgress;

    @Schema(description = "竣工资料")
    @ExcelProperty(value = "竣工资料", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isCompletedFile;

    @Schema(description = "现场核销")
    @ExcelProperty(value = "现场核销", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isVerification;

    @Schema(description = "竣工图")
    @ExcelProperty(value = "竣工图", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isCompleted;

    @Schema(description = "工程量确认单")
    @ExcelProperty(value = "工程量确认单", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isDeterminedQuantity;

    @Schema(description = "材料核销")
    @ExcelProperty(value = "材料核销", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isMaterialVerification;

    @Schema(description = "结算审定书")
    @ExcelProperty(value = "结算审定书", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isSettlementFile;
    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}
