package com.lz.module.biz.controller.admin.project.vo;

import com.lz.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
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

    @Schema(description = "完工移交时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] completedTime;

    @Schema(description = "财年")
    private Integer fiscalYear;

    @Schema(description = "工程阶段")
    private String projectProgress;

    @Schema(description = "竣工资料")
    private String isCompletedFile;

    @Schema(description = "现场核销")
    private String isVerification;

    @Schema(description = "竣工图")
    private String isCompleted;

    @Schema(description = "工程量确认单")
    private String isDeterminedQuantity;

    @Schema(description = "材料核销")
    private String isMaterialVerification;

    @Schema(description = "结算审定书")
    private String isSettlementFile;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注")
    private String remark;

}
