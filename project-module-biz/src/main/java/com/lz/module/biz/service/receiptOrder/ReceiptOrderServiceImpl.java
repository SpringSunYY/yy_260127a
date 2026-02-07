package com.lz.module.biz.service.receiptOrder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.receiptOrder.vo.*;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.receiptOrder.ReceiptOrderMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 收款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ReceiptOrderServiceImpl implements ReceiptOrderService {

    @Resource
    private ReceiptOrderMapper receiptOrderMapper;

    @Override
    public Long createReceiptOrder(ReceiptOrderSaveReqVO createReqVO) {
        // 插入
        ReceiptOrderDO receiptOrder = BeanUtils.toBean(createReqVO, ReceiptOrderDO.class);
        receiptOrderMapper.insert(receiptOrder);

        // 返回
        return receiptOrder.getId();
    }

    @Override
    public void updateReceiptOrder(ReceiptOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReceiptOrderExists(updateReqVO.getId());
        // 更新
        ReceiptOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReceiptOrderDO.class);
        receiptOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteReceiptOrder(Long id) {
        // 校验存在
        validateReceiptOrderExists(id);
        // 删除
        receiptOrderMapper.deleteById(id);
    }

    @Override
        public void deleteReceiptOrderListByIds(List<Long> ids) {
        // 删除
        receiptOrderMapper.deleteByIds(ids);
        }


    private void validateReceiptOrderExists(Long id) {
        if (receiptOrderMapper.selectById(id) == null) {
            throw exception(RECEIPT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ReceiptOrderDO getReceiptOrder(Long id) {
        return receiptOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReceiptOrderDO> getReceiptOrderPage(ReceiptOrderPageReqVO pageReqVO) {
        return receiptOrderMapper.selectPage(pageReqVO);
    }

}