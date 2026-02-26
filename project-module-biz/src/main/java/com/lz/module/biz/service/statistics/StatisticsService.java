package com.lz.module.biz.service.statistics;

import com.lz.module.biz.controller.admin.statistics.vo.StatisticsRequest;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsVo;
import jakarta.validation.Valid;

import java.util.List;

public interface StatisticsService {
    List<StatisticsVo<Float>> paymentStatistics(@Valid StatisticsRequest request);
}
