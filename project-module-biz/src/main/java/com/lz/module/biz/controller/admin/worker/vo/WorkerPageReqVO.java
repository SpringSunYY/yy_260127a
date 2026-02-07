package com.lz.module.biz.controller.admin.worker.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工人信息分页 Request VO")
@Data
public class WorkerPageReqVO extends PageParam {

    @Schema(description = "工人姓名", example = "芋艿")
    private String workerName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "身份证号")
    private String idCardNo;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "工人类型", example = "1")
    private String workerType;

    @Schema(description = "工种", example = "2")
    private String workType;

    @Schema(description = "技能等级")
    private String skillLevel;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}