package com.lz.module.biz.controller.admin.project;

import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.module.biz.enums.*;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;

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

import com.lz.module.biz.controller.admin.project.vo.*;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.service.project.ProjectService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 项目信息")
@RestController
@RequestMapping("/biz/project")
@Validated
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping("/create")
    @Operation(summary = "创建项目信息")
    @PreAuthorize("@ss.hasPermission('biz:project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody ProjectSaveReqVO createReqVO) {
        return success(projectService.createProject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目信息")
    @PreAuthorize("@ss.hasPermission('biz:project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody ProjectSaveReqVO updateReqVO) {
        projectService.updateProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除项目信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除项目信息")
                @PreAuthorize("@ss.hasPermission('biz:project:delete')")
    public CommonResult<Boolean> deleteProjectList(@RequestParam("ids") List<Long> ids) {
        projectService.deleteProjectListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得项目信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:project:query')")
    public CommonResult<ProjectRespVO> getProject(@RequestParam("id") Long id) {
        ProjectDO project = projectService.getProject(id);
        return success(BeanUtils.toBean(project, ProjectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得项目信息分页")
    @PreAuthorize("@ss.hasPermission('biz:project:query')")
    public CommonResult<PageResult<ProjectRespVO>> getProjectPage(@Valid ProjectPageReqVO pageReqVO) {
        PageResult<ProjectDO> pageResult = projectService.getProjectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProjectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:project:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProjectExcel(@Valid ProjectPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProjectDO> list = projectService.getProjectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "项目信息.xls", "数据", ProjectRespVO.class,
                        BeanUtils.toBean(list, ProjectRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入项目信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ProjectImportExcelVO> list = Collections.singletonList(
                ProjectImportExcelVO.builder()
                        .projectNo("20260110")
                        .name("导入")
                        .projectType(BizProjectTypeEnum.BIZ_PROJECT_TYPE_TYPE_2.getStatus())
                        .contractNumber("12234567")
                        .engineeringType(BizProjectEngineeringTypeEnum.BIZ_PROJECT_ENGINEERING_TYPE_1.getStatus())
                        .isPmc(BizProjectIsPmcEnum.BIZ_PROJECT_IS_PMC_1.getStatus())
                        .customerId(1L)
                        .customerName("服务商名称")
                        .deliverTime(LocalDateTime.now())
                        .completedTime(LocalDateTime.now())
                        .fiscalYear(2023)
                        .projectProgress(BizProjectProgressEnum.BIZ_PROJECT_PROGRESS_2.getStatus())
                        .isCompleted(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .isSettlementFile(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .isCompletedFile(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .isVerification(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .isDeterminedQuantity(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .isMaterialVerification(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "项目信息导入模板.xls", "项目模板", ProjectImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入项目信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:project:create')")
    public CommonResult<ProjectImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<ProjectImportExcelVO> list = ExcelUtils.read(file, ProjectImportExcelVO.class);
        return success(projectService.importProjectList(list));
    }
}
