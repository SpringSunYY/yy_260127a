package com.lz.module.biz.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目信息分页 Request VO")
@Data
public class ProjectPageReqVO extends PageParam {

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "张三")
    private String name;

    @Schema(description = "项目类型", example = "1")
    private String projectType;

    @Schema(description = "合同编号")
    private String contractNumber;

    @Schema(description = "工程类型", example = "2")
    private String engineeringType;

    @Schema(description = "属于PMC")
    private String isPmc;

    @Schema(description = "服务商名称", example = "王五")
    private String customerName;

    @Schema(description = "交底时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deliverTime;

    @Schema(description = "财年")
    private Integer fiscalYear;

    @Schema(description = "工程阶段")
    private String projectProgress;

    @Schema(description = "竣工图")
    private String isCompleted;

    @Schema(description = "现场核销")
    private String verification;

    @Schema(description = "竣工工程确定量")
    private String determinedQuantity;

    @Schema(description = "材料核销")
    private String materialVerification;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}