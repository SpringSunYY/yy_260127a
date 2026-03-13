package com.lz.module.biz.controller.admin.salaryPaymentOrder;

import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportRespVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportVO;
import com.lz.module.biz.enums.BizReceiptMethodEnum;
import io.swagger.v3.oas.annotations.Parameters;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.*;
import com.lz.module.biz.dal.dataobject.salaryPaymentOrder.SalaryPaymentOrderDO;
import com.lz.module.biz.service.salaryPaymentOrder.SalaryPaymentOrderService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 工资付款信息")
@RestController
@RequestMapping("/biz/salary-payment-order")
@Validated
public class SalaryPaymentOrderController {

    @Resource
    private SalaryPaymentOrderService salaryPaymentOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建工资付款信息")
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:create')")
    public CommonResult<Long> createSalaryPaymentOrder(@Valid @RequestBody SalaryPaymentOrderSaveReqVO createReqVO) {
        return success(salaryPaymentOrderService.createSalaryPaymentOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工资付款信息")
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:update')")
    public CommonResult<Boolean> updateSalaryPaymentOrder(@Valid @RequestBody SalaryPaymentOrderSaveReqVO updateReqVO) {
        salaryPaymentOrderService.updateSalaryPaymentOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工资付款信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:delete')")
    public CommonResult<Boolean> deleteSalaryPaymentOrder(@RequestParam("id") Long id) {
        salaryPaymentOrderService.deleteSalaryPaymentOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除工资付款信息")
                @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:delete')")
    public CommonResult<Boolean> deleteSalaryPaymentOrderList(@RequestParam("ids") List<Long> ids) {
        salaryPaymentOrderService.deleteSalaryPaymentOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工资付款信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:query')")
    public CommonResult<SalaryPaymentOrderRespVO> getSalaryPaymentOrder(@RequestParam("id") Long id) {
        SalaryPaymentOrderDO salaryPaymentOrder = salaryPaymentOrderService.getSalaryPaymentOrder(id);
        return success(BeanUtils.toBean(salaryPaymentOrder, SalaryPaymentOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工资付款信息分页")
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:query')")
    public CommonResult<PageResult<SalaryPaymentOrderRespVO>> getSalaryPaymentOrderPage(@Valid SalaryPaymentOrderPageReqVO pageReqVO) {
        PageResult<SalaryPaymentOrderDO> pageResult = salaryPaymentOrderService.getSalaryPaymentOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SalaryPaymentOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工资付款信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalaryPaymentOrderExcel(@Valid SalaryPaymentOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SalaryPaymentOrderDO> list = salaryPaymentOrderService.getSalaryPaymentOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工资付款信息.xls", "数据", SalaryPaymentOrderRespVO.class,
                        BeanUtils.toBean(list, SalaryPaymentOrderRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入工资付款信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<SalaryPaymentOrderImportVO> list = Collections.singletonList(
                SalaryPaymentOrderImportVO.builder()
                        .workerName("工人名称")
                        .workerId(1L)
                        .salaryId(1L)
                        .salaryName("工资名称")
                        .paymentTime(LocalDateTime.now())
                        .paymentAmount(BigDecimal.ONE)
                        .paymentMethod(BizReceiptMethodEnum.BIZ_RECEIPT_METHOD_1.getStatus())
                        .paymentPurpose("付款事由")
                        .isInvoiced(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "工资付款信息导入模板.xls", "工资付款模板", SalaryPaymentOrderImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入收款信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:salary-payment-order:create')")
    public CommonResult<SalaryPaymentOrderImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<SalaryPaymentOrderImportVO> list = ExcelUtils.read(file, SalaryPaymentOrderImportVO.class);
        return success(salaryPaymentOrderService.importSalaryPaymentOrderList(list));
    }


}
