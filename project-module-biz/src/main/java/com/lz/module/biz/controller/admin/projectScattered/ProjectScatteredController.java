package com.lz.module.biz.controller.admin.projectScattered;

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

import com.lz.module.biz.controller.admin.projectScattered.vo.*;
import com.lz.module.biz.dal.dataobject.projectScattered.ProjectScatteredDO;
import com.lz.module.biz.service.projectScattered.ProjectScatteredService;

@Tag(name = "管理后台 - 零散工程")
@RestController
@RequestMapping("/biz/project-scattered")
@Validated
public class ProjectScatteredController {

    @Resource
    private ProjectScatteredService projectScatteredService;

    @PostMapping("/create")
    @Operation(summary = "创建零散工程")
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:create')")
    public CommonResult<Long> createProjectScattered(@Valid @RequestBody ProjectScatteredSaveReqVO createReqVO) {
        return success(projectScatteredService.createProjectScattered(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新零散工程")
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:update')")
    public CommonResult<Boolean> updateProjectScattered(@Valid @RequestBody ProjectScatteredSaveReqVO updateReqVO) {
        projectScatteredService.updateProjectScattered(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除零散工程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:delete')")
    public CommonResult<Boolean> deleteProjectScattered(@RequestParam("id") Long id) {
        projectScatteredService.deleteProjectScattered(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除零散工程")
                @PreAuthorize("@ss.hasPermission('biz:project-scattered:delete')")
    public CommonResult<Boolean> deleteProjectScatteredList(@RequestParam("ids") List<Long> ids) {
        projectScatteredService.deleteProjectScatteredListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得零散工程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:query')")
    public CommonResult<ProjectScatteredRespVO> getProjectScattered(@RequestParam("id") Long id) {
        ProjectScatteredDO projectScattered = projectScatteredService.getProjectScattered(id);
        return success(BeanUtils.toBean(projectScattered, ProjectScatteredRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得零散工程分页")
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:query')")
    public CommonResult<PageResult<ProjectScatteredRespVO>> getProjectScatteredPage(@Valid ProjectScatteredPageReqVO pageReqVO) {
        PageResult<ProjectScatteredDO> pageResult = projectScatteredService.getProjectScatteredPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProjectScatteredRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出零散工程 Excel")
    @PreAuthorize("@ss.hasPermission('biz:project-scattered:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProjectScatteredExcel(@Valid ProjectScatteredPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProjectScatteredDO> list = projectScatteredService.getProjectScatteredPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "零散工程.xls", "数据", ProjectScatteredRespVO.class,
                        BeanUtils.toBean(list, ProjectScatteredRespVO.class));
    }

}