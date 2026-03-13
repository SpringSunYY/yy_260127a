package com.lz.module.biz.dal.mysql.salaryPaymentOrder;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.salaryPaymentOrder.SalaryPaymentOrderDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.*;

/**
 * 工资付款信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface SalaryPaymentOrderMapper extends BaseMapperX<SalaryPaymentOrderDO> {

    default PageResult<SalaryPaymentOrderDO> selectPage(SalaryPaymentOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SalaryPaymentOrderDO>()
                .likeIfPresent(SalaryPaymentOrderDO::getPaymentNo, reqVO.getPaymentNo())
                .eqIfPresent(SalaryPaymentOrderDO::getSalaryId, reqVO.getSalaryId())
                .likeIfPresent(SalaryPaymentOrderDO::getSalaryName, reqVO.getSalaryName())
                .eqIfPresent(SalaryPaymentOrderDO::getWorkerId, reqVO.getWorkerId())
                .likeIfPresent(SalaryPaymentOrderDO::getWorkerName, reqVO.getWorkerName())
                .betweenIfPresent(SalaryPaymentOrderDO::getPaymentTime, reqVO.getPaymentTime())
                .eqIfPresent(SalaryPaymentOrderDO::getPaymentMethod, reqVO.getPaymentMethod())
                .eqIfPresent(SalaryPaymentOrderDO::getIsInvoiced, reqVO.getIsInvoiced())
                .betweenIfPresent(SalaryPaymentOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SalaryPaymentOrderDO::getId));
    }

}