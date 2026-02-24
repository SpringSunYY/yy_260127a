package com.lz.module.biz.controller.admin.paymentOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 付款信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PaymentOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2060")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "付款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款单号")
    private String paymentNo;

    @Schema(description = "项目ID", example = "11356")
    @ExcelProperty("项目ID")
    private Long projectId;

    @Schema(description = "项目类型", example = "2")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_receipt_project_type")
    private String projectType;

    @Schema(description = "项目名称", example = "赵六")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "收款对象类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "收款对象类型", converter = DictConvert.class)
    @DictFormat("biz_payment_payee_type")
    private String payeeType;

    @Schema(description = "收款对象ID", example = "2609")
    @ExcelProperty("收款对象ID")
    private Long payeeId;

    @Schema(description = "收款对象名称", example = "赵六")
    @ExcelProperty("收款对象名称")
    private String payeeName;

    @Schema(description = "付款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("付款日期")
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

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}