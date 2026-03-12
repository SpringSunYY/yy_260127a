package com.lz.module.biz.service.projectScattered;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.projectScattered.vo.*;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectScattered.ProjectScatteredDO;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectScattered.ProjectScatteredMapper;
import com.lz.module.biz.enums.BizProjectTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public ProjectScatteredImportRespVO importProjectScatteredList(List<ProjectScatteredImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        //收集项目信息
        Set<Long> projectIds = new HashSet<>();
        //校验数据
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectScatteredImportVO item = list.get(i);
            //项目ID、工程名称、时间、工程阶段不能为空
            if (ObjUtil.isNull(item.getProjectId())) {
                throw new ServiceException(400, StrUtil.format("第{}行项目ID不能为空", index));
            }
            if (StrUtil.isEmpty(item.getScatteredName())) {
                throw new ServiceException(400, StrUtil.format("第{}行工程名称不能为空", index));
            }
            if (StrUtil.isEmpty(item.getProjectProgress())) {
                throw new ServiceException(400, StrUtil.format("第{}行工程阶段不能为空", index));
            }
            if (ObjUtil.isNull(item.getScatteredTime())) {
                throw new ServiceException(400, StrUtil.format("第{}行时间不能为空", index));
            }
            projectIds.add(item.getProjectId());
        }
        //查询到项目信息
        LambdaQueryWrapper<ProjectDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProjectDO::getId, projectIds);
        queryWrapper.eq(ProjectDO::getProjectType, BizProjectTypeEnum.BIZ_PROJECT_TYPE_TYPE_2.getStatus());
        List<ProjectDO> projectList = projectMapper.selectList(queryWrapper);
        //如果没有查到项目信息
        if (CollUtil.isEmpty(projectList)) {
            throw new ServiceException(400, "导入数据所有的项目不存在");
        }
        //转换为map，键为项目ID，值为项目信息
        Map<Long, ProjectDO> projectMap = projectList
                .stream().collect(
                        Collectors.toMap(
                                ProjectDO::getId, item -> item)
                );
        List<ProjectScatteredDO> projectScatteredDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectScatteredImportVO item = list.get(i);
            ProjectDO projectDO = projectMap.get(item.getProjectId());
            if (ObjUtil.isNull(projectDO)) {
                throw new ServiceException(400, StrUtil.format("第{}行项目不存在，或者是整体工程，请检查", index));
            }
            ProjectScatteredDO projectScatteredDO = BeanUtils.toBean(item, ProjectScatteredDO.class);
            projectScatteredDO.setProjectName(projectDO.getName());
            projectScatteredDO.setProjectNo(projectDO.getProjectNo());
            projectScatteredDOS.add(projectScatteredDO);
        }
        projectScatteredMapper.insertBatch(projectScatteredDOS);
        return ProjectScatteredImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个零散工程信息", projectScatteredDOS.size()))
                .build();
    }

}
