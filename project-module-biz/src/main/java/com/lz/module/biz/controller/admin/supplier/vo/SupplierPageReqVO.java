package com.lz.module.biz.controller.admin.supplier.vo;

import com.lz.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 供应商信息分页 Request VO")
@Data
public class SupplierPageReqVO extends PageParam {

    @Schema(description = "供应商名称", example = "芋艿")
    private String name;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String weChat;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "付款金额")
    private BigDecimal paymentAmount;

    @Schema(description = "欠款金额")
    private BigDecimal debtAmount;

    @Schema(description = "应付金额")
    private BigDecimal payableAmount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "排序字段（仅允许：paymentAmount、debtAmount、payableAmount）", example = "paymentAmount")
    private String orderBy;

    @Schema(description = "排序方向（asc/desc）", example = "desc")
    private String order;

}
