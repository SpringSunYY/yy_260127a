package com.lz.module.biz.controller.admin.paymentOrder;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.excel.core.util.ExcelUtils;
import com.lz.module.biz.controller.admin.paymentOrder.vo.*;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderImportExcelVO;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.module.biz.enums.BizPaymentPayeeTypeEnum;
import com.lz.module.biz.enums.BizReceiptMethodEnum;
import com.lz.module.biz.enums.BizReceiptProjectTypeEnum;
import com.lz.module.biz.service.paymentOrder.PaymentOrderService;
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

@Tag(name = "管理后台 - 付款信息")
@RestController
@RequestMapping("/biz/payment-order")
@Validated
public class PaymentOrderController {

    @Resource
    private PaymentOrderService paymentOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建付款信息")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:create')")
    public CommonResult<Long> createPaymentOrder(@Valid @RequestBody PaymentOrderSaveReqVO createReqVO) {
        return success(paymentOrderService.createPaymentOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新付款信息")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:update')")
    public CommonResult<Boolean> updatePaymentOrder(@Valid @RequestBody PaymentOrderSaveReqVO updateReqVO) {
        paymentOrderService.updatePaymentOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除付款信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:payment-order:delete')")
    public CommonResult<Boolean> deletePaymentOrder(@RequestParam("id") Long id) {
        paymentOrderService.deletePaymentOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除付款信息")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:delete')")
    public CommonResult<Boolean> deletePaymentOrderList(@RequestParam("ids") List<Long> ids) {
        paymentOrderService.deletePaymentOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得付款信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:query')")
    public CommonResult<PaymentOrderRespVO> getPaymentOrder(@RequestParam("id") Long id) {
        PaymentOrderDO paymentOrder = paymentOrderService.getPaymentOrder(id);
        return success(BeanUtils.toBean(paymentOrder, PaymentOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得付款信息分页")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:query')")
    public CommonResult<PageResult<PaymentOrderRespVO>> getPaymentOrderPage(@Valid PaymentOrderPageReqVO pageReqVO) {
        PageResult<PaymentOrderDO> pageResult = paymentOrderService.getPaymentOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PaymentOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出付款信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPaymentOrderExcel(@Valid PaymentOrderPageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PaymentOrderDO> list = paymentOrderService.getPaymentOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "付款信息.xls", "数据", PaymentOrderRespVO.class,
                BeanUtils.toBean(list, PaymentOrderRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入收款信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<PaymentOrderImportExcelVO> list = Collections.singletonList(
                PaymentOrderImportExcelVO.builder()
                        .paymentNo("20260110")
                        .projectType(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus())
                        .projectId(1L)
                        .projectName("项目")
                        .payeeType(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_1.getStatus())
                        .payeeName("aaaa")
                        .payeeId(1L)
                        .paymentTime(LocalDateTime.now())
                        .paymentAmount(BigDecimal.ONE)
                        .paymentMethod(BizReceiptMethodEnum.BIZ_RECEIPT_METHOD_1.getStatus())
                        .paymentPurpose("收款事由")
                        .isInvoiced(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "付款信息导入模板.xls", "付款模板", PaymentOrderImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入收款信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:payment-order:create')")
    public CommonResult<PaymentOrderImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<PaymentOrderImportExcelVO> list = ExcelUtils.read(file, PaymentOrderImportExcelVO.class);
        return success(paymentOrderService.importPaymentOrderList(list));
    }

}
