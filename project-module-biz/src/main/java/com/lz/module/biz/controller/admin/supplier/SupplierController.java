package com.lz.module.biz.controller.admin.supplier;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.excel.core.util.ExcelUtils;
import com.lz.module.biz.controller.admin.customer.vo.CustomerImportRespVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierImportVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierPageReqVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierRespVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierSaveReqVO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.service.supplier.SupplierService;
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
import java.util.Collections;
import java.util.List;

import static com.lz.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.lz.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 供应商信息")
@RestController
@RequestMapping("/biz/supplier")
@Validated
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @PostMapping("/create")
    @Operation(summary = "创建供应商信息")
    @PreAuthorize("@ss.hasPermission('biz:supplier:create')")
    public CommonResult<Long> createSupplier(@Valid @RequestBody SupplierSaveReqVO createReqVO) {
        return success(supplierService.createSupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商信息")
    @PreAuthorize("@ss.hasPermission('biz:supplier:update')")
    public CommonResult<Boolean> updateSupplier(@Valid @RequestBody SupplierSaveReqVO updateReqVO) {
        supplierService.updateSupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除供应商信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:supplier:delete')")
    public CommonResult<Boolean> deleteSupplier(@RequestParam("id") Long id) {
        supplierService.deleteSupplier(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除供应商信息")
    @PreAuthorize("@ss.hasPermission('biz:supplier:delete')")
    public CommonResult<Boolean> deleteSupplierList(@RequestParam("ids") List<Long> ids) {
        supplierService.deleteSupplierListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得供应商信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:supplier:query')")
    public CommonResult<SupplierRespVO> getSupplier(@RequestParam("id") Long id) {
        SupplierDO supplier = supplierService.getSupplier(id);
        return success(BeanUtils.toBean(supplier, SupplierRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得供应商信息分页")
    @PreAuthorize("@ss.hasPermission('biz:supplier:query')")
    public CommonResult<PageResult<SupplierRespVO>> getSupplierPage(@Valid SupplierPageReqVO pageReqVO) {
        PageResult<SupplierDO> pageResult = supplierService.getSupplierPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SupplierRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出供应商信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:supplier:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSupplierExcel(@Valid SupplierPageReqVO pageReqVO,
                                    HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SupplierDO> list = supplierService.getSupplierPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "供应商信息.xls", "数据", SupplierRespVO.class,
                BeanUtils.toBean(list, SupplierRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得供应商信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<SupplierImportVO> list = Collections.singletonList(
                SupplierImportVO.builder()
                        .name("张三")
                        .telephone("13888888888")
                        .qq("123456789")
                        .weChat("123456789")
                        .email("test@test.com")
                        .areaId(510000L)
                        .detailAddress("详细地址")
                        .paymentAmount(BigDecimal.ZERO)
                        .debtAmount(BigDecimal.ZERO)
                        .payableAmount(BigDecimal.ZERO)
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "供应商信息导入模板.xls", "供应商信息模板", SupplierImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入供应商信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:supplier:create')")
    public CommonResult<CustomerImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<SupplierImportVO> list = ExcelUtils.read(file, SupplierImportVO.class);
        return success(supplierService.importSupplierList(list));
    }

}
