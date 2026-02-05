package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 采购信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PurchaseOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4667")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "采购单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采购单号")
    private String orderNo;

    @Schema(description = "采购名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("采购名称")
    private String name;

    @Schema(description = "供应商", example = "31508")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("供应商名称")
    private String supplierName;

    @Schema(description = "采购人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("采购人")
    private String purchaserName;

    @Schema(description = "采购金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采购金额")
    private BigDecimal totalAmount;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采购数量")
    private BigDecimal totalQuantity;

    @Schema(description = "期望到货日期")
    @ExcelProperty("期望到货日期")
    private LocalDateTime expectedTime;

    @Schema(description = "实际到货日期")
    @ExcelProperty("实际到货日期")
    private LocalDateTime actualTime;

    @Schema(description = "采购状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "采购状态", converter = DictConvert.class)
    @DictFormat("biz_purchase_order_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String orderStatus;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
