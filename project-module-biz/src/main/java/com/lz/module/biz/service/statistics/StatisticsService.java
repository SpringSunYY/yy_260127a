package com.lz.module.biz.service.statistics;

import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderPageReqVO;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsRequest;
import com.lz.module.biz.controller.admin.statistics.vo.StatisticsVo;
import jakarta.validation.Valid;

import java.util.List;

public interface StatisticsService {
    /**
     * 付款信息统计
     *
     * @param request
     * @return
     */
    List<StatisticsVo<Float>> paymentStatistics(@Valid StatisticsRequest request);

    /**
     * 收款信息统计
     *
     * @param pageReqVO
     * @return
     */
    List<StatisticsVo<Float>> receiptStatistics(@Valid StatisticsRequest request);
}
