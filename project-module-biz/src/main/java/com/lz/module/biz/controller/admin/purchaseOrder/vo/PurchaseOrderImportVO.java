package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 采购信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class PurchaseOrderImportVO {

    @Schema(description = "采购名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("采购名称")
    private String name;

    @Schema(description = "供应商", example = "31508")
    @ExcelProperty("供应商")
    private Long supplierId;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("供应商名称")
    private String supplierName;

    @Schema(description = "采购人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11146")
    @ExcelProperty("采购人ID")
    private Long purchaseUserId;

    @Schema(description = "采购人", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("采购人")
    private String purchaserUserName;

    @Schema(description = "采购金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采购金额")
    private BigDecimal totalAmount;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采购数量")
    private BigDecimal totalQuantity;

    @Schema(description = "期望到货日期")
    @ExcelProperty("期望到货日期")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime expectedTime;

    @Schema(description = "实际到货日期")
    @ExcelProperty("实际到货日期")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime actualTime;

    @Schema(description = "采购状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "采购状态", converter = DictConvert.class)
    @DictFormat("biz_purchase_order_status")
    private String orderStatus;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

}
