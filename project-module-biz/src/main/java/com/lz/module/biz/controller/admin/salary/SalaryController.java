package com.lz.module.biz.controller.admin.salary;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.excel.core.util.ExcelUtils;
import com.lz.module.biz.controller.admin.salary.vo.*;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.module.biz.service.salary.SalaryService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.lz.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.lz.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 工资信息")
@RestController
@RequestMapping("/biz/salary")
@Validated
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    @PostMapping("/create")
    @Operation(summary = "创建工资信息")
    @PreAuthorize("@ss.hasPermission('biz:salary:create')")
    public CommonResult<Long> createSalary(@Valid @RequestBody SalarySaveReqVO createReqVO) {
        return success(salaryService.createSalary(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工资信息")
    @PreAuthorize("@ss.hasPermission('biz:salary:update')")
    public CommonResult<Boolean> updateSalary(@Valid @RequestBody SalarySaveReqVO updateReqVO) {
        salaryService.updateSalary(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工资信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:salary:delete')")
    public CommonResult<Boolean> deleteSalary(@RequestParam("id") Long id) {
        salaryService.deleteSalary(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除工资信息")
    @PreAuthorize("@ss.hasPermission('biz:salary:delete')")
    public CommonResult<Boolean> deleteSalaryList(@RequestParam("ids") List<Long> ids) {
        salaryService.deleteSalaryListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工资信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:salary:query')")
    public CommonResult<SalaryRespVO> getSalary(@RequestParam("id") Long id) {
        SalaryDO salary = salaryService.getSalary(id);
        return success(BeanUtils.toBean(salary, SalaryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工资信息分页")
    @PreAuthorize("@ss.hasPermission('biz:salary:query')")
    public CommonResult<PageResult<SalaryRespVO>> getSalaryPage(@Valid SalaryPageReqVO pageReqVO) {
        PageResult<SalaryDO> pageResult = salaryService.getSalaryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SalaryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工资信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:salary:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSalaryExcel(@Valid SalaryPageReqVO pageReqVO,
                                  HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SalaryDO> list = salaryService.getSalaryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工资信息.xls", "数据", SalaryRespVO.class,
                BeanUtils.toBean(list, SalaryRespVO.class));
    }

    @PostMapping("/import")
    @Operation(summary = "导入工资")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<SalaryImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<SalaryImportExcelVO> list = ExcelUtils.read(file, SalaryImportExcelVO.class);
        return success(salaryService.importSalaryList(list));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入工资模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<SalaryImportExcelVO> list = Collections.singletonList(
                SalaryImportExcelVO.builder().workerId(1L).workerName("YY")
                        .isSettlement(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .settlementTime(LocalDateTime.now()).attendanceDays(1)
                        .overtimeDays(1)
                        .laborFeeAmount(BigDecimal.ONE).overtimeFee(BigDecimal.ONE).allowanceAmount(BigDecimal.ONE)
                        .subtotalAmount(BigDecimal.ONE).socialInsurance(BigDecimal.ONE).payableAmount(BigDecimal.ONE)
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "工资导入模板.xls", "工资模板", SalaryImportExcelVO.class, list);
    }

}
