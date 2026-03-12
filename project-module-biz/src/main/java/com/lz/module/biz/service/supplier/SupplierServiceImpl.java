package com.lz.module.biz.service.supplier;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.customer.vo.CustomerImportRespVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierImportVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierPageReqVO;
import com.lz.module.biz.controller.admin.supplier.vo.SupplierSaveReqVO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.SUPPLIER_NOT_EXISTS;

/**
 * 供应商信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public Long createSupplier(SupplierSaveReqVO createReqVO) {
        // 插入
        SupplierDO supplier = BeanUtils.toBean(createReqVO, SupplierDO.class);
        supplierMapper.insert(supplier);

        // 返回
        return supplier.getId();
    }

    @Override
    public void updateSupplier(SupplierSaveReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        SupplierDO updateObj = BeanUtils.toBean(updateReqVO, SupplierDO.class);
        supplierMapper.updateById(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierMapper.deleteById(id);
    }

    @Override
    public void deleteSupplierListByIds(List<Long> ids) {
        // 删除
        supplierMapper.deleteByIds(ids);
    }


    private void validateSupplierExists(Long id) {
        if (supplierMapper.selectById(id) == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
    }

    @Override
    public SupplierDO getSupplier(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public PageResult<SupplierDO> getSupplierPage(SupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO);
    }

    @Override
    public CustomerImportRespVO importSupplierList(List<SupplierImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<SupplierDO> createList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            SupplierImportVO supplierImportVO = list.get(i);
            SupplierDO supplierDO = BeanUtils.toBean(supplierImportVO, SupplierDO.class);
            createList.add(supplierDO);
        }
        supplierMapper.insertBatch(createList);
        return CustomerImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个供应商信息", createList.size())).build();
    }

    @Override
    public void updateSupplierAmount(SupplierDO supplierDO) {
        //应付金额-已付金额=欠款金额
        BigDecimal payableAmount = supplierDO.getPayableAmount();
        BigDecimal paymentAmount = supplierDO.getPaymentAmount();
        if (ObjUtil.isNotNull(payableAmount) && ObjUtil.isNotNull(paymentAmount)) {
            BigDecimal debtAmount = payableAmount.subtract(paymentAmount);
            if (debtAmount.compareTo(BigDecimal.ZERO) <= 0) {
                debtAmount = BigDecimal.ZERO;
            }
            supplierDO.setDebtAmount(debtAmount);
            if (paymentAmount.compareTo(BigDecimal.ZERO)<=0){
                supplierDO.setPaymentAmount(BigDecimal.ZERO);
            }
            if (payableAmount.compareTo(BigDecimal.ZERO)<=0){
                supplierDO.setPayableAmount(BigDecimal.ZERO);
            }
        }
        supplierMapper.updateById(supplierDO);
    }

}
