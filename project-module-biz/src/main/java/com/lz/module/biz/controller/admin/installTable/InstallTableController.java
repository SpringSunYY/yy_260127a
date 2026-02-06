package com.lz.module.biz.controller.admin.installTable;

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

import com.lz.module.biz.controller.admin.installTable.vo.*;
import com.lz.module.biz.dal.dataobject.installTable.InstallTableDO;
import com.lz.module.biz.service.installTable.InstallTableService;

@Tag(name = "管理后台 - 装表信息")
@RestController
@RequestMapping("/biz/install-table")
@Validated
public class InstallTableController {

    @Resource
    private InstallTableService installTableService;

    @PostMapping("/create")
    @Operation(summary = "创建装表信息")
    @PreAuthorize("@ss.hasPermission('biz:install-table:create')")
    public CommonResult<Long> createInstallTable(@Valid @RequestBody InstallTableSaveReqVO createReqVO) {
        return success(installTableService.createInstallTable(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新装表信息")
    @PreAuthorize("@ss.hasPermission('biz:install-table:update')")
    public CommonResult<Boolean> updateInstallTable(@Valid @RequestBody InstallTableSaveReqVO updateReqVO) {
        installTableService.updateInstallTable(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除装表信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:install-table:delete')")
    public CommonResult<Boolean> deleteInstallTable(@RequestParam("id") Long id) {
        installTableService.deleteInstallTable(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除装表信息")
                @PreAuthorize("@ss.hasPermission('biz:install-table:delete')")
    public CommonResult<Boolean> deleteInstallTableList(@RequestParam("ids") List<Long> ids) {
        installTableService.deleteInstallTableListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得装表信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:install-table:query')")
    public CommonResult<InstallTableRespVO> getInstallTable(@RequestParam("id") Long id) {
        InstallTableDO installTable = installTableService.getInstallTable(id);
        return success(BeanUtils.toBean(installTable, InstallTableRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得装表信息分页")
    @PreAuthorize("@ss.hasPermission('biz:install-table:query')")
    public CommonResult<PageResult<InstallTableRespVO>> getInstallTablePage(@Valid InstallTablePageReqVO pageReqVO) {
        PageResult<InstallTableDO> pageResult = installTableService.getInstallTablePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InstallTableRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出装表信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:install-table:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInstallTableExcel(@Valid InstallTablePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InstallTableDO> list = installTableService.getInstallTablePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "装表信息.xls", "数据", InstallTableRespVO.class,
                        BeanUtils.toBean(list, InstallTableRespVO.class));
    }

}