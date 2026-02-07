package com.lz.module.biz.controller.admin.salary;

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

import com.lz.module.biz.controller.admin.salary.vo.*;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.module.biz.service.salary.SalaryService;

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

}