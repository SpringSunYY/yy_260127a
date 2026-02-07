package com.lz.module.biz.controller.admin.worker.vo;

import com.lz.framework.common.validation.IdCard;
import com.lz.framework.common.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 工人信息新增/修改 Request VO")
@Data
public class WorkerSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10091")
    private Long id;

    @Schema(description = "工人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "工人姓名不能为空")
    private String workerName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "身份证号")
    @IdCard
    private String idCardNo;

    @Schema(description = "联系电话")
    @Mobile
    private String phone;

    @Schema(description = "工人类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "工人类型不能为空")
    private String workerType;

    @Schema(description = "工种", example = "2")
    private String workType;

    @Schema(description = "日薪")
    private BigDecimal dailySalary;

    @Schema(description = "月薪")
    private BigDecimal monthlySalary;

    @Schema(description = "薪资说明")
    private String salaryDesc;

    @Schema(description = "技能等级")
    private String skillLevel;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}
