package com.lz.module.biz.controller.admin.projectOther.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 其他工程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProjectOtherRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11011")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_project_other_project_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String projectType;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String projectAddress;

    @Schema(description = "时间")
    @ExcelProperty("时间")
    private LocalDateTime projectDate;

    @Schema(description = "施工费")
    @ExcelProperty("施工费")
    private BigDecimal constructionFee;

    @Schema(description = "已结算", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "已结算", converter = DictConvert.class)
    @DictFormat("common_whether") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isSettled;

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String appendixFile;

    @Schema(description = "材料说明")
    @ExcelProperty("材料说明")
    private String materialDesc;

    @Schema(description = "进度", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "进度", converter = DictConvert.class)
    @DictFormat("biz_project_other_project_progress") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String progressStatus;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
