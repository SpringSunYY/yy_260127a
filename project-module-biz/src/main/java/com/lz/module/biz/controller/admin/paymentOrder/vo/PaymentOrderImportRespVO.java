package com.lz.module.biz.controller.admin.paymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 付款信息 Response VO")
@Data
@Builder
public class PaymentOrderImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
