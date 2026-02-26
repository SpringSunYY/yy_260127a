package com.lz.module.biz.dal.mysql.statistics;

import com.lz.module.biz.controller.admin.statistics.vo.StatisticsRequest;
import com.lz.module.biz.dal.dataobject.statistics.StatisticsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    /**
     * 统计付款信息
     *
     * @param request
     * @return
     */
    List<StatisticsDO<Float>> paymentStatistics(StatisticsRequest request);

    /**
     * 统计收款信息
     *
     * @param request
     * @return
     */
    List<StatisticsDO<Float>> receiptStatistics(StatisticsRequest request);

    /**
     * 统计员工工资信息
     *
     * @param request
     * @return
     */
    List<StatisticsDO<Float>> salaryStatistics(StatisticsRequest request);
}
