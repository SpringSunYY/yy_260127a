package com.lz.module.biz.controller.admin.projectOther.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 其他工程分页 Request VO")
@Data
public class ProjectOtherPageReqVO extends PageParam {

    @Schema(description = "项目名称", example = "李四")
    private String projectName;

    @Schema(description = "项目类型", example = "1")
    private String projectType;

    @Schema(description = "地址")
    private String projectAddress;

    @Schema(description = "时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime[] projectDate;

    @Schema(description = "已结算")
    private String isSettled;

    @Schema(description = "材料说明")
    private String materialDesc;

    @Schema(description = "进度", example = "1")
    private String progressStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
