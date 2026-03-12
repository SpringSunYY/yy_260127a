package com.lz.module.biz.controller.admin.projectVisa.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 项目签证 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class ProjectVisaImportVO {


    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24280")
    @ExcelProperty("项目ID")
    private Long projectId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("工程名称")
    private String engineeringName;

    @Schema(description = "签证名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("签证名称")
    private String visaName;

    @Schema(description = "时间")
    @ExcelProperty("时间")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime visaTime;

    @Schema(description = "签证内容")
    @ExcelProperty("签证内容")
    private String visaContent;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("金额")
    private BigDecimal amount;


    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

}
