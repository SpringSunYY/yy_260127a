package com.lz.module.biz.controller.admin.projectOther.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 其他工程新增/修改 Request VO")
@Data
public class ProjectOtherSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11011")
    private Long id;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "项目名称不能为空")
    private String projectName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "项目类型不能为空")
    private String projectType;

    @Schema(description = "地址")
    private String projectAddress;

    @Schema(description = "时间")
    private LocalDateTime projectDate;

    @Schema(description = "施工费")
    private BigDecimal constructionFee;

    @Schema(description = "已结算", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "已结算不能为空")
    private String isSettled;

    @Schema(description = "附件")
    private String appendixFile;

    @Schema(description = "材料说明")
    private String materialDesc;

    @Schema(description = "进度", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "进度不能为空")
    private String progressStatus;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
