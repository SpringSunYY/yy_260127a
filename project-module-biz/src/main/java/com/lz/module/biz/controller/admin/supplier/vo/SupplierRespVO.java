package com.lz.module.biz.controller.admin.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 供应商信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SupplierRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "170")
    @ExcelProperty("编号")
    private Long id;

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

    @Schema(description = "地区编号", example = "7671")
    @ExcelProperty("地区编号")
    private Long areaId;

    @Schema(description = "详细地址")
    @ExcelProperty("详细地址")
    private String detailAddress;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}