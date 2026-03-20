package com.lz.module.biz.dal.mysql.supplier;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.supplier.vo.*;

/**
 * 供应商信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface SupplierMapper extends BaseMapperX<SupplierDO> {

    default PageResult<SupplierDO> selectPage(SupplierPageReqVO reqVO) {
        LambdaQueryWrapperX<SupplierDO> query = new LambdaQueryWrapperX<SupplierDO>()
                .likeIfPresent(SupplierDO::getName, reqVO.getName())
                .likeIfPresent(SupplierDO::getTelephone, reqVO.getTelephone())
                .likeIfPresent(SupplierDO::getQq, reqVO.getQq())
                .likeIfPresent(SupplierDO::getWeChat, reqVO.getWeChat())
                .likeIfPresent(SupplierDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(SupplierDO::getDebtAmount, reqVO.getDebtAmount())
                .betweenIfPresent(SupplierDO::getPaymentAmount, reqVO.getPaymentAmount())
                .betweenIfPresent(SupplierDO::getPayableAmount, reqVO.getPayableAmount())
                .betweenIfPresent(SupplierDO::getCreateTime, reqVO.getCreateTime());

        // 动态排序（白名单字段），未传则默认按 id 倒序
        boolean asc = "asc".equalsIgnoreCase(reqVO.getOrder());
        String orderBy = reqVO.getOrderBy();
        if ("paymentAmount".equals(orderBy)) {
            query.orderBy(true, asc, SupplierDO::getPaymentAmount);
        } else if ("debtAmount".equals(orderBy)) {
            query.orderBy(true, asc, SupplierDO::getDebtAmount);
        } else if ("payableAmount".equals(orderBy)) {
            query.orderBy(true, asc, SupplierDO::getPayableAmount);
        } else {
            query.orderByDesc(SupplierDO::getCreateTime);
        }

        return selectPage(reqVO, query);
    }

}
