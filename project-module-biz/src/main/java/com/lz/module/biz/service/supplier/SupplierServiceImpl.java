package com.lz.module.biz.service.supplier;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.supplier.vo.*;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

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

}