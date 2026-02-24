package com.lz.module.biz.controller.admin.paymentOrder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 付款信息分页 Request VO")
@Data
public class PaymentOrderPageReqVO extends PageParam {

    @Schema(description = "付款单号")
    private String paymentNo;

    @Schema(description = "项目ID", example = "11356")
    private Long projectId;

    @Schema(description = "项目类型", example = "2")
    private String projectType;

    @Schema(description = "项目名称", example = "赵六")
    private String projectName;

    @Schema(description = "收款对象类型", example = "1")
    private String payeeType;

    @Schema(description = "收款对象ID", example = "2609")
    private Long payeeId;

    @Schema(description = "收款对象名称", example = "赵六")
    private String payeeName;

    @Schema(description = "付款日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] paymentTime;

    @Schema(description = "付款方式")
    private String paymentMethod;

    @Schema(description = "是否开票")
    private String isInvoiced;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}