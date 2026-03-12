package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lz.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 采购明细 导入 Vo
 *
 * @author YY
 */
@Schema(description = "管理后台 - 采购信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class PurchaseOrderDetailImportVo {


    /**
     * 采购单编号
     */
    @Schema(description = "采购单ID", example = "31508")
    @ExcelProperty("采购单ID")
    private Long purchaseId;
    /**
     * 材料ID
     */
    @Schema(description = "材料ID", example = "31508")
    @ExcelProperty("材料ID")
    private Long materialId;
    /**
     * 材料名称
     */
    @Schema(description = "材料名称", example = "31508")
    @ExcelProperty("材料名称")
    private String materialName;

    /**
     * 采购数量
     */
    @Schema(description = "采购数量", example = "31508")
    @ExcelProperty("采购数量")
    private BigDecimal quantity;
    /**
     * 采购单价
     */
    @Schema(description = "采购单价", example = "31508")
    @ExcelProperty("采购单价")
    private BigDecimal unitPrice;
    /**
     * 小计金额
     */
    @Schema(description = "小计金额", example = "31508")
    @ExcelProperty("小计金额")
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    @Schema(description = "备注", example = "31508")
    @ExcelProperty("备注")
    private String remark;

}
