package com.lz.module.biz.service.paymentOrder;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderPageReqVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.mysql.customer.CustomerMapper;
import com.lz.module.biz.dal.mysql.paymentOrder.PaymentOrderMapper;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;
import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import com.lz.module.biz.enums.BizPaymentPayeeEnum;
import com.lz.module.biz.enums.BizReceiptProjectTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
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

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectOtherMapper projectOtherMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private WorkerMapper workerMapper;

    @Override
    public Long createPaymentOrder(PaymentOrderSaveReqVO createReqVO) {
        // 插入
        PaymentOrderDO paymentOrder = BeanUtils.toBean(createReqVO, PaymentOrderDO.class);
        initPaymentOrder(paymentOrder);
        paymentOrderMapper.insert(paymentOrder);

        // 返回
        return paymentOrder.getId();
    }

    private void initPaymentOrder(PaymentOrderDO orderDO) {
        judgePaymentProject(orderDO);
        judgePaymentPayeeType(orderDO);
    }

    private void judgePaymentPayeeType(PaymentOrderDO orderDO) {
        if (StrUtil.isEmpty(orderDO.getPayeeType())) {
            return;
        }
        //如果是客户
        if (StrUtil.equals(orderDO.getPayeeType(), BizPaymentPayeeEnum.BIZ_PAYMENT_PAYEE_TYPE_3.getStatus())) {
            CustomerDO customerDO = customerMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(customerDO)) {
                throw exception(CUSTOMER_NOT_EXISTS);
            }
            orderDO.setPayeeName(customerDO.getName());
        }else if (StrUtil.equals(orderDO.getPayeeType(), BizPaymentPayeeEnum.BIZ_PAYMENT_PAYEE_TYPE_2.getStatus())) {
            SupplierDO supplierDO = supplierMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(supplierDO)) {
                throw exception(SUPPLIER_NOT_EXISTS);
            }
            orderDO.setPayeeName(supplierDO.getName());
        } else {
            orderDO.setPayeeType(BizPaymentPayeeEnum.BIZ_PAYMENT_PAYEE_TYPE_1.getStatus());
            WorkerDO workerDO = workerMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(workerDO)) {
                throw exception(WORKER_NOT_EXISTS);
            }
            orderDO.setPayeeName(workerDO.getWorkerName());
        }
    }

    private void judgePaymentProject(PaymentOrderDO orderDO) {
        //首先判断是否选择了项目类型
        if (StrUtil.isEmpty(orderDO.getProjectType())) {
            return;
        }
        //判断项目类型是什么
        if (StrUtil.equals(orderDO.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_2.getStatus())) {
            //查询项目是否存在
            ProjectOtherDO projectOtherDO = projectOtherMapper.selectById(orderDO.getProjectId());
            if (ObjUtil.isNull(projectOtherDO)) {
                throw exception(PROJECT_OTHER_NOT_EXISTS);
            }
            orderDO.setProjectName(projectOtherDO.getProjectName());
        } else {
            orderDO.setProjectType(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus());
            //默认就是这个
            ProjectDO projectDO = projectMapper.selectById(orderDO.getProjectId());
            if (ObjUtil.isNull(projectDO)) {
                throw exception(PROJECT_NOT_EXISTS);
            }
            orderDO.setProjectName(projectDO.getName());
        }
    }

    @Override
    public void updatePaymentOrder(PaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        validatePaymentOrderExists(updateReqVO.getId());
        // 更新
        PaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, PaymentOrderDO.class);
        initPaymentOrder(updateObj);
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
