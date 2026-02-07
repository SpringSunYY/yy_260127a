package com.lz.module.biz.controller.admin.projectOther;

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

import com.lz.module.biz.controller.admin.projectOther.vo.*;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.module.biz.service.projectOther.ProjectOtherService;

@Tag(name = "管理后台 - 其他工程")
@RestController
@RequestMapping("/biz/project-other")
@Validated
public class ProjectOtherController {

    @Resource
    private ProjectOtherService projectOtherService;

    @PostMapping("/create")
    @Operation(summary = "创建其他工程")
    @PreAuthorize("@ss.hasPermission('biz:project-other:create')")
    public CommonResult<Long> createProjectOther(@Valid @RequestBody ProjectOtherSaveReqVO createReqVO) {
        return success(projectOtherService.createProjectOther(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新其他工程")
    @PreAuthorize("@ss.hasPermission('biz:project-other:update')")
    public CommonResult<Boolean> updateProjectOther(@Valid @RequestBody ProjectOtherSaveReqVO updateReqVO) {
        projectOtherService.updateProjectOther(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除其他工程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:project-other:delete')")
    public CommonResult<Boolean> deleteProjectOther(@RequestParam("id") Long id) {
        projectOtherService.deleteProjectOther(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除其他工程")
                @PreAuthorize("@ss.hasPermission('biz:project-other:delete')")
    public CommonResult<Boolean> deleteProjectOtherList(@RequestParam("ids") List<Long> ids) {
        projectOtherService.deleteProjectOtherListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得其他工程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:project-other:query')")
    public CommonResult<ProjectOtherRespVO> getProjectOther(@RequestParam("id") Long id) {
        ProjectOtherDO projectOther = projectOtherService.getProjectOther(id);
        return success(BeanUtils.toBean(projectOther, ProjectOtherRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得其他工程分页")
    @PreAuthorize("@ss.hasPermission('biz:project-other:query')")
    public CommonResult<PageResult<ProjectOtherRespVO>> getProjectOtherPage(@Valid ProjectOtherPageReqVO pageReqVO) {
        PageResult<ProjectOtherDO> pageResult = projectOtherService.getProjectOtherPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProjectOtherRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出其他工程 Excel")
    @PreAuthorize("@ss.hasPermission('biz:project-other:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProjectOtherExcel(@Valid ProjectOtherPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProjectOtherDO> list = projectOtherService.getProjectOtherPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "其他工程.xls", "数据", ProjectOtherRespVO.class,
                        BeanUtils.toBean(list, ProjectOtherRespVO.class));
    }

}