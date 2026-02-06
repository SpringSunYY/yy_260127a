package com.lz.module.biz.service.projectScattered;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.projectScattered.vo.*;
import com.lz.module.biz.dal.dataobject.projectScattered.ProjectScatteredDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 零散工程 Service 接口
 *
 * @author YY
 */
public interface ProjectScatteredService {

    /**
     * 创建零散工程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectScattered(@Valid ProjectScatteredSaveReqVO createReqVO);

    /**
     * 更新零散工程
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectScattered(@Valid ProjectScatteredSaveReqVO updateReqVO);

    /**
     * 删除零散工程
     *
     * @param id 编号
     */
    void deleteProjectScattered(Long id);

    /**
    * 批量删除零散工程
    *
    * @param ids 编号
    */
    void deleteProjectScatteredListByIds(List<Long> ids);

    /**
     * 获得零散工程
     *
     * @param id 编号
     * @return 零散工程
     */
    ProjectScatteredDO getProjectScattered(Long id);

    /**
     * 获得零散工程分页
     *
     * @param pageReqVO 分页查询
     * @return 零散工程分页
     */
    PageResult<ProjectScatteredDO> getProjectScatteredPage(ProjectScatteredPageReqVO pageReqVO);

}