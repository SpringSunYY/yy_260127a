package com.lz.module.biz.controller.admin.salaryPaymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 工资付款信息新增/修改 Request VO")
@Data
public class SalaryPaymentOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18815")
    private Long id;


    @Schema(description = "工资ID", example = "22538")
    private Long salaryId;

    @Schema(description = "工资名称", example = "赵六")
    private String salaryName;

    @Schema(description = "工人ID", example = "31798")
    private Long workerId;

    @Schema(description = "工人名称", example = "张三")
    private String workerName;

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

    @Schema(description = "备注", example = "随便")
    private String remark;

}
