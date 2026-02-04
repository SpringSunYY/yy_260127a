package com.lz.module.biz.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 客户信息新增/修改 Request VO")
@Data
public class CustomerSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23068")
    private Long id;

    @Schema(description = "客户名称", example = "赵六")
    private String name;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String weChat;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "地区编号", example = "22553")
    private Long areaId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "所属行业")
    private String industry;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}