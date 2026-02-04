package com.lz.module.biz.controller.admin.supplier.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 供应商信息分页 Request VO")
@Data
public class SupplierPageReqVO extends PageParam {

    @Schema(description = "供应商名称", example = "芋艿")
    private String name;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String weChat;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}