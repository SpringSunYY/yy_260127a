package com.lz.module.biz.controller.admin.projectVisa;

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

import com.lz.module.biz.controller.admin.projectVisa.vo.*;
import com.lz.module.biz.dal.dataobject.projectVisa.ProjectVisaDO;
import com.lz.module.biz.service.projectVisa.ProjectVisaService;

@Tag(name = "管理后台 - 项目签证")
@RestController
@RequestMapping("/biz/project-visa")
@Validated
public class ProjectVisaController {

    @Resource
    private ProjectVisaService projectVisaService;

    @PostMapping("/create")
    @Operation(summary = "创建项目签证")
    @PreAuthorize("@ss.hasPermission('biz:project-visa:create')")
    public CommonResult<Long> createProjectVisa(@Valid @RequestBody ProjectVisaSaveReqVO createReqVO) {
        return success(projectVisaService.createProjectVisa(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目签证")
    @PreAuthorize("@ss.hasPermission('biz:project-visa:update')")
    public CommonResult<Boolean> updateProjectVisa(@Valid @RequestBody ProjectVisaSaveReqVO updateReqVO) {
        projectVisaService.updateProjectVisa(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目签证")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:project-visa:delete')")
    public CommonResult<Boolean> deleteProjectVisa(@RequestParam("id") Long id) {
        projectVisaService.deleteProjectVisa(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除项目签证")
                @PreAuthorize("@ss.hasPermission('biz:project-visa:delete')")
    public CommonResult<Boolean> deleteProjectVisaList(@RequestParam("ids") List<Long> ids) {
        projectVisaService.deleteProjectVisaListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目签证")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:project-visa:query')")
    public CommonResult<ProjectVisaRespVO> getProjectVisa(@RequestParam("id") Long id) {
        ProjectVisaDO projectVisa = projectVisaService.getProjectVisa(id);
        return success(BeanUtils.toBean(projectVisa, ProjectVisaRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目签证分页")
    @PreAuthorize("@ss.hasPermission('biz:project-visa:query')")
    public CommonResult<PageResult<ProjectVisaRespVO>> getProjectVisaPage(@Valid ProjectVisaPageReqVO pageReqVO) {
        PageResult<ProjectVisaDO> pageResult = projectVisaService.getProjectVisaPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProjectVisaRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目签证 Excel")
    @PreAuthorize("@ss.hasPermission('biz:project-visa:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProjectVisaExcel(@Valid ProjectVisaPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProjectVisaDO> list = projectVisaService.getProjectVisaPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目签证.xls", "数据", ProjectVisaRespVO.class,
                        BeanUtils.toBean(list, ProjectVisaRespVO.class));
    }

}