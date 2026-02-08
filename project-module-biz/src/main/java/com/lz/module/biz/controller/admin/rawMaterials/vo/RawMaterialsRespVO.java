
package com.lz.module.biz.controller.admin.rawMaterials.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 原材料信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RawMaterialsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30062")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "材料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("材料名称")
    private String materialName;

    @Schema(description = "规格型号")
    @ExcelProperty("规格型号")
    private String materialSpec;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "计量单位", converter = DictConvert.class)
    @DictFormat("biz_raw_materials_unit")  
    private String unit;

    @Schema(description = "采购单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "22053")
    @ExcelProperty("采购单价")
    private BigDecimal unitPrice;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
