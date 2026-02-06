package com.lz.module.biz.service.projectScattered;

import cn.hutool.core.util.ObjUtil;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.projectScattered.vo.ProjectScatteredPageReqVO;
import com.lz.module.biz.controller.admin.projectScattered.vo.ProjectScatteredSaveReqVO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectScattered.ProjectScatteredDO;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectScattered.ProjectScatteredMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.PROJECT_NOT_EXISTS;
import static com.lz.module.biz.enums.ErrorCodeConstants.PROJECT_SCATTERED_NOT_EXISTS;

/**
 * 零散工程 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ProjectScatteredServiceImpl implements ProjectScatteredService {

    @Resource
    private ProjectScatteredMapper projectScatteredMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public Long createProjectScattered(ProjectScatteredSaveReqVO createReqVO) {
        // 插入
        ProjectScatteredDO projectScattered = BeanUtils.toBean(createReqVO, ProjectScatteredDO.class);
        initScattered(projectScattered);
        projectScatteredMapper.insert(projectScattered);

        // 返回
        return projectScattered.getId();
    }

    private void initScattered(ProjectScatteredDO projectScattered) {
        //首先查询项目是否存在
        ProjectDO projectDO = projectMapper.selectById(projectScattered.getProjectId());
        if (ObjUtil.isNull(projectDO)) {
            throw exception(PROJECT_NOT_EXISTS);
        }
        projectScattered.setProjectName(projectDO.getName());
        projectScattered.setProjectNo(projectDO.getProjectNo());
    }

    @Override
    public void updateProjectScattered(ProjectScatteredSaveReqVO updateReqVO) {
        // 校验存在
        validateProjectScatteredExists(updateReqVO.getId());
        // 更新
        ProjectScatteredDO updateObj = BeanUtils.toBean(updateReqVO, ProjectScatteredDO.class);
        initScattered(updateObj);
        projectScatteredMapper.updateById(updateObj);
    }

    @Override
    public void deleteProjectScattered(Long id) {
        // 校验存在
        validateProjectScatteredExists(id);
        // 删除
        projectScatteredMapper.deleteById(id);
    }

    @Override
    public void deleteProjectScatteredListByIds(List<Long> ids) {
        // 删除
        projectScatteredMapper.deleteByIds(ids);
    }


    private void validateProjectScatteredExists(Long id) {
        if (projectScatteredMapper.selectById(id) == null) {
            throw exception(PROJECT_SCATTERED_NOT_EXISTS);
        }
    }

    @Override
    public ProjectScatteredDO getProjectScattered(Long id) {
        return projectScatteredMapper.selectById(id);
    }

    @Override
    public PageResult<ProjectScatteredDO> getProjectScatteredPage(ProjectScatteredPageReqVO pageReqVO) {
        return projectScatteredMapper.selectPage(pageReqVO);
    }

}
