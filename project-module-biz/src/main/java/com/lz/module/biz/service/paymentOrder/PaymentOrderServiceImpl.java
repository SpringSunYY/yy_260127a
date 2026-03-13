package com.lz.module.biz.service.paymentOrder;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.id.IdUtils;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportRespVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderPageReqVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.mysql.paymentOrder.PaymentOrderMapper;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;
import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;
import com.lz.module.biz.service.supplier.SupplierService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private SupplierMapper supplierMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private SupplierService supplierService;


    @Override
    public Long createPaymentOrder(PaymentOrderSaveReqVO createReqVO) {
        // 插入
        PaymentOrderDO paymentOrder = BeanUtils.toBean(createReqVO, PaymentOrderDO.class);
        SupplierDO supplierDO = judgePaymentSupplier(paymentOrder);
        paymentOrder.setPaymentNo(IdUtils.generateTimeRandomId());
        transactionTemplate.executeWithoutResult(status -> {
            supplierDO.setPaymentAmount(supplierDO.getPaymentAmount().add(paymentOrder.getPaymentAmount()));
            supplierService.updateSupplierAmount(supplierDO);
            paymentOrderMapper.insert(paymentOrder);
        });
        // 返回
        return paymentOrder.getId();
    }


    private SupplierDO judgePaymentSupplier(PaymentOrderDO orderDO) {
        SupplierDO supplierDO = supplierMapper.selectById(orderDO.getSupplierId());
        if (ObjUtil.isNull(supplierDO)) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
        orderDO.setSupplierName(supplierDO.getName());
        return supplierDO;
    }


    @Override
    public void updatePaymentOrder(PaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        PaymentOrderDO paymentOrderDO = validatePaymentOrderExists(updateReqVO.getId());
        // 更新
        PaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, PaymentOrderDO.class);
        SupplierDO supplierDO = judgePaymentSupplier(updateObj);
        //如果更改了供应商
        if (!paymentOrderDO.getSupplierId().equals(updateObj.getSupplierId())) {
            throw exception(PAYMENT_ORDER_SUPPLIER_CANNOT_UPDATE);
        }
        transactionTemplate.executeWithoutResult(status -> {
            //当前的金额,新的减去老的
            BigDecimal currentAmount = updateObj.getPaymentAmount().subtract(paymentOrderDO.getPaymentAmount());
            supplierDO.setPaymentAmount(supplierDO.getPaymentAmount().add(currentAmount));
            supplierService.updateSupplierAmount(supplierDO);
            paymentOrderMapper.updateById(updateObj);
        });
    }

    @Override
    @Transactional
    public void deletePaymentOrder(Long id) {
        // 校验存在
        PaymentOrderDO paymentOrderDO = validatePaymentOrderExists(id);
        //拿到供应商
        SupplierDO supplierDO = supplierMapper.selectById(paymentOrderDO.getSupplierId());
        if (ObjUtil.isNotNull(supplierDO)) {
            supplierDO.setPaymentAmount(supplierDO.getPaymentAmount().subtract(paymentOrderDO.getPaymentAmount()));
            supplierService.updateSupplierAmount(supplierDO);
        }
        paymentOrderMapper.deleteById(id);
    }

    @Override
    public void deletePaymentOrderListByIds(List<Long> ids) {
        //查询到所有的付款单
        LambdaQueryWrapper<PaymentOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PaymentOrderDO::getId, ids);
        List<PaymentOrderDO> paymentOrderDOS = paymentOrderMapper.selectList(queryWrapper);
        if (ArrayUtil.isEmpty(paymentOrderDOS))return;
        //拿到所有的供应商
        List<Long> supplierIds = paymentOrderDOS.stream().map(PaymentOrderDO::getSupplierId).collect(Collectors.toList());
        List<SupplierDO> supplierDOS = supplierMapper.selectByIds(supplierIds);
        Map<Long, SupplierDO> supplierDOMap = supplierDOS.stream().collect(Collectors.toMap(SupplierDO::getId, supplierDO -> supplierDO));
        for (PaymentOrderDO paymentOrderDO : paymentOrderDOS) {
            SupplierDO supplierDO = supplierDOMap.get(paymentOrderDO.getSupplierId());
            if (ObjUtil.isNotNull(supplierDO)) {
                supplierDO.setPaymentAmount(supplierDO.getPaymentAmount().subtract(paymentOrderDO.getPaymentAmount()));
                supplierService.updateSupplierAmount(supplierDO);
            }
        }
        transactionTemplate.executeWithoutResult(status -> {
            paymentOrderMapper.deleteByIds(ids);
            supplierDOMap.values().forEach(supplierDO -> supplierService.updateSupplierAmount(supplierDO));
        });

    }


    private PaymentOrderDO validatePaymentOrderExists(Long id) {
        PaymentOrderDO paymentOrderDO = paymentOrderMapper.selectById(id);
        if (paymentOrderDO == null) {
            throw exception(PAYMENT_ORDER_NOT_EXISTS);
        }
        return paymentOrderDO;
    }

    @Override
    public PaymentOrderDO getPaymentOrder(Long id) {
        return paymentOrderMapper.selectById(id);
    }

    @Override
    public PageResult<PaymentOrderDO> getPaymentOrderPage(PaymentOrderPageReqVO pageReqVO) {
        return paymentOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public BigDecimal getPaymentOrderAmount(PaymentOrderPageReqVO pageReqVO) {
        return paymentOrderMapper.getPaymentOrderAmount(pageReqVO);
    }

    @Override
    public PaymentOrderImportRespVO importPaymentOrderList(List<PaymentOrderImportVO> list) {
        //首先校验
        judgeImportData(list);
        Map<Long, SupplierDO> supplierDOMap = initImportPayeeData(list);
        //校验数据并转换为do
        List<PaymentOrderDO> paymentOrderDOS = initImportData(list,  supplierDOMap);
        transactionTemplate.executeWithoutResult(status -> {
            paymentOrderMapper.insertBatch(paymentOrderDOS);
            supplierDOMap.forEach((id, supplierDO) -> supplierService.updateSupplierAmount(supplierDO));
        });
        return PaymentOrderImportRespVO.builder().message("导入成功，成功导入" + paymentOrderDOS.size() + "条数据").build();
    }

    private List<PaymentOrderDO> initImportData(List<PaymentOrderImportVO> list, Map<Long, SupplierDO> supplierDOMap) {
        List<PaymentOrderDO> paymentOrderDOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
            PaymentOrderImportVO paymentOrderImportVO = list.get(i);
            BeanUtils.copyProperties(paymentOrderImportVO, paymentOrderDO);
            //供应商
            SupplierDO supplierDO = supplierDOMap.get(paymentOrderImportVO.getSupplierId());
            if (ObjUtil.isNull(supplierDO)) {
                throw new ServiceException(400, "第" + index + "行供应商不存在,请检查供应商编号");
            }
            paymentOrderDO.setSupplierId(supplierDO.getId());
            paymentOrderDO.setSupplierName(supplierDO.getName());
            //更新供应商付款金额
            supplierDO.setPaymentAmount(supplierDO.getPaymentAmount().add(paymentOrderDO.getPaymentAmount()));

            paymentOrderDO.setPaymentNo(IdUtils.generateTimeRandomId());
            paymentOrderDOList.add(paymentOrderDO);
        }
        return paymentOrderDOList;
    }

    private Map<Long, SupplierDO> initImportPayeeData(List<PaymentOrderImportVO> list) {
        List<Long> supplierIds = new ArrayList<>();
        for (PaymentOrderImportVO item : list) {
            supplierIds.add(item.getSupplierId());
        }
        List<SupplierDO> supplierDOS = new ArrayList<>();
        if (!supplierIds.isEmpty()) {
            supplierDOS = supplierMapper.selectByIds(supplierIds);
        }

        //创建一个map，key为项目类型-项目id，value为付款对象信息
        Map<Long, SupplierDO> supplierDOMap = new HashMap<>();
        supplierDOS.forEach(item -> {
            supplierDOMap.put(item.getId(), item);
        });
        return supplierDOMap;
    }

    private static void judgeImportData(List<PaymentOrderImportVO> list) {
        if (ArrayUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        for (int i = 0; i < list.size(); i++) {
            //是否开票、付款方式、付款单号、付款金额、付款时间、收款对象、是否开票不能为空
            PaymentOrderImportVO orderImportExcelVO = list.get(i);
            int index = i + 1;
            if (StrUtil.isEmpty(orderImportExcelVO.getIsInvoiced())) {
                throw new ServiceException(400, "第" + index + "行数据，是否开票不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getPaymentMethod())) {
                throw new ServiceException(400, "第" + index + "行数据，付款方式不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getPaymentAmount())) {
                throw new ServiceException(400, "第" + index + "行数据，付款金额不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getPaymentTime())) {
                throw new ServiceException(400, "第" + index + "行数据，付款时间不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getSupplierId())) {
                throw new ServiceException(400, "第" + index + "行数据，供应商ID不能为空");
            }
        }
    }

}
