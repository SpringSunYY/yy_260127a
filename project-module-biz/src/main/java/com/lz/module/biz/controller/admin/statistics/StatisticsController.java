package com.lz.module.biz.controller.admin.statistics;

import com.lz.module.biz.service.statistics.StatisticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析
 *
 * @Project: project
 * @Author: YY
 * @CreateTime: 2026-02-26  16:24
 * @Version: 1.0
 */
@Tag(name = "管理后台 - 数据统计")
@RestController
@RequestMapping("/biz/statistics")
@Validated
public class StatisticsController {
    @Resource
    private StatisticsService statisticsService;
}
