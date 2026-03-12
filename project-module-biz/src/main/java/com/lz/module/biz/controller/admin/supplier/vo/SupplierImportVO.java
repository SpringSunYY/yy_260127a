package com.lz.module.biz.controller.admin.supplier.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 供应商信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class SupplierImportVO {

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("供应商名称")
    private String name;

    @Schema(description = "电话")
    @ExcelProperty("电话")
    private String telephone;

    @Schema(description = "QQ")
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "微信")
    @ExcelProperty("微信")
    private String weChat;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "地区编号", example = "510000")
    @ExcelProperty("地区编号")
    private Long areaId;

    @Schema(description = "详细地址")
    @ExcelProperty("详细地址")
    private String detailAddress;

    @Schema(description = "付款金额")
    @ExcelProperty("付款金额")
    private BigDecimal paymentAmount;

    @Schema(description = "欠款金额")
    @ExcelProperty("欠款金额")
    private BigDecimal debtAmount;

    @Schema(description = "应付金额")
    @ExcelProperty("应付金额")
    private BigDecimal payableAmount;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}
