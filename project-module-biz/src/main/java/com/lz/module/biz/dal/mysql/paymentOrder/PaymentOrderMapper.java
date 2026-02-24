package com.lz.module.biz.dal.mysql.paymentOrder;

import java.math.BigDecimal;
import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.paymentOrder.vo.*;

/**
 * 付款信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface PaymentOrderMapper extends BaseMapperX<PaymentOrderDO> {

    default PageResult<PaymentOrderDO> selectPage(PaymentOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PaymentOrderDO>()
                .eqIfPresent(PaymentOrderDO::getPaymentNo, reqVO.getPaymentNo())
                .eqIfPresent(PaymentOrderDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PaymentOrderDO::getProjectType, reqVO.getProjectType())
                .likeIfPresent(PaymentOrderDO::getProjectName, reqVO.getProjectName())
                .eqIfPresent(PaymentOrderDO::getPayeeType, reqVO.getPayeeType())
                .eqIfPresent(PaymentOrderDO::getPayeeId, reqVO.getPayeeId())
                .likeIfPresent(PaymentOrderDO::getPayeeName, reqVO.getPayeeName())
                .betweenIfPresent(PaymentOrderDO::getPaymentTime, reqVO.getPaymentTime())
                .eqIfPresent(PaymentOrderDO::getPaymentMethod, reqVO.getPaymentMethod())
                .eqIfPresent(PaymentOrderDO::getIsInvoiced, reqVO.getIsInvoiced())
                .betweenIfPresent(PaymentOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PaymentOrderDO::getId));
    }

    BigDecimal getPaymentOrderAmount(PaymentOrderPageReqVO pageReqVO);
}
