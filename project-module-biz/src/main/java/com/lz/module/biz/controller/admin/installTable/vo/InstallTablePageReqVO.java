package com.lz.module.biz.controller.admin.installTable.vo;

import com.lz.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 装表信息分页 Request VO")
@Data
public class InstallTablePageReqVO extends PageParam {

    @Schema(description = "安装日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime[] installDate;

    @Schema(description = "小区名称", example = "张三")
    private String communityName;

    @Schema(description = "表号")
    private String meterNo;

    @Schema(description = "型号")
    private String meterModel;

    @Schema(description = "表向")
    private String meterDirection;

    @Schema(description = "超长费用(元)")
    private BigDecimal extraLengthFee;

    @Schema(description = "安装人员", example = "赵六")
    private String installerName;

    @Schema(description = "高空作业")
    private String isHighAltitude;

    @Schema(description = "开T")
    private String isOpenTee;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
