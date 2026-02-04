package com.lz.module.biz.controller.admin.rawMaterials.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 原材料信息新增/修改 Request VO")
@Data
public class RawMaterialsSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30062")
    private Long id;

    @Schema(description = "材料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "材料名称不能为空")
    private String materialName;

    @Schema(description = "规格型号")
    private String materialSpec;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "采购单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "22053")
    @NotNull(message = "采购单价不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}