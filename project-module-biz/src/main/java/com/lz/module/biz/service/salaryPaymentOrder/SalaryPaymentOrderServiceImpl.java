package com.lz.module.biz.service.salaryPaymentOrder;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.*;
import com.lz.module.biz.dal.dataobject.salaryPaymentOrder.SalaryPaymentOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.salaryPaymentOrder.SalaryPaymentOrderMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 工资付款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class SalaryPaymentOrderServiceImpl implements SalaryPaymentOrderService {

    @Resource
    private SalaryPaymentOrderMapper salaryPaymentOrderMapper;

    @Override
    public Long createSalaryPaymentOrder(SalaryPaymentOrderSaveReqVO createReqVO) {
        // 插入
        SalaryPaymentOrderDO salaryPaymentOrder = BeanUtils.toBean(createReqVO, SalaryPaymentOrderDO.class);
        salaryPaymentOrderMapper.insert(salaryPaymentOrder);

        // 返回
        return salaryPaymentOrder.getId();
    }

    @Override
    public void updateSalaryPaymentOrder(SalaryPaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateSalaryPaymentOrderExists(updateReqVO.getId());
        // 更新
        SalaryPaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, SalaryPaymentOrderDO.class);
        salaryPaymentOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalaryPaymentOrder(Long id) {
        // 校验存在
        validateSalaryPaymentOrderExists(id);
        // 删除
        salaryPaymentOrderMapper.deleteById(id);
    }

    @Override
        public void deleteSalaryPaymentOrderListByIds(List<Long> ids) {
        // 删除
        salaryPaymentOrderMapper.deleteByIds(ids);
        }


    private void validateSalaryPaymentOrderExists(Long id) {
        if (salaryPaymentOrderMapper.selectById(id) == null) {
            throw exception(SALARY_PAYMENT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public SalaryPaymentOrderDO getSalaryPaymentOrder(Long id) {
        return salaryPaymentOrderMapper.selectById(id);
    }

    @Override
    public PageResult<SalaryPaymentOrderDO> getSalaryPaymentOrderPage(SalaryPaymentOrderPageReqVO pageReqVO) {
        return salaryPaymentOrderMapper.selectPage(pageReqVO);
    }

}