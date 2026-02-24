package com.lz.module.biz.service.paymentOrder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.paymentOrder.vo.*;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.paymentOrder.PaymentOrderMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 付款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    @Override
    public Long createPaymentOrder(PaymentOrderSaveReqVO createReqVO) {
        // 插入
        PaymentOrderDO paymentOrder = BeanUtils.toBean(createReqVO, PaymentOrderDO.class);
        paymentOrderMapper.insert(paymentOrder);

        // 返回
        return paymentOrder.getId();
    }

    @Override
    public void updatePaymentOrder(PaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        validatePaymentOrderExists(updateReqVO.getId());
        // 更新
        PaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, PaymentOrderDO.class);
        paymentOrderMapper.updateById(updateObj);
    }

    @Override
    public void deletePaymentOrder(Long id) {
        // 校验存在
        validatePaymentOrderExists(id);
        // 删除
        paymentOrderMapper.deleteById(id);
    }

    @Override
        public void deletePaymentOrderListByIds(List<Long> ids) {
        // 删除
        paymentOrderMapper.deleteByIds(ids);
        }


    private void validatePaymentOrderExists(Long id) {
        if (paymentOrderMapper.selectById(id) == null) {
            throw exception(PAYMENT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public PaymentOrderDO getPaymentOrder(Long id) {
        return paymentOrderMapper.selectById(id);
    }

    @Override
    public PageResult<PaymentOrderDO> getPaymentOrderPage(PaymentOrderPageReqVO pageReqVO) {
        return paymentOrderMapper.selectPage(pageReqVO);
    }

}