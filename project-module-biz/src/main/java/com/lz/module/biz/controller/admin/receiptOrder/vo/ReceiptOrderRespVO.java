package com.lz.module.biz.controller.admin.receiptOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 收款信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReceiptOrderRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21549")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "收款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收款单号")
    private String receiptNo;

    @Schema(description = "收款类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "收款类型", converter = DictConvert.class)
    @DictFormat("biz_receipt_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String receiptType;

    @Schema(description = "项目类型", example = "2")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_receipt_project_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String projectType;

    @Schema(description = "工程类型", example = "1")
    @ExcelProperty(value = "工程类型", converter = DictConvert.class)
    @DictFormat("biz_project_engineering_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String projectScatteredType;

    @Schema(description = "财年")
    @ExcelProperty("财年")
    private Integer fiscalYear;

    @Schema(description = "项目ID", example = "5697")
    @ExcelProperty("项目ID")
    private Long projectId;

    @Schema(description = "项目编号")
    @ExcelProperty("项目编号")
    private String projectNo;

    @Schema(description = "项目名称", example = "李四")
    @ExcelProperty("项目名称")
    private String projectName;

    @Schema(description = "付款方", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("付款方")
    private String payerName;

    @Schema(description = "收款日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收款日期")
    private LocalDateTime receiptDate;

    @Schema(description = "收款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收款金额")
    private BigDecimal receiptAmount;

    @Schema(description = "收款方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "收款方式", converter = DictConvert.class)
    @DictFormat("biz_receivable_return_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String receiptMethod;

    @Schema(description = "收款凭证")
    @ExcelProperty("收款凭证")
    private String receiptCertificate;

    @Schema(description = "收款事由")
    @ExcelProperty("收款事由")
    private String receiptPurpose;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否开票", converter = DictConvert.class)
    @DictFormat("common_whether") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String isInvoiced;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}