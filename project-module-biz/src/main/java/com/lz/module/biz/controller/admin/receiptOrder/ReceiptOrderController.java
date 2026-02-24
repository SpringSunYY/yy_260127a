package com.lz.module.biz.controller.admin.receiptOrder;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.excel.core.util.ExcelUtils;
import com.lz.module.biz.controller.admin.receiptOrder.vo.*;
import com.lz.module.biz.controller.admin.salary.vo.SalaryImportExcelVO;
import com.lz.module.biz.controller.admin.salary.vo.SalaryImportRespVO;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.module.biz.enums.BizReceiptMethodEnum;
import com.lz.module.biz.enums.BizReceiptProjectTypeEnum;
import com.lz.module.biz.enums.BizReceiptTypeEnum;
import com.lz.module.biz.service.receiptOrder.ReceiptOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.lz.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.lz.framework.common.pojo.CommonResult.success;

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

    @GetMapping("/get/amount")
    @Operation(summary = "获得收款信息总额")
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:query')")
    public CommonResult<BigDecimal> getReceiptOrderAmount(@Valid ReceiptOrderPageReqVO pageReqVO) {
        BigDecimal amount = receiptOrderService.getReceiptOrderAmount(pageReqVO);
        return success(amount);
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

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入收款信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ReceiptOrderImportExcelVO> list = Collections.singletonList(
                ReceiptOrderImportExcelVO.builder()
                        .receiptNo("20260110")
                        .receiptType(BizReceiptTypeEnum.BIZ_RECEIPT_TYPE_2.getStatus())
                        .projectType(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus())
                        .projectId(1L)
                        .payerName("昊阳建筑")
                        .receiptDate(LocalDateTime.now())
                        .receiptAmount(BigDecimal.ONE)
                        .receiptMethod(BizReceiptMethodEnum.BIZ_RECEIPT_METHOD_1.getStatus())
                        .receiptPurpose("收款事由")
                        .isInvoiced(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "收款信息导入模板.xls", "收款模板", ReceiptOrderImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入收款信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:receipt-order:create')")
    public CommonResult<ReceiptOrderImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<ReceiptOrderImportExcelVO> list = ExcelUtils.read(file, ReceiptOrderImportExcelVO.class);
        return success(receiptOrderService.importReceiptOrderList(list));
    }

}
