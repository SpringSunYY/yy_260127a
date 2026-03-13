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


    @Schema(description = "供应商ID", example = "666")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "芋艿")
    private String supplierName;

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
