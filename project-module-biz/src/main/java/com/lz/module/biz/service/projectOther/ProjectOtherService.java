package com.lz.module.biz.service.projectOther;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.projectOther.vo.*;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 其他工程 Service 接口
 *
 * @author YY
 */
public interface ProjectOtherService {

    /**
     * 创建其他工程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectOther(@Valid ProjectOtherSaveReqVO createReqVO);

    /**
     * 更新其他工程
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectOther(@Valid ProjectOtherSaveReqVO updateReqVO);

    /**
     * 删除其他工程
     *
     * @param id 编号
     */
    void deleteProjectOther(Long id);

    /**
    * 批量删除其他工程
    *
    * @param ids 编号
    */
    void deleteProjectOtherListByIds(List<Long> ids);

    /**
     * 获得其他工程
     *
     * @param id 编号
     * @return 其他工程
     */
    ProjectOtherDO getProjectOther(Long id);

    /**
     * 获得其他工程分页
     *
     * @param pageReqVO 分页查询
     * @return 其他工程分页
     */
    PageResult<ProjectOtherDO> getProjectOtherPage(ProjectOtherPageReqVO pageReqVO);

}