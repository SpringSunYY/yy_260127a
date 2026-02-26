package com.lz.module.biz.controller.admin.receiptOrder.vo;

import com.lz.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收款信息分页 Request VO")
@Data
public class ReceiptOrderPageReqVO extends PageParam {

    @Schema(description = "收款单号")
    private String receiptNo;

    @Schema(description = "收款类型", example = "1")
    private String receiptType;

    @Schema(description = "项目类型", example = "2")
    private String projectType;

    @Schema(description = "工程类型", example = "1")
    private String projectScatteredType;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "李四")
    private String projectName;

    @Schema(description = "财年")
    private Integer fiscalYear;

    @Schema(description = "付款方", example = "赵六")
    private String payerName;

    @Schema(description = "收款日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] receiptDate;

    @Schema(description = "收款方式")
    private String receiptMethod;

    @Schema(description = "是否开票")
    private String isInvoiced;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
