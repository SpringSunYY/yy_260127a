package com.lz.module.biz.service.purchaseOrder;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.*;
import com.lz.module.biz.dal.dataobject.purchaseOrder.PurchaseOrderDO;
import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 采购信息 Service 接口
 *
 * @author YY
 */
public interface PurchaseOrderService {

    /**
     * 创建采购信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPurchaseOrder(@Valid PurchaseOrderSaveReqVO createReqVO);

    /**
     * 更新采购信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePurchaseOrder(@Valid PurchaseOrderSaveReqVO updateReqVO);

    /**
     * 删除采购信息
     *
     * @param id 编号
     */
    void deletePurchaseOrder(Long id);

    /**
    * 批量删除采购信息
    *
    * @param ids 编号
    */
    void deletePurchaseOrderListByIds(List<Long> ids);

    /**
     * 获得采购信息
     *
     * @param id 编号
     * @return 采购信息
     */
    PurchaseOrderDO getPurchaseOrder(Long id);

    /**
     * 获得采购信息分页
     *
     * @param pageReqVO 分页查询
     * @return 采购信息分页
     */
    PageResult<PurchaseOrderDO> getPurchaseOrderPage(PurchaseOrderPageReqVO pageReqVO);

    // ==================== 子表（采购明细） ====================

    /**
     * 获得采购明细列表
     *
     * @param purchaseId 采购单编号
     * @return 采购明细列表
     */
    List<PurchaseOrderDetailDO> getPurchaseOrderDetailListByPurchaseId(Long purchaseId);

}