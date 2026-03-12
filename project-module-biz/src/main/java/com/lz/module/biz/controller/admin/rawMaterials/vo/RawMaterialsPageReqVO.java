package com.lz.module.biz.controller.admin.rawMaterials.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.lz.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 原材料信息分页 Request VO")
@Data
public class RawMaterialsPageReqVO extends PageParam {

    @Schema(description = "材料名称", example = "赵六")
    private String materialName;

    @Schema(description = "规格类别", example = "2")
    private String materialType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
