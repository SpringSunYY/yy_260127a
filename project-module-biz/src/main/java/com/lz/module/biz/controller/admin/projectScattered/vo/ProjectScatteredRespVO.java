package com.lz.module.biz.controller.admin.projectScattered.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 零散工程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProjectScatteredRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11480")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2392")
    @ExcelProperty("项目ID")
    private String projectId;

    @Schema(description = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("项目编号")
    private String projectNo;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("工程名称")
    private String scatteredName;

    @Schema(description = "时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("时间")
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

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String appendixFile;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
