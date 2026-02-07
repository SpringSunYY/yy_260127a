package com.lz.module.biz.service.salary;

import cn.hutool.core.util.ObjUtil;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.salary.vo.SalaryPageReqVO;
import com.lz.module.biz.controller.admin.salary.vo.SalarySaveReqVO;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.mysql.salary.SalaryMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.SALARY_NOT_EXISTS;
import static com.lz.module.biz.enums.ErrorCodeConstants.WORKER_NOT_EXISTS;

/**
 * 工资信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SalaryServiceImpl implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private WorkerMapper workerMapper;

    @Override
    public Long createSalary(SalarySaveReqVO createReqVO) {
        // 插入
        SalaryDO salary = BeanUtils.toBean(createReqVO, SalaryDO.class);
        validateWorkerExists(salary);
        //查询是否存在工人
        salaryMapper.insert(salary);

        // 返回
        return salary.getId();
    }

    private void validateWorkerExists(SalaryDO salary) {
        if (ObjUtil.isNull(salary.getWorkerId())) {
            return;
        }
        WorkerDO workerDO = workerMapper.selectById(salary.getWorkerId());
        if (ObjUtil.isNull(workerDO)) {
            throw exception(WORKER_NOT_EXISTS);
        }
        salary.setWorkerName(workerDO.getWorkerName());
    }

    @Override
    public void updateSalary(SalarySaveReqVO updateReqVO) {
        // 校验存在
        validateSalaryExists(updateReqVO.getId());
        // 更新
        SalaryDO updateObj = BeanUtils.toBean(updateReqVO, SalaryDO.class);
        validateWorkerExists(updateObj);
        salaryMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalary(Long id) {
        // 校验存在
        validateSalaryExists(id);
        // 删除
        salaryMapper.deleteById(id);
    }

    @Override
    public void deleteSalaryListByIds(List<Long> ids) {
        // 删除
        salaryMapper.deleteByIds(ids);
    }


    private void validateSalaryExists(Long id) {
        if (salaryMapper.selectById(id) == null) {
            throw exception(SALARY_NOT_EXISTS);
        }
    }

    @Override
    public SalaryDO getSalary(Long id) {
        return salaryMapper.selectById(id);
    }

    @Override
    public PageResult<SalaryDO> getSalaryPage(SalaryPageReqVO pageReqVO) {
        return salaryMapper.selectPage(pageReqVO);
    }

}
