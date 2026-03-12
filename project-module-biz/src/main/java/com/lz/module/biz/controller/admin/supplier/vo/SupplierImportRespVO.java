package com.lz.module.biz.controller.admin.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 供应商信息 导入 VO")
@Data
@Builder
public class SupplierImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
