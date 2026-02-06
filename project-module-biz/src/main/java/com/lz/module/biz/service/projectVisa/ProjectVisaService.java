package com.lz.module.biz.service.projectVisa;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.projectVisa.vo.*;
import com.lz.module.biz.dal.dataobject.projectVisa.ProjectVisaDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 项目签证 Service 接口
 *
 * @author YY
 */
public interface ProjectVisaService {

    /**
     * 创建项目签证
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectVisa(@Valid ProjectVisaSaveReqVO createReqVO);

    /**
     * 更新项目签证
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectVisa(@Valid ProjectVisaSaveReqVO updateReqVO);

    /**
     * 删除项目签证
     *
     * @param id 编号
     */
    void deleteProjectVisa(Long id);

    /**
    * 批量删除项目签证
    *
    * @param ids 编号
    */
    void deleteProjectVisaListByIds(List<Long> ids);

    /**
     * 获得项目签证
     *
     * @param id 编号
     * @return 项目签证
     */
    ProjectVisaDO getProjectVisa(Long id);

    /**
     * 获得项目签证分页
     *
     * @param pageReqVO 分页查询
     * @return 项目签证分页
     */
    PageResult<ProjectVisaDO> getProjectVisaPage(ProjectVisaPageReqVO pageReqVO);

}