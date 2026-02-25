package com.lz.module.biz.controller.admin.salary.vo;

import com.lz.framework.excel.core.annotations.DictFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 工资信息新增/修改 Request VO")
@Data
public class SalarySaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25589")
    private Long id;

    @Schema(description = "工人编号", example = "22970")
    private Long workerId;

    @Schema(description = "工人姓名", example = "芋艿")
    private String workerName;

    @Schema(description = "是否结算")
    private String isSettlement;

    @Schema(description = "结算日期")
    private LocalDateTime settlementTime;

    @Schema(description = "出勤天数")
    private Integer attendanceDays;

    @Schema(description = "加班天数")
    private Integer overtimeDays;

    @Schema(description = "劳务费金额")
    private BigDecimal laborFeeAmount;

    @Schema(description = "加班费")
    private BigDecimal overtimeFee;

    @Schema(description = "补贴")
    private BigDecimal allowanceAmount;

    @Schema(description = "小计")
    private BigDecimal subtotalAmount;

    @Schema(description = "社保")
    private BigDecimal socialInsurance;

    @Schema(description = "应发款项")
    private BigDecimal payableAmount;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "是否新增", example = "true")
    private Boolean isAddPayment;

    @Schema(description = "付款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentNo;

    @Schema(description = "付款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @DictFormat("biz_receipt_method")
    private String paymentMethod;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @DictFormat("common_whether")
    private String isInvoiced;

    @Schema(description = "付款凭证")
    private String paymentCertificate;
}
