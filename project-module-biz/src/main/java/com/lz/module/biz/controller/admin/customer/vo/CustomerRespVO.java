package com.lz.module.biz.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 客户信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23068")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "客户名称", example = "赵六")
    @ExcelProperty("客户名称")
    private String name;

    @Schema(description = "手机")
    @ExcelProperty("手机")
    private String mobile;

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

    @Schema(description = "地区编号", example = "22553")
    @ExcelProperty("地区编号")
    private Long areaId;

    @Schema(description = "详细地址")
    @ExcelProperty("详细地址")
    private String detailAddress;

    @Schema(description = "所属行业")
    @ExcelProperty(value = "所属行业", converter = DictConvert.class)
    @DictFormat("biz_customer_industry") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String industry;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}