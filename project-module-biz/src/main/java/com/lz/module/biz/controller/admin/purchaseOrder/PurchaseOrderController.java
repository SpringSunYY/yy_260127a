package com.lz.module.biz.controller.admin.purchaseOrder;

import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.util.object.BeanUtils;
import static com.lz.framework.common.pojo.CommonResult.success;

import com.lz.framework.excel.core.util.ExcelUtils;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import static com.lz.framework.apilog.core.enums.OperateTypeEnum.*;

import com.lz.module.biz.controller.admin.purchaseOrder.vo.*;
import com.lz.module.biz.dal.dataobject.purchaseOrder.PurchaseOrderDO;
import com.lz.module.biz.service.purchaseOrder.PurchaseOrderService;

@Tag(name = "管理后台 - 采购信息")
@RestController
@RequestMapping("/biz/purchase-order")
@Validated
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建采购信息")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:create')")
    public CommonResult<Long> createPurchaseOrder(@Valid @RequestBody PurchaseOrderSaveReqVO createReqVO) {
        return success(purchaseOrderService.createPurchaseOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新采购信息")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:update')")
    public CommonResult<Boolean> updatePurchaseOrder(@Valid @RequestBody PurchaseOrderSaveReqVO updateReqVO) {
        purchaseOrderService.updatePurchaseOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除采购信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:delete')")
    public CommonResult<Boolean> deletePurchaseOrder(@RequestParam("id") Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除采购信息")
                @PreAuthorize("@ss.hasPermission('biz:purchase-order:delete')")
    public CommonResult<Boolean> deletePurchaseOrderList(@RequestParam("ids") List<Long> ids) {
        purchaseOrderService.deletePurchaseOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得采购信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:query')")
    public CommonResult<PurchaseOrderRespVO> getPurchaseOrder(@RequestParam("id") Long id) {
        PurchaseOrderDO purchaseOrder = purchaseOrderService.getPurchaseOrder(id);
        return success(BeanUtils.toBean(purchaseOrder, PurchaseOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得采购信息分页")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:query')")
    public CommonResult<PageResult<PurchaseOrderRespVO>> getPurchaseOrderPage(@Valid PurchaseOrderPageReqVO pageReqVO) {
        PageResult<PurchaseOrderDO> pageResult = purchaseOrderService.getPurchaseOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PurchaseOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出采购信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPurchaseOrderExcel(@Valid PurchaseOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PurchaseOrderDO> list = purchaseOrderService.getPurchaseOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "采购信息.xls", "数据", PurchaseOrderRespVO.class,
                        BeanUtils.toBean(list, PurchaseOrderRespVO.class));
    }

    // ==================== 子表（采购明细） ====================

    @GetMapping("/purchase-order-detail/list-by-purchase-id")
    @Operation(summary = "获得采购明细列表")
    @Parameter(name = "purchaseId", description = "采购单编号")
    @PreAuthorize("@ss.hasPermission('biz:purchase-order:query')")
    public CommonResult<List<PurchaseOrderDetailDO>> getPurchaseOrderDetailListByPurchaseId(@RequestParam("purchaseId") Long purchaseId) {
        return success(purchaseOrderService.getPurchaseOrderDetailListByPurchaseId(purchaseId));
    }

}
