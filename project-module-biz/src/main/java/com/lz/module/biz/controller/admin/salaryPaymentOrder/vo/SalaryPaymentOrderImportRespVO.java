package com.lz.module.biz.controller.admin.salaryPaymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 工资付款导入 Response VO")
@Data
@Builder
public class SalaryPaymentOrderImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
