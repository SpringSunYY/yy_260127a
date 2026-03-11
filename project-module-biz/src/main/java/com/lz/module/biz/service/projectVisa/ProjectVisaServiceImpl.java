package com.lz.module.biz.service.projectVisa;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.projectVisa.vo.ProjectVisaImportExcelVO;
import com.lz.module.biz.controller.admin.projectVisa.vo.ProjectVisaImportRespVO;
import com.lz.module.biz.controller.admin.projectVisa.vo.ProjectVisaPageReqVO;
import com.lz.module.biz.controller.admin.projectVisa.vo.ProjectVisaSaveReqVO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectVisa.ProjectVisaDO;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectVisa.ProjectVisaMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.PROJECT_NOT_EXISTS;
import static com.lz.module.biz.enums.ErrorCodeConstants.PROJECT_VISA_NOT_EXISTS;

/**
 * 项目签证 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ProjectVisaServiceImpl implements ProjectVisaService {

    @Resource
    private ProjectVisaMapper projectVisaMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public Long createProjectVisa(ProjectVisaSaveReqVO createReqVO) {
        // 插入
        ProjectVisaDO projectVisa = BeanUtils.toBean(createReqVO, ProjectVisaDO.class);
        //初始化数据
        ProjectDO projectDO = initProjectVisa(projectVisa);
        //查询项目有多少个签证
        Long count = projectVisaMapper.selectCount(ProjectVisaDO::getProjectId, projectVisa.getProjectId());
        projectDO.setVisaNum((int) (count + 1));

        transactionTemplate.executeWithoutResult(status -> {
            projectMapper.updateById(projectDO);
            projectVisaMapper.insert(projectVisa);
        });
        // 返回
        return projectVisa.getId();
    }

    private ProjectDO initProjectVisa(ProjectVisaDO projectVisa) {
        //首先查询项目是否存在
        ProjectDO project = projectMapper.selectById(projectVisa.getProjectId());
        if (ObjUtil.isNull(project)) {
            throw exception(PROJECT_NOT_EXISTS);
        }
        projectVisa.setProjectNo(project.getProjectNo());
        projectVisa.setProjectName(project.getName());
        return project;
    }

    @Override
    public void updateProjectVisa(ProjectVisaSaveReqVO updateReqVO) {
        // 校验存在
        validateProjectVisaExists(updateReqVO.getId());
        // 更新
        ProjectVisaDO updateObj = BeanUtils.toBean(updateReqVO, ProjectVisaDO.class);
        initProjectVisa(updateObj);
        projectVisaMapper.updateById(updateObj);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProjectVisa(Long id) {
        // 校验存在
        ProjectVisaDO projectVisaDO = validateProjectVisaExists(id);
        // 删除
        projectVisaMapper.deleteById(id);
        //统计
        ProjectDO projectDO = projectMapper.selectById(projectVisaDO.getProjectId());
        if (ObjUtil.isNotNull(projectDO)) {
            projectDO.setVisaNum(projectDO.getVisaNum() - 1);
            projectMapper.updateById(projectDO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProjectVisaListByIds(List<Long> ids) {
        // 删除
        ids.forEach(this::deleteProjectVisa);
    }


    private ProjectVisaDO validateProjectVisaExists(Long id) {
        ProjectVisaDO projectVisaDO = projectVisaMapper.selectById(id);
        if (projectVisaDO == null) {
            throw exception(PROJECT_VISA_NOT_EXISTS);
        }
        return projectVisaDO;
    }

    @Override
    public ProjectVisaDO getProjectVisa(Long id) {
        return projectVisaMapper.selectById(id);
    }

    @Override
    public PageResult<ProjectVisaDO> getProjectVisaPage(ProjectVisaPageReqVO pageReqVO) {
        return projectVisaMapper.selectPage(pageReqVO);
    }

    @Override
    public ProjectVisaImportRespVO importProjectVisaList(List<ProjectVisaImportExcelVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        //收集项目信息
        Set<Long> projectIds = new HashSet<>();
        //校验数据
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectVisaImportExcelVO item = list.get(i);
            //项目ID、签证名称、工程名称、金额不能为空
            if (ObjUtil.isNull(item.getProjectId())) {
                throw new ServiceException(400, StrUtil.format("第{}行项目ID不能为空", index));
            }
            if (StrUtil.isEmpty(item.getVisaName())) {
                throw new ServiceException(400, StrUtil.format("第{}行签证名称不能为空", index));
            }
            if (StrUtil.isEmpty(item.getEngineeringName())) {
                throw new ServiceException(400, StrUtil.format("第{}行工程名称不能为空", index));
            }
            if (ObjUtil.isNull(item.getAmount())) {
                throw new ServiceException(400, StrUtil.format("第{}行金额不能为空", index));
            }
            projectIds.add(item.getProjectId());
        }
        //查询到项目信息
        List<ProjectDO> projectList = projectMapper.selectList(ProjectDO::getId, projectIds);
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
        ArrayList<ProjectVisaDO> projectVisaDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectVisaImportExcelVO item = list.get(i);
            ProjectDO project = projectMap.get(item.getProjectId());
            if (ObjUtil.isNull(project)) {
                throw new ServiceException(400, StrUtil.format("第{}行数据不能为空", index));
            }
            ProjectVisaDO projectVisaDO = BeanUtils.toBean(item, ProjectVisaDO.class);
            projectVisaDO.setProjectId(project.getId());
            projectVisaDO.setProjectNo(project.getProjectNo());
            projectVisaDO.setProjectName(project.getName());
            projectVisaDOS.add(projectVisaDO);
            //为该个项目添加一个签证数
            project.setVisaNum(project.getVisaNum() + 1);
        }
        transactionTemplate.executeWithoutResult(status -> {
            projectMapper.updateBatch(projectList);
            projectVisaMapper.insertBatch(projectVisaDOS);
        });
        return ProjectVisaImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个项目签证信息", projectVisaDOS.size()))
                .build();
    }

}
