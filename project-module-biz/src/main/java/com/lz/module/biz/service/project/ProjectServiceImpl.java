package com.lz.module.biz.service.project;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.project.vo.ProjectImportExcelVO;
import com.lz.module.biz.controller.admin.project.vo.ProjectImportRespVO;
import com.lz.module.biz.controller.admin.project.vo.ProjectPageReqVO;
import com.lz.module.biz.controller.admin.project.vo.ProjectSaveReqVO;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.mysql.customer.CustomerMapper;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;
import static com.lz.module.biz.enums.ErrorCodeConstants.PROJECT_NOT_EXISTS;

/**
 * 项目信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Long createProject(ProjectSaveReqVO createReqVO) {
        // 插入
        ProjectDO project = BeanUtils.toBean(createReqVO, ProjectDO.class);
        //校验服务商是否存在
        validateCustomerExists(createReqVO.getCustomerId());
        projectMapper.insert(project);

        // 返回
        return project.getId();
    }

    private void validateCustomerExists(Long customerId) {
        if (ObjUtil.isNull(customerId)) {
            return;
        }
        CustomerDO customer = customerMapper.selectById(customerId);
        if (ObjUtil.isNull(customer)) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
    }

    @Override
    public void updateProject(ProjectSaveReqVO updateReqVO) {
        // 校验存在
        validateProjectExists(updateReqVO.getId());
        // 更新
        ProjectDO updateObj = BeanUtils.toBean(updateReqVO, ProjectDO.class);
        validateCustomerExists(updateReqVO.getCustomerId());
        projectMapper.updateById(updateObj);
    }

    @Override
    public void deleteProject(Long id) {
        // 校验存在
        validateProjectExists(id);
        // 删除
        projectMapper.deleteById(id);
    }

    @Override
    public void deleteProjectListByIds(List<Long> ids) {
        // 删除
        projectMapper.deleteByIds(ids);
    }


    private void validateProjectExists(Long id) {
        if (projectMapper.selectById(id) == null) {
            throw exception(PROJECT_NOT_EXISTS);
        }
    }

    @Override
    public ProjectDO getProject(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public PageResult<ProjectDO> getProjectPage(ProjectPageReqVO pageReqVO) {
        return projectMapper.selectPage(pageReqVO);
    }

    @Override
    public ProjectImportRespVO importProjectList(List<ProjectImportExcelVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        //收集服务商信息
        Set<Long> customerIds = new HashSet<>();
        //初步校验数据
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectImportExcelVO excelVO = list.get(i);
            //项目编号、项目名称、项目类型、工程类型、属于PMC、工程阶段不能为空
            if (StrUtil.isEmpty(excelVO.getProjectNo())) {
                throw new ServiceException(400, StrUtil.format("第{}行项目编号不能为空", index));
            }
            if (StrUtil.isEmpty(excelVO.getName())) {
                throw new ServiceException(400, StrUtil.format("第{}行项目名称不能为空", index));
            }
            if (StrUtil.isEmpty(excelVO.getProjectType())) {
                throw new ServiceException(400, StrUtil.format("第{}行项目类型不能为空", index));
            }
            if (StrUtil.isEmpty(excelVO.getEngineeringType())) {
                throw new ServiceException(400, StrUtil.format("第{}行工程类型不能为空", index));
            }
            if (StrUtil.isEmpty(excelVO.getIsPmc())) {
                throw new ServiceException(400, StrUtil.format("第{}行属于PMC不能为空", index));
            }
            if (StrUtil.isEmpty(excelVO.getProjectProgress())) {
                throw new ServiceException(400, StrUtil.format("第{}行工程阶段不能为空", index));
            }
            if (ObjUtil.isNotNull(excelVO.getCustomerId())) {
                customerIds.add(excelVO.getCustomerId());
            }
        }
        //查询到服务商信息
        Map<Long, CustomerDO> customerDOMap = new HashMap<>();
        if (CollUtil.isNotEmpty(customerIds)) {
            List<CustomerDO> customerList = customerMapper.selectList(new LambdaQueryWrapper<CustomerDO>()
                    .in(CustomerDO::getId, customerIds));
            customerDOMap = customerList.stream().collect(Collectors.toMap(CustomerDO::getId, v -> v));
        }
        List<ProjectDO> projectDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            ProjectImportExcelVO excelVO = list.get(i);
            //如果存在服务商信息，则设置
            if (ObjUtil.isNotNull(excelVO.getCustomerId())) {
                CustomerDO customerDO = customerDOMap.get(excelVO.getCustomerId());
                if (ObjUtil.isNull(customerDO)) {
                    throw new ServiceException(400, StrUtil.format("第{}行设置了服务商，但是服务商不存在", index));
                }
                excelVO.setCustomerName(customerDO.getName());
            }
            ProjectDO projectDO = BeanUtils.toBean(excelVO, ProjectDO.class);
            projectDOS.add(projectDO);
        }
        projectMapper.insertBatch(projectDOS);
        return ProjectImportRespVO.builder().message(StrUtil.format("成功导入 {} 个项目信息", projectDOS.size())).build();
    }

}
