package com.lz.module.biz.dal.mysql.projectOther;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.projectOther.vo.*;

/**
 * 其他工程 Mapper
 *
 * @author YY
 */
@Mapper
public interface ProjectOtherMapper extends BaseMapperX<ProjectOtherDO> {

    default PageResult<ProjectOtherDO> selectPage(ProjectOtherPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProjectOtherDO>()
                .likeIfPresent(ProjectOtherDO::getProjectName, reqVO.getProjectName())
                .eqIfPresent(ProjectOtherDO::getProjectType, reqVO.getProjectType())
                .likeIfPresent(ProjectOtherDO::getProjectAddress, reqVO.getProjectAddress())
                .betweenIfPresent(ProjectOtherDO::getProjectDate, reqVO.getProjectDate())
                .eqIfPresent(ProjectOtherDO::getIsSettled, reqVO.getIsSettled())
                .eqIfPresent(ProjectOtherDO::getMaterialDesc, reqVO.getMaterialDesc())
                .eqIfPresent(ProjectOtherDO::getProgressStatus, reqVO.getProgressStatus())
                .betweenIfPresent(ProjectOtherDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProjectOtherDO::getId));
    }

}