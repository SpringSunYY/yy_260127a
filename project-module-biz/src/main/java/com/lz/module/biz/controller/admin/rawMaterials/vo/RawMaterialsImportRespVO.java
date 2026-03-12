package com.lz.module.biz.controller.admin.rawMaterials.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 原材料信息 导入 VO")
@Data
@Builder
public class RawMaterialsImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
