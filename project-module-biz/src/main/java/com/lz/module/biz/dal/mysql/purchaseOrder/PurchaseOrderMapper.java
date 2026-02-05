package com.lz.module.biz.dal.mysql.purchaseOrder;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.purchaseOrder.PurchaseOrderDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.*;

/**
 * 采购信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface PurchaseOrderMapper extends BaseMapperX<PurchaseOrderDO> {

    default PageResult<PurchaseOrderDO> selectPage(PurchaseOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PurchaseOrderDO>()
                .likeIfPresent(PurchaseOrderDO::getOrderNo, reqVO.getOrderNo())
                .likeIfPresent(PurchaseOrderDO::getName, reqVO.getName())
                .eqIfPresent(PurchaseOrderDO::getSupplierId, reqVO.getSupplierId())
                .likeIfPresent(PurchaseOrderDO::getSupplierName, reqVO.getSupplierName())
                .likeIfPresent(PurchaseOrderDO::getPurchaserName, reqVO.getPurchaserName())
                .betweenIfPresent(PurchaseOrderDO::getExpectedTime, reqVO.getExpectedTime())
                .betweenIfPresent(PurchaseOrderDO::getActualTime, reqVO.getActualTime())
                .eqIfPresent(PurchaseOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .betweenIfPresent(PurchaseOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PurchaseOrderDO::getId));
    }

}