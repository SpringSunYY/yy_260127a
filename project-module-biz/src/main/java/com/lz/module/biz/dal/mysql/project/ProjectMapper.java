package com.lz.module.biz.dal.mysql.project;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.module.biz.controller.admin.project.vo.ProjectPageReqVO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

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
                .betweenIfPresent(ProjectDO::getCompletedTime, reqVO.getCompletedTime())
                .eqIfPresent(ProjectDO::getFiscalYear, reqVO.getFiscalYear())
                .eqIfPresent(ProjectDO::getProjectProgress, reqVO.getProjectProgress())
                .eqIfPresent(ProjectDO::getIsCompletedFile, reqVO.getIsCompletedFile())
                .eqIfPresent(ProjectDO::getIsVerification, reqVO.getIsVerification())
                .eqIfPresent(ProjectDO::getIsCompleted, reqVO.getIsCompleted())
                .eqIfPresent(ProjectDO::getIsDeterminedQuantity, reqVO.getIsDeterminedQuantity())
                .eqIfPresent(ProjectDO::getIsMaterialVerification, reqVO.getIsMaterialVerification())
                .eqIfPresent(ProjectDO::getIsSettlementFile, reqVO.getIsSettlementFile())
                .betweenIfPresent(ProjectDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(ProjectDO::getRemark, reqVO.getRemark())
                .orderByDesc(ProjectDO::getId));
    }

}
