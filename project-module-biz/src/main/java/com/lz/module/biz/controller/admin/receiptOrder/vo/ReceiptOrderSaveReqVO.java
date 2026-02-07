package com.lz.module.biz.controller.admin.receiptOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 收款信息新增/修改 Request VO")
@Data
public class ReceiptOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21549")
    private Long id;

    @Schema(description = "收款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收款单号不能为空")
    private String receiptNo;

    @Schema(description = "收款类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "收款类型不能为空")
    private String receiptType;

    @Schema(description = "项目类型", example = "2")
    private String projectType;

    @Schema(description = "工程类型", example = "1")
    private String projectScatteredType;

    @Schema(description = "财年")
    private Integer fiscalYear;

    @Schema(description = "项目ID", example = "5697")
    private Long projectId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "李四")
    private String projectName;

    @Schema(description = "付款方", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "付款方不能为空")
    private String payerName;

    @Schema(description = "收款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收款日期不能为空")
    private LocalDateTime receiptDate;

    @Schema(description = "收款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收款金额不能为空")
    private BigDecimal receiptAmount;

    @Schema(description = "收款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "收款方式不能为空")
    private String receiptMethod;

    @Schema(description = "收款凭证")
    private String receiptCertificate;

    @Schema(description = "收款事由")
    private String receiptPurpose;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否开票不能为空")
    private String isInvoiced;

    @Schema(description = "备注", example = "随便")
    private String remark;

}