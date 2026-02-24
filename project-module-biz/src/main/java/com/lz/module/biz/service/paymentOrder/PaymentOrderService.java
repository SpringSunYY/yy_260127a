package com.lz.module.biz.service.paymentOrder;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.paymentOrder.vo.*;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 付款信息 Service 接口
 *
 * @author YY
 */
public interface PaymentOrderService {

    /**
     * 创建付款信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPaymentOrder(@Valid PaymentOrderSaveReqVO createReqVO);

    /**
     * 更新付款信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePaymentOrder(@Valid PaymentOrderSaveReqVO updateReqVO);

    /**
     * 删除付款信息
     *
     * @param id 编号
     */
    void deletePaymentOrder(Long id);

    /**
    * 批量删除付款信息
    *
    * @param ids 编号
    */
    void deletePaymentOrderListByIds(List<Long> ids);

    /**
     * 获得付款信息
     *
     * @param id 编号
     * @return 付款信息
     */
    PaymentOrderDO getPaymentOrder(Long id);

    /**
     * 获得付款信息分页
     *
     * @param pageReqVO 分页查询
     * @return 付款信息分页
     */
    PageResult<PaymentOrderDO> getPaymentOrderPage(PaymentOrderPageReqVO pageReqVO);

}