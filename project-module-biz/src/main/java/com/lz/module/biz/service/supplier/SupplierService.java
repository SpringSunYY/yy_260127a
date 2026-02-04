package com.lz.module.biz.service.supplier;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.supplier.vo.*;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 供应商信息 Service 接口
 *
 * @author YY
 */
public interface SupplierService {

    /**
     * 创建供应商信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplier(@Valid SupplierSaveReqVO createReqVO);

    /**
     * 更新供应商信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplier(@Valid SupplierSaveReqVO updateReqVO);

    /**
     * 删除供应商信息
     *
     * @param id 编号
     */
    void deleteSupplier(Long id);

    /**
    * 批量删除供应商信息
    *
    * @param ids 编号
    */
    void deleteSupplierListByIds(List<Long> ids);

    /**
     * 获得供应商信息
     *
     * @param id 编号
     * @return 供应商信息
     */
    SupplierDO getSupplier(Long id);

    /**
     * 获得供应商信息分页
     *
     * @param pageReqVO 分页查询
     * @return 供应商信息分页
     */
    PageResult<SupplierDO> getSupplierPage(SupplierPageReqVO pageReqVO);

}