package com.lz.module.biz.controller.admin.projectVisa.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目签证分页 Request VO")
@Data
public class ProjectVisaPageReqVO extends PageParam {

    @Schema(description = "项目ID", example = "24280")
    private Long projectId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "李四")
    private String name;

    @Schema(description = "工程名称", example = "李四")
    private String visaName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}