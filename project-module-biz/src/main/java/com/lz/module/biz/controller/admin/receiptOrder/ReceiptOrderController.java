package com.lz.module.biz.controller.admin.receiptOrder;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
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

import com.lz.module.biz.controller.admin.receiptOrder.vo.*;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.module.biz.service.receiptOrder.ReceiptOrderService;

@Tag(name = "管理后台 - 收款信息")
@RestController
@RequestMapping("/biz/receipt-order")
@Validated
public class ReceiptOrderController {

    @Resource
    private ReceiptOrderService receiptOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建收款信息")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:create')")
    public CommonResult<Long> createReceiptOrder(@Valid @RequestBody ReceiptOrderSaveReqVO createReqVO) {
        return success(receiptOrderService.createReceiptOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收款信息")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:update')")
    public CommonResult<Boolean> updateReceiptOrder(@Valid @RequestBody ReceiptOrderSaveReqVO updateReqVO) {
        receiptOrderService.updateReceiptOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收款信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:delete')")
    public CommonResult<Boolean> deleteReceiptOrder(@RequestParam("id") Long id) {
        receiptOrderService.deleteReceiptOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除收款信息")
                @PreAuthorize("@ss.hasPermission('biz:receipt-order:delete')")
    public CommonResult<Boolean> deleteReceiptOrderList(@RequestParam("ids") List<Long> ids) {
        receiptOrderService.deleteReceiptOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收款信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:query')")
    public CommonResult<ReceiptOrderRespVO> getReceiptOrder(@RequestParam("id") Long id) {
        ReceiptOrderDO receiptOrder = receiptOrderService.getReceiptOrder(id);
        return success(BeanUtils.toBean(receiptOrder, ReceiptOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收款信息分页")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:query')")
    public CommonResult<PageResult<ReceiptOrderRespVO>> getReceiptOrderPage(@Valid ReceiptOrderPageReqVO pageReqVO) {
        PageResult<ReceiptOrderDO> pageResult = receiptOrderService.getReceiptOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReceiptOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收款信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReceiptOrderExcel(@Valid ReceiptOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReceiptOrderDO> list = receiptOrderService.getReceiptOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收款信息.xls", "数据", ReceiptOrderRespVO.class,
                        BeanUtils.toBean(list, ReceiptOrderRespVO.class));
    }

}