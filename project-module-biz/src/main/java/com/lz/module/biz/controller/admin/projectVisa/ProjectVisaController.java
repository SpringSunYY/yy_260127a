package com.lz.module.biz.controller.admin.projectVisa;

import com.lz.framework.apilog.core.annotation.ApiAccessLog;
import com.lz.framework.common.pojo.CommonResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.excel.core.util.ExcelUtils;
import com.lz.module.biz.controller.admin.projectVisa.vo.*;
import com.lz.module.biz.dal.dataobject.projectVisa.ProjectVisaDO;
import com.lz.module.biz.service.projectVisa.ProjectVisaService;
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

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入项目签证信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ProjectVisaImportVO> list = Collections.singletonList(
                ProjectVisaImportVO.builder()
                        .projectId(20260110L)
                        .projectName("项目名称")
                        .engineeringName("工程名称")
                        .visaName("签证名称")
                        .visaTime(LocalDateTime.now())
                        .visaContent("签证内容")
                        .amount(BigDecimal.TEN)
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "项目签证信息导入模板.xls", "项目签证模板", ProjectVisaImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入项目信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:project-visa:create')")
    public CommonResult<ProjectVisaImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<ProjectVisaImportVO> list = ExcelUtils.read(file, ProjectVisaImportVO.class);
        return success(projectVisaService.importProjectVisaList(list));
    }
}
