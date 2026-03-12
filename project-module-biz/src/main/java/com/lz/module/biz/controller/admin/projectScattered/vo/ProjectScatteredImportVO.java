package com.lz.module.biz.controller.admin.projectScattered.vo;

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

@Schema(description = "管理后台 - 零散工程 Response VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class ProjectScatteredImportVO {


    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2392")
    @ExcelProperty("项目ID")
    private Long projectId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("工程名称")
    private String scatteredName;

    @Schema(description = "时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("时间")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime scatteredTime;

    @Schema(description = "工程阶段", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "工程阶段", converter = DictConvert.class)
    @DictFormat("biz_project_project_progress")
    private String projectProgress;

    @Schema(description = "竣工图")
    @ExcelProperty(value = "竣工图", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String completedImage;

    @Schema(description = "现场核销")
    @ExcelProperty(value = "现场核销", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String verification;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}
