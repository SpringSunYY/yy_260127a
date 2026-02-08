package com.lz.module.biz.dal.mysql.receiptOrder;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.receiptOrder.vo.*;

/**
 * 收款信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface ReceiptOrderMapper extends BaseMapperX<ReceiptOrderDO> {

    default PageResult<ReceiptOrderDO> selectPage(ReceiptOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReceiptOrderDO>()
                .likeIfPresent(ReceiptOrderDO::getReceiptNo, reqVO.getReceiptNo())
                .eqIfPresent(ReceiptOrderDO::getReceiptType, reqVO.getReceiptType())
                .eqIfPresent(ReceiptOrderDO::getProjectType, reqVO.getProjectType())
                .eqIfPresent(ReceiptOrderDO::getProjectScatteredType, reqVO.getProjectScatteredType())
                .likeIfPresent(ReceiptOrderDO::getProjectNo, reqVO.getProjectNo())
                .eqIfPresent(ReceiptOrderDO::getFiscalYear, reqVO.getFiscalYear())
                .likeIfPresent(ReceiptOrderDO::getProjectName, reqVO.getProjectName())
                .likeIfPresent(ReceiptOrderDO::getPayerName, reqVO.getPayerName())
                .betweenIfPresent(ReceiptOrderDO::getReceiptDate, reqVO.getReceiptDate())
                .eqIfPresent(ReceiptOrderDO::getReceiptMethod, reqVO.getReceiptMethod())
                .eqIfPresent(ReceiptOrderDO::getIsInvoiced, reqVO.getIsInvoiced())
                .betweenIfPresent(ReceiptOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReceiptOrderDO::getId));
    }

}
