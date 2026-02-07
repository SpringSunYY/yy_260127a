package com.lz.module.biz.controller.admin.worker;

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

import com.lz.module.biz.controller.admin.worker.vo.*;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.service.worker.WorkerService;

@Tag(name = "管理后台 - 工人信息")
@RestController
@RequestMapping("/biz/worker")
@Validated
public class WorkerController {

    @Resource
    private WorkerService workerService;

    @PostMapping("/create")
    @Operation(summary = "创建工人信息")
    @PreAuthorize("@ss.hasPermission('biz:worker:create')")
    public CommonResult<Long> createWorker(@Valid @RequestBody WorkerSaveReqVO createReqVO) {
        return success(workerService.createWorker(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工人信息")
    @PreAuthorize("@ss.hasPermission('biz:worker:update')")
    public CommonResult<Boolean> updateWorker(@Valid @RequestBody WorkerSaveReqVO updateReqVO) {
        workerService.updateWorker(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工人信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:worker:delete')")
    public CommonResult<Boolean> deleteWorker(@RequestParam("id") Long id) {
        workerService.deleteWorker(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除工人信息")
                @PreAuthorize("@ss.hasPermission('biz:worker:delete')")
    public CommonResult<Boolean> deleteWorkerList(@RequestParam("ids") List<Long> ids) {
        workerService.deleteWorkerListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工人信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:worker:query')")
    public CommonResult<WorkerRespVO> getWorker(@RequestParam("id") Long id) {
        WorkerDO worker = workerService.getWorker(id);
        return success(BeanUtils.toBean(worker, WorkerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工人信息分页")
    @PreAuthorize("@ss.hasPermission('biz:worker:query')")
    public CommonResult<PageResult<WorkerRespVO>> getWorkerPage(@Valid WorkerPageReqVO pageReqVO) {
        PageResult<WorkerDO> pageResult = workerService.getWorkerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WorkerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工人信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:worker:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWorkerExcel(@Valid WorkerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WorkerDO> list = workerService.getWorkerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工人信息.xls", "数据", WorkerRespVO.class,
                        BeanUtils.toBean(list, WorkerRespVO.class));
    }

}