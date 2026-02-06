package com.lz.module.biz.controller.admin.projectVisa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 项目签证新增/修改 Request VO")
@Data
public class ProjectVisaSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2242")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24280")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "工程名称不能为空")
    private String visaName;

    @Schema(description = "签证内容")
    private String visaContent;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    @Schema(description = "附件")
    private String appendixFile;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}
