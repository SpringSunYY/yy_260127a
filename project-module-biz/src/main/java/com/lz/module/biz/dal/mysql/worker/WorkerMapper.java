package com.lz.module.biz.dal.mysql.worker;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.worker.vo.*;

/**
 * 工人信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface WorkerMapper extends BaseMapperX<WorkerDO> {

    default PageResult<WorkerDO> selectPage(WorkerPageReqVO reqVO) {
        LambdaQueryWrapperX<WorkerDO> query = new LambdaQueryWrapperX<WorkerDO>()
                .likeIfPresent(WorkerDO::getWorkerName, reqVO.getWorkerName())
                .eqIfPresent(WorkerDO::getGender, reqVO.getGender())
                .likeIfPresent(WorkerDO::getIdCardNo, reqVO.getIdCardNo())
                .likeIfPresent(WorkerDO::getPhone, reqVO.getPhone())
                .eqIfPresent(WorkerDO::getWorkerType, reqVO.getWorkerType())
                .eqIfPresent(WorkerDO::getWorkType, reqVO.getWorkType())
                .eqIfPresent(WorkerDO::getSkillLevel, reqVO.getSkillLevel())
                .eqIfPresent(WorkerDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(WorkerDO::getDebtAmount, reqVO.getDebtAmount())
                .betweenIfPresent(WorkerDO::getPaymentAmount, reqVO.getPaymentAmount())
                .betweenIfPresent(WorkerDO::getPayableAmount, reqVO.getPayableAmount())
                .betweenIfPresent(WorkerDO::getCreateTime, reqVO.getCreateTime());
        // 动态排序（白名单字段），未传则默认按 id 倒序
        boolean asc = "asc".equalsIgnoreCase(reqVO.getOrder());
        String orderBy = reqVO.getOrderBy();
        if ("paymentAmount".equals(orderBy)) {
            query.orderBy(true, asc, WorkerDO::getPaymentAmount);
        } else if ("debtAmount".equals(orderBy)) {
            query.orderBy(true, asc, WorkerDO::getDebtAmount);
        } else if ("payableAmount".equals(orderBy)) {
            query.orderBy(true, asc, WorkerDO::getPayableAmount);
        } else {
            query.orderByDesc(WorkerDO::getCreateTime);
        }
        return selectPage(reqVO, query);
    }

}
