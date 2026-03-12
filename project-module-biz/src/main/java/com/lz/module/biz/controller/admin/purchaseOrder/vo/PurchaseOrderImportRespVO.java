package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 采购信息 导入 VO")
@Data
@Builder
public class PurchaseOrderImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
