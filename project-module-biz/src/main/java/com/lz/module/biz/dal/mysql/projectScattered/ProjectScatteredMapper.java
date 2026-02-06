package com.lz.module.biz.dal.mysql.projectScattered;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.projectScattered.ProjectScatteredDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.projectScattered.vo.*;

/**
 * 零散工程 Mapper
 *
 * @author YY
 */
@Mapper
public interface ProjectScatteredMapper extends BaseMapperX<ProjectScatteredDO> {

    default PageResult<ProjectScatteredDO> selectPage(ProjectScatteredPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProjectScatteredDO>()
                .eqIfPresent(ProjectScatteredDO::getProjectId, reqVO.getProjectId())
                .likeIfPresent(ProjectScatteredDO::getProjectNo, reqVO.getProjectNo())
                .likeIfPresent(ProjectScatteredDO::getProjectName, reqVO.getProjectName())
                .likeIfPresent(ProjectScatteredDO::getScatteredName, reqVO.getScatteredName())
                .betweenIfPresent(ProjectScatteredDO::getScatteredTime, reqVO.getScatteredTime())
                .eqIfPresent(ProjectScatteredDO::getProjectProgress, reqVO.getProjectProgress())
                .eqIfPresent(ProjectScatteredDO::getCompletedImage, reqVO.getCompletedImage())
                .eqIfPresent(ProjectScatteredDO::getVerification, reqVO.getVerification())
                .betweenIfPresent(ProjectScatteredDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProjectScatteredDO::getId));
    }

}