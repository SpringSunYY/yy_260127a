package com.lz.module.biz.controller.admin.rawMaterials.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 原材料信息新增/修改 Request VO")
@Data
public class RawMaterialsSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13943")
    private Long id;

    @Schema(description = "材料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "材料名称不能为空")
    private String materialName;

    @Schema(description = "规格类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "规格类别不能为空")
    private String materialType;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
