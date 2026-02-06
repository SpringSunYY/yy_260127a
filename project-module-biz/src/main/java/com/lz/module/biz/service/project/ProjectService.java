package com.lz.module.biz.service.project;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.project.vo.*;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 项目信息 Service 接口
 *
 * @author YY
 */
public interface ProjectService {

    /**
     * 创建项目信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProject(@Valid ProjectSaveReqVO createReqVO);

    /**
     * 更新项目信息
     *
     * @param updateReqVO 更新信息
     */
    void updateProject(@Valid ProjectSaveReqVO updateReqVO);

    /**
     * 删除项目信息
     *
     * @param id 编号
     */
    void deleteProject(Long id);

    /**
    * 批量删除项目信息
    *
    * @param ids 编号
    */
    void deleteProjectListByIds(List<Long> ids);

    /**
     * 获得项目信息
     *
     * @param id 编号
     * @return 项目信息
     */
    ProjectDO getProject(Long id);

    /**
     * 获得项目信息分页
     *
     * @param pageReqVO 分页查询
     * @return 项目信息分页
     */
    PageResult<ProjectDO> getProjectPage(ProjectPageReqVO pageReqVO);

}