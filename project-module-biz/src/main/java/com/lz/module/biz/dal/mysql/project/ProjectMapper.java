package com.lz.module.biz.dal.mysql.project;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.project.vo.*;

/**
 * 项目信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface ProjectMapper extends BaseMapperX<ProjectDO> {

    default PageResult<ProjectDO> selectPage(ProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProjectDO>()
                .likeIfPresent(ProjectDO::getProjectNo, reqVO.getProjectNo())
                .likeIfPresent(ProjectDO::getName, reqVO.getName())
                .eqIfPresent(ProjectDO::getProjectType, reqVO.getProjectType())
                .likeIfPresent(ProjectDO::getContractNumber, reqVO.getContractNumber())
                .eqIfPresent(ProjectDO::getEngineeringType, reqVO.getEngineeringType())
                .eqIfPresent(ProjectDO::getIsPmc, reqVO.getIsPmc())
                .likeIfPresent(ProjectDO::getCustomerName, reqVO.getCustomerName())
                .betweenIfPresent(ProjectDO::getDeliverTime, reqVO.getDeliverTime())
                .eqIfPresent(ProjectDO::getFiscalYear, reqVO.getFiscalYear())
                .eqIfPresent(ProjectDO::getProjectProgress, reqVO.getProjectProgress())
                .eqIfPresent(ProjectDO::getIsCompleted, reqVO.getIsCompleted())
                .eqIfPresent(ProjectDO::getVerification, reqVO.getVerification())
                .eqIfPresent(ProjectDO::getDeterminedQuantity, reqVO.getDeterminedQuantity())
                .eqIfPresent(ProjectDO::getMaterialVerification, reqVO.getMaterialVerification())
                .betweenIfPresent(ProjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProjectDO::getId));
    }

}