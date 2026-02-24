package com.lz.module.biz.controller.admin.paymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 付款信息新增/修改 Request VO")
@Data
public class PaymentOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2060")
    private Long id;

    @Schema(description = "付款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "付款单号不能为空")
    private String paymentNo;

    @Schema(description = "项目ID", example = "11356")
    private Long projectId;

    @Schema(description = "项目类型", example = "2")
    private String projectType;

    @Schema(description = "项目名称", example = "赵六")
    private String projectName;

    @Schema(description = "收款对象类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "收款对象类型不能为空")
    private String payeeType;

    @Schema(description = "收款对象ID", example = "2609")
    private Long payeeId;

    @Schema(description = "收款对象名称", example = "赵六")
    private String payeeName;

    @Schema(description = "付款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "付款日期不能为空")
    private LocalDateTime paymentTime;

    @Schema(description = "付款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "付款金额不能为空")
    private BigDecimal paymentAmount;

    @Schema(description = "付款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "付款方式不能为空")
    private String paymentMethod;

    @Schema(description = "付款凭证")
    private String paymentCertificate;

    @Schema(description = "付款事由")
    private String paymentPurpose;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "是否开票不能为空")
    private String isInvoiced;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}