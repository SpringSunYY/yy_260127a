package com.lz.module.biz.dal.mysql.salary;

import java.math.BigDecimal;
import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import io.swagger.v3.oas.models.OpenAPI;
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
                .likeIfPresent(SalaryDO::getName, reqVO.getName())
                .likeIfPresent(SalaryDO::getWorkerName, reqVO.getWorkerName())
                .eqIfPresent(SalaryDO::getIsSettlement, reqVO.getIsSettlement())
                .betweenIfPresent(SalaryDO::getSalaryCycleTime, reqVO.getSalaryCycleTime())
                .betweenIfPresent(SalaryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SalaryDO::getCreateTime));
    }

    BigDecimal getTotalPayableAmount(SalaryPageReqVO pageReqVO);
}
