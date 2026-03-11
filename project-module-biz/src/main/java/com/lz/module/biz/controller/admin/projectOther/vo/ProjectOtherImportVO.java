package com.lz.module.biz.controller.admin.projectOther.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 其他工程 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class ProjectOtherImportVO {


    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_project_other_project_type")
    private String projectType;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String projectAddress;

    @Schema(description = "时间")
    @ExcelProperty("时间")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime projectDate;

    @Schema(description = "施工费")
    @ExcelProperty("施工费")
    private BigDecimal constructionFee;

    @Schema(description = "已结算", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "已结算", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isSettled;


    @Schema(description = "材料说明")
    @ExcelProperty("材料说明")
    private String materialDesc;

    @Schema(description = "进度", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "进度", converter = DictConvert.class)
    @DictFormat("biz_project_other_project_progress")
    private String progressStatus;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

}
