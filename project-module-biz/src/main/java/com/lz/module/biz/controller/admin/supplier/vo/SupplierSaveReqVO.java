package com.lz.module.biz.controller.admin.supplier.vo;

import com.lz.framework.common.validation.Mobile;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 供应商信息新增/修改 Request VO")
@Data
public class SupplierSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "170")
    private Long id;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "供应商名称不能为空")
    private String name;

    @Schema(description = "电话", example = "15601691300")
    @Mobile
    @DiffLogField(name = "电话")
    private String telephone;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String weChat;

    @Schema(description = "用户邮箱", example = "project@iocoder.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    @DiffLogField(name = "用户邮箱")
    private String email;

    @Schema(description = "地区编号", example = "7671")
    private Long areaId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
