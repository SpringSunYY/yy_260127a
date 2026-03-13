package com.lz.module.biz.controller.admin.worker.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "管理后台 - 工人信息 导入 VO")
@Data
@Builder
public class WorkerImportRespVO {

    @Schema(description = "信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
