package com.lz.module.biz.controller.admin.receiptOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 工资导入 Response VO")
@Data
@Builder
public class ReceiptOrderImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
