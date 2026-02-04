package com.lz.module.biz.controller.admin.customer.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

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

    @Schema(description = "电话")
    @ExcelProperty("电话")
    private String telephone;

    @Schema(description = "QQ")
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "微信")
    @ExcelProperty("微信")
    private String weChat;

    @Schema(description = "用户邮箱", example = "project@iocoder.cn")
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
