package com.lz.module.biz.controller.admin.statistics.vo;

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
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
