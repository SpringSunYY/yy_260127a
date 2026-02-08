package com.lz.module.biz.controller.admin.receiptOrder.vo;

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

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class ReceiptOrderImportExcelVO {

    @Schema(description = "收款单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("收款单号")
    private String receiptNo;

    @Schema(description = "收款类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "收款类型", converter = DictConvert.class)
    @DictFormat("biz_receipt_type")
    private String receiptType;

    @Schema(description = "项目类型", example = "2")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("biz_receipt_project_type")
    private String projectType;

    @Schema(description = "项目ID", example = "5697")
    @ExcelProperty("项目ID")
    private Long projectId;

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
    @DictFormat("biz_receipt_method")
    private String receiptMethod;


    @Schema(description = "收款事由")
    @ExcelProperty("收款事由")
    private String receiptPurpose;

    @Schema(description = "是否开票", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否开票", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isInvoiced;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;


}
