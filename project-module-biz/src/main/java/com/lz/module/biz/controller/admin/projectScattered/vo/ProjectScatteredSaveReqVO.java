package com.lz.module.biz.controller.admin.projectScattered.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 零散工程新增/修改 Request VO")
@Data
public class ProjectScatteredSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11480")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2392")
    @NotEmpty(message = "项目ID不能为空")
    private String projectId;


    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "工程名称不能为空")
    private String scatteredName;

    @Schema(description = "时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "时间不能为空")
    private LocalDateTime scatteredTime;

    @Schema(description = "工程阶段", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "工程阶段不能为空")
    private String projectProgress;

    @Schema(description = "竣工图")
    private String completedImage;

    @Schema(description = "现场核销")
    private String verification;

    @Schema(description = "附件")
    private String appendixFile;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
