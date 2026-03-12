package com.lz.module.biz.controller.admin.rawMaterials.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 原材料信息分页 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class RawMaterialsImportVO {

    @Schema(description = "材料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("材料名称")
    private String materialName;

    @Schema(description = "规格类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "规格类别", converter = DictConvert.class)
    @DictFormat("biz_material_type")
    private String materialType;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}
