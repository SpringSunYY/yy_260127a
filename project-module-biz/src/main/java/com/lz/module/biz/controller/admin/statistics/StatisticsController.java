package com.lz.module.biz.controller.admin.statistics;

import com.lz.framework.common.pojo.CommonResult;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderPageReqVO;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsRequest;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsVo;
import com.lz.module.biz.service.statistics.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.lz.framework.common.pojo.CommonResult.success;

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

    @GetMapping("/payment")
    @Operation(summary = "获得付款信息统计")
    @PreAuthorize("@ss.hasPermission('biz:payment-order:query')")
    public CommonResult<List<StatisticsVo<Float>>> paymentStatistics(@Valid StatisticsRequest request) {
        List<StatisticsVo<Float>> result = statisticsService.paymentStatistics(request);
        return success(result);
    }
}
