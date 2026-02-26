package com.lz.module.biz.service.statistics;

import com.lz.framework.common.util.date.DateUtils;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsRequest;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsVo;
import com.lz.module.biz.dal.dataobject.statistics.StatisticsDO;
import com.lz.module.biz.dal.mysql.statistics.StatisticsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计
 *
 * @Project: project
 * @Author: YY
 * @CreateTime: 2026-02-26  16:34
 * @Version: 1.0
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public List<StatisticsVo<Float>> paymentStatistics(StatisticsRequest request) {
        List<String> dataRange = DateUtils.getDataRange(request.getStartTime(), request.getEndTime(), request.getType());
        //格式化时间类型为MySQL需要类型
        String format = DateUtils.formatDateType(request.getType());
        request.setFormat(format);
        System.out.println("dataRange = " + dataRange);
        List<StatisticsDO<Float>> statisticsDOList = statisticsMapper.paymentStatistics(request);
        //初始时间范围
        Map<String, Float> resultMap = new LinkedHashMap<>();
        for (String time : dataRange) {
            resultMap.put(time, 0f);
        }
        System.out.println("statisticsDOList = " + statisticsDOList);
        System.out.println("resultMap = " + resultMap);
        for (StatisticsDO<Float> statisticsDO : statisticsDOList) {
            resultMap.put(statisticsDO.getName(), statisticsDO.getValue());
        }
        return resultMap.entrySet().stream().map(entry -> new StatisticsVo<Float>(entry.getKey(), entry.getValue())).toList();

    }

}
