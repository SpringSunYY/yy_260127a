package com.lz.module.biz.dal.mysql.projectVisa;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.projectVisa.ProjectVisaDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.projectVisa.vo.*;

/**
 * 项目签证 Mapper
 *
 * @author YY
 */
@Mapper
public interface ProjectVisaMapper extends BaseMapperX<ProjectVisaDO> {

    default PageResult<ProjectVisaDO> selectPage(ProjectVisaPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProjectVisaDO>()
                .eqIfPresent(ProjectVisaDO::getProjectId, reqVO.getProjectId())
                .likeIfPresent(ProjectVisaDO::getProjectNo, reqVO.getProjectNo())
                .likeIfPresent(ProjectVisaDO::getName, reqVO.getName())
                .likeIfPresent(ProjectVisaDO::getVisaName, reqVO.getVisaName())
                .betweenIfPresent(ProjectVisaDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProjectVisaDO::getId));
    }

}