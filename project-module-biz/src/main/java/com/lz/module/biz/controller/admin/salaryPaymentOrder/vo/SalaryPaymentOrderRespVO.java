package com.lz.module.biz.controller.admin.salaryPaymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 工资付款信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SalaryPaymentOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18815")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "付款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款单号")
    private String paymentNo;

    @Schema(description = "工资ID", example = "22538")
    @ExcelProperty("工资ID")
    private Long salaryId;

    @Schema(description = "工资名称", example = "赵六")
    @ExcelProperty("工资名称")
    private String salaryName;

    @Schema(description = "工人ID", example = "31798")
    @ExcelProperty("工人ID")
    private Long workerId;

    @Schema(description = "工人名称", example = "张三")
    @ExcelProperty("工人名称")
    private String workerName;

    @Schema(description = "付款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款日期")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime paymentTime;

    @Schema(description = "付款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款金额")
    private BigDecimal paymentAmount;

    @Schema(description = "付款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "付款方式", converter = DictConvert.class)
    @DictFormat("biz_receipt_method")
    private String paymentMethod;

    @Schema(description = "付款凭证")
    @ExcelProperty("付款凭证")
    private String paymentCertificate;

    @Schema(description = "付款事由")
    @ExcelProperty("付款事由")
    private String paymentPurpose;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否开票", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isInvoiced;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
