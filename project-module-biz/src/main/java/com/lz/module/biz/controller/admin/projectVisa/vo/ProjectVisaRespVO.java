package com.lz.module.biz.controller.admin.projectVisa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 项目签证 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProjectVisaRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2242")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24280")
    @ExcelProperty("项目ID")
    private Long projectId;

    @Schema(description = "项目编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("项目编号")
    private String projectNo;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("项目名称")
    private String name;

    @Schema(description = "工程名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("工程名称")
    private String visaName;

    @Schema(description = "签证内容")
    @ExcelProperty("签证内容")
    private String visaContent;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("金额")
    private BigDecimal amount;

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String appendixFile;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
