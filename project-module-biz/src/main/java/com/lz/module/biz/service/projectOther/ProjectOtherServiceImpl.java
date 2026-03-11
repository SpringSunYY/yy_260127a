package com.lz.module.biz.service.projectOther;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.module.biz.controller.admin.project.vo.ProjectImportExcelVO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.projectOther.vo.*;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 其他工程 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ProjectOtherServiceImpl implements ProjectOtherService {

    @Resource
    private ProjectOtherMapper projectOtherMapper;

    @Override
    public Long createProjectOther(ProjectOtherSaveReqVO createReqVO) {
        // 插入
        ProjectOtherDO projectOther = BeanUtils.toBean(createReqVO, ProjectOtherDO.class);
        projectOtherMapper.insert(projectOther);

        // 返回
        return projectOther.getId();
    }

    @Override
    public void updateProjectOther(ProjectOtherSaveReqVO updateReqVO) {
        // 校验存在
        validateProjectOtherExists(updateReqVO.getId());
        // 更新
        ProjectOtherDO updateObj = BeanUtils.toBean(updateReqVO, ProjectOtherDO.class);
        projectOtherMapper.updateById(updateObj);
    }

    @Override
    public void deleteProjectOther(Long id) {
        // 校验存在
        validateProjectOtherExists(id);
        // 删除
        projectOtherMapper.deleteById(id);
    }

    @Override
        public void deleteProjectOtherListByIds(List<Long> ids) {
        // 删除
        projectOtherMapper.deleteByIds(ids);
        }


    private void validateProjectOtherExists(Long id) {
        if (projectOtherMapper.selectById(id) == null) {
            throw exception(PROJECT_OTHER_NOT_EXISTS);
        }
    }

    @Override
    public ProjectOtherDO getProjectOther(Long id) {
        return projectOtherMapper.selectById(id);
    }

    @Override
    public PageResult<ProjectOtherDO> getProjectOtherPage(ProjectOtherPageReqVO pageReqVO) {
        return projectOtherMapper.selectPage(pageReqVO);
    }

    @Override
    public ProjectOtherImportRespVO importProjectOtherList(List<ProjectOtherImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<ProjectOtherDO> dos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //项目名称、项目类型、已结算、进度不能为空
            int index = i + 1;
            ProjectOtherImportVO vo = list.get(i);
            if (StrUtil.isEmpty(vo.getProjectName())) {
                throw new ServiceException(400,StrUtil.format("第{}行请填写项目名称", index));
            }
            if (StrUtil.isEmpty(vo.getProjectType())) {
                throw new ServiceException(400,StrUtil.format("第{}行请填写项目类型", index));
            }
            if (StrUtil.isEmpty(vo.getIsSettled())) {
                throw new ServiceException(400,StrUtil.format("第{}行请填写已结算", index));
            }
            if (StrUtil.isEmpty(vo.getProgressStatus())) {
                throw new ServiceException(400,StrUtil.format("第{}行请填写进度", index));
            }
            ProjectOtherDO projectOtherDO = BeanUtils.toBean(vo, ProjectOtherDO.class);
            dos.add(projectOtherDO);
        }
        projectOtherMapper.insertBatch(dos);
        return ProjectOtherImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个项目信息", dos.size()))
                .build();
    }

}
