package com.lz.module.biz.dal.mysql.purchaseOrderDetail;

import java.util.*;

import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购明细 Mapper
 *
 * @author YY
 */
@Mapper
public interface PurchaseOrderDetailMapper extends BaseMapperX<PurchaseOrderDetailDO> {

    default List<PurchaseOrderDetailDO> selectListByPurchaseId(Long purchaseId) {
        return selectList(PurchaseOrderDetailDO::getPurchaseId, purchaseId);
    }

    default int deleteByPurchaseId(Long purchaseId) {
        return delete(PurchaseOrderDetailDO::getPurchaseId, purchaseId);
    }

	default int deleteByPurchaseIds(List<Long> purchaseIds) {
	    return deleteBatch(PurchaseOrderDetailDO::getPurchaseId, purchaseIds);
	}

}
