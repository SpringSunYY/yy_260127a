package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 采购信息分页 Request VO")
@Data
public class PurchaseOrderPageReqVO extends PageParam {

    @Schema(description = "采购单号")
    private String orderNo;

    @Schema(description = "采购名称", example = "王五")
    private String name;

    @Schema(description = "供应商", example = "31508")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "王五")
    private String supplierName;

    @Schema(description = "采购人", example = "王五")
    private String purchaserName;

    @Schema(description = "期望到货日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expectedTime;

    @Schema(description = "实际到货日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] actualTime;

    @Schema(description = "采购状态", example = "1")
    private String orderStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}