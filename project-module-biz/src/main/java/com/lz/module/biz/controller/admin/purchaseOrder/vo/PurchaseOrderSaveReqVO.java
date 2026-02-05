package com.lz.module.biz.controller.admin.purchaseOrder.vo;

import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 采购信息新增/修改 Request VO")
@Data
public class PurchaseOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4667")
    private Long id;

    @Schema(description = "采购单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "采购单号不能为空")
    private String orderNo;

    @Schema(description = "采购名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "采购名称不能为空")
    private String name;

    @Schema(description = "供应商", example = "31508")
    private Long supplierId;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "供应商名称不能为空")
    private String supplierName;

    @Schema(description = "采购人", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "采购人不能为空")
    private String purchaserName;

    @Schema(description = "采购金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal totalAmount;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private BigDecimal totalQuantity;

    @Schema(description = "期望到货日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime expectedTime;

    @Schema(description = "实际到货日期")
    private LocalDateTime actualTime;

    @Schema(description = "采购状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "采购状态不能为空")
    private String orderStatus;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "采购明细列表")
    private List<PurchaseOrderDetailDO> purchaseOrderDetails;

}
