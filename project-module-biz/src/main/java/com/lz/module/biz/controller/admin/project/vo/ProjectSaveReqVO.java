package com.lz.module.biz.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目信息新增/修改 Request VO")
@Data
public class ProjectSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "26633")
    private Long id;

    @Schema(description = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "项目编号不能为空")
    private String projectNo;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "项目名称不能为空")
    private String name;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "项目类型不能为空")
    private String projectType;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "工程类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "工程类型不能为空")
    private String engineeringType;

    @Schema(description = "属于PMC", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "属于PMC不能为空")
    private String isPmc;

    @Schema(description = "服务商编号", example = "17881")
    private Long customerId;

    @Schema(description = "服务商名称", example = "王五")
    private String customerName;

    @Schema(description = "交底时间")
    private LocalDateTime deliverTime;

    @Schema(description = "财年")
    private Integer fiscalYear;

    @Schema(description = "签证数")
    private Integer visaNum;

    @Schema(description = "工程阶段", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "工程阶段不能为空")
    private String projectProgress;

    @Schema(description = "竣工图")
    private String isCompleted;

    @Schema(description = "竣工资料")
    private String completedFile;

    @Schema(description = "现场核销")
    private String verification;

    @Schema(description = "竣工工程确定量")
    private String determinedQuantity;

    @Schema(description = "材料核销")
    private String materialVerification;

    @Schema(description = "结算审定书")
    private String settlementFile;

    @Schema(description = "附件")
    private String appendixFile;

    @Schema(description = "备注", example = "随便")
    private String remark;

}