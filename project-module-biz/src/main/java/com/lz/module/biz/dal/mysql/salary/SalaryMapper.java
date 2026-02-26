package com.lz.module.biz.dal.mysql.salary;

import java.math.BigDecimal;
import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.salary.vo.*;

/**
 * 工资信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface SalaryMapper extends BaseMapperX<SalaryDO> {

    default PageResult<SalaryDO> selectPage(SalaryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SalaryDO>()
                .likeIfPresent(SalaryDO::getWorkerName, reqVO.getWorkerName())
                .eqIfPresent(SalaryDO::getIsSettlement, reqVO.getIsSettlement())
                .betweenIfPresent(SalaryDO::getSettlementTime, reqVO.getSettlementTime())
                .betweenIfPresent(SalaryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SalaryDO::getId));
    }

    BigDecimal getTotalPayableAmount(SalaryPageReqVO pageReqVO);
}
