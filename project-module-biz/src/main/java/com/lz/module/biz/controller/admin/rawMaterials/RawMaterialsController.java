package com.lz.module.biz.controller.admin.rawMaterials;

import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.module.biz.controller.admin.project.vo.ProjectImportExcelVO;
import com.lz.module.biz.controller.admin.project.vo.ProjectImportRespVO;
import com.lz.module.biz.enums.*;
import io.swagger.v3.oas.annotations.Parameters;
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

import com.lz.module.biz.controller.admin.rawMaterials.vo.*;
import com.lz.module.biz.dal.dataobject.rawMaterials.RawMaterialsDO;
import com.lz.module.biz.service.rawMaterials.RawMaterialsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 原材料信息")
@RestController
@RequestMapping("/biz/raw-materials")
@Validated
public class RawMaterialsController {

    @Resource
    private RawMaterialsService rawMaterialsService;

    @PostMapping("/create")
    @Operation(summary = "创建原材料信息")
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:create')")
    public CommonResult<Long> createRawMaterials(@Valid @RequestBody RawMaterialsSaveReqVO createReqVO) {
        return success(rawMaterialsService.createRawMaterials(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新原材料信息")
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:update')")
    public CommonResult<Boolean> updateRawMaterials(@Valid @RequestBody RawMaterialsSaveReqVO updateReqVO) {
        rawMaterialsService.updateRawMaterials(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除原材料信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:delete')")
    public CommonResult<Boolean> deleteRawMaterials(@RequestParam("id") Long id) {
        rawMaterialsService.deleteRawMaterials(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除原材料信息")
                @PreAuthorize("@ss.hasPermission('biz:raw-materials:delete')")
    public CommonResult<Boolean> deleteRawMaterialsList(@RequestParam("ids") List<Long> ids) {
        rawMaterialsService.deleteRawMaterialsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得原材料信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:query')")
    public CommonResult<RawMaterialsRespVO> getRawMaterials(@RequestParam("id") Long id) {
        RawMaterialsDO rawMaterials = rawMaterialsService.getRawMaterials(id);
        return success(BeanUtils.toBean(rawMaterials, RawMaterialsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得原材料信息分页")
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:query')")
    public CommonResult<PageResult<RawMaterialsRespVO>> getRawMaterialsPage(@Valid RawMaterialsPageReqVO pageReqVO) {
        PageResult<RawMaterialsDO> pageResult = rawMaterialsService.getRawMaterialsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RawMaterialsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出原材料信息 Excel")
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRawMaterialsExcel(@Valid RawMaterialsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RawMaterialsDO> list = rawMaterialsService.getRawMaterialsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "原材料信息.xls", "数据", RawMaterialsRespVO.class,
                        BeanUtils.toBean(list, RawMaterialsRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入项目信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<RawMaterialsImportVO> list = Collections.singletonList(
                RawMaterialsImportVO.builder()
                        .materialName("材料名称")
                        .materialType(BizMaterialTypeEnum.BIZ_MATERIAL_TYPE_0.getStatus())
                        .remark("备注").build());
        // 输出
        ExcelUtils.write(response, "原材料信息导入模板.xls", "原材料信息模板", RawMaterialsImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入项目信息")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
    })
    @PreAuthorize("@ss.hasPermission('biz:raw-materials:create')")
    public CommonResult<RawMaterialsImportRespVO> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<RawMaterialsImportVO> list = ExcelUtils.read(file, RawMaterialsImportVO.class);
        return success(rawMaterialsService.importRawMaterialsList(list));
    }

}
