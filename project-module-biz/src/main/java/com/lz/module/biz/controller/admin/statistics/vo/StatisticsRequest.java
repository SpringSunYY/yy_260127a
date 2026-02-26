package com.lz.module.biz.controller.admin.statistics.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 统计
 *
 * @Project: Lecture
 * @Author: YY
 * @CreateTime: 2026-01-18  19:27
 * @Version: 1.0
 */
@Data
public class StatisticsRequest {

    /**
     * 开始时间
     */
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;
    /**
     * 结束时间
     */
    @NotEmpty(message = "结束时间不能为空")
    private String endTime;

    /**
     * 时间类型
     */
    @NotEmpty(message = "时间类型不能为空")
    private String type;

    /**
     * 时间格式
     */
    private String format;
}
