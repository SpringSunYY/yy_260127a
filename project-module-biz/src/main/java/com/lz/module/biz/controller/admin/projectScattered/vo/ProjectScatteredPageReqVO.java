package com.lz.module.biz.controller.admin.projectScattered.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 零散工程分页 Request VO")
@Data
public class ProjectScatteredPageReqVO extends PageParam {

    @Schema(description = "项目ID", example = "2392")
    private String projectId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "赵六")
    private String projectName;

    @Schema(description = "工程名称", example = "李四")
    private String scatteredName;

    @Schema(description = "时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime[] scatteredTime;

    @Schema(description = "工程阶段")
    private String projectProgress;

    @Schema(description = "竣工图")
    private String completedImage;

    @Schema(description = "现场核销")
    private String verification;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
