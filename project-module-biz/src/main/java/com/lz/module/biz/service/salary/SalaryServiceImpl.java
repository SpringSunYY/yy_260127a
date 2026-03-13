package com.lz.module.biz.service.salary;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.module.biz.controller.admin.salary.vo.SalaryImportExcelVO;
import com.lz.module.biz.controller.admin.salary.vo.SalaryImportRespVO;
import com.lz.module.biz.controller.admin.salary.vo.SalaryPageReqVO;
import com.lz.module.biz.controller.admin.salary.vo.SalarySaveReqVO;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.mysql.salary.SalaryMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import com.lz.module.biz.service.worker.WorkerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 工资信息 Service 实现类
 *
 * @author YY
 */
@Slf4j
@Service
@Validated
public class SalaryServiceImpl implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private WorkerMapper workerMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private WorkerService workerService;

    @Override
    public Long createSalary(SalarySaveReqVO createReqVO) {
        // 插入
        SalaryDO salary = BeanUtils.toBean(createReqVO, SalaryDO.class);
        //查询是否存在工人
        WorkerDO workerDO = validateWorkerExists(salary);
        workerDO.setPayableAmount(workerDO.getDebtAmount().add(salary.getPayableAmount()));

        salary.setIsSettlement(CommonWhetherEnum.COMMON_WHETHER_2.getStatus());
        transactionTemplate.executeWithoutResult(status -> {
            salaryMapper.insert(salary);
            workerService.updateWorkerAmount(workerDO);
        });
        // 返回
        return salary.getId();
    }

    private WorkerDO validateWorkerExists(SalaryDO salary) {
        WorkerDO workerDO = workerMapper.selectById(salary.getWorkerId());
        if (ObjUtil.isNull(workerDO)) {
            throw exception(WORKER_NOT_EXISTS);
        }
        salary.setWorkerName(workerDO.getWorkerName());
        return workerDO;
    }

    @Override
    @Transactional
    public void updateSalary(SalarySaveReqVO updateReqVO) {
        // 校验存在
        SalaryDO salaryDO = validateSalaryExists(updateReqVO.getId());
        // 更新
        SalaryDO updateObj = BeanUtils.toBean(updateReqVO, SalaryDO.class);
        //如果工人和数据库不一致
        if (!salaryDO.getWorkerId().equals(updateObj.getWorkerId())) {
            throw exception(SALARY_WORKER_CANNOT_UPDATE);
        }
        WorkerDO workerDO = validateWorkerExists(updateObj);
        if (ObjUtil.isNotNull(workerDO)&&workerDO.getDebtAmount().compareTo(salaryDO.getPayableAmount())!=0) {
            BigDecimal currentAmount = updateReqVO.getPayableAmount().subtract(salaryDO.getPayableAmount());
            workerDO.setPayableAmount(workerDO.getDebtAmount().add(currentAmount));
            workerService.updateWorkerAmount(workerDO);
        }
        salaryMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void deleteSalary(Long id) {
        // 校验存在
        SalaryDO salaryDO = validateSalaryExists(id);
        WorkerDO workerDO = workerMapper.selectById(salaryDO.getWorkerId());
        if (ObjUtil.isNotNull(workerDO)) {
            workerDO.setPayableAmount(workerDO.getDebtAmount().subtract(salaryDO.getPayableAmount()));
            workerService.updateWorkerAmount(workerDO);
        }
        // 删除
        salaryMapper.deleteById(id);
    }

    @Override
    public void deleteSalaryListByIds(List<Long> ids) {
        //查到所有的工资信息
        LambdaQueryWrapperX<SalaryDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.in(SalaryDO::getId, ids);
        List<SalaryDO> salaryDOList = salaryMapper.selectList(queryWrapper);
        if (ArrayUtil.isEmpty(salaryDOList)) {
            return;
        }
        //拿到所有的工人Id信息
        List<Long> workerIds = salaryDOList.stream().map(SalaryDO::getWorkerId).collect(Collectors.toList());
        LambdaQueryWrapperX<WorkerDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.in(WorkerDO::getId, workerIds);
        List<WorkerDO> workerDOS = workerMapper.selectList(wrapper);
        Map<Long, WorkerDO> workerDOMap=new HashMap<>();
        if (ArrayUtil.isNotEmpty(workerDOS)) {
            //转换为map
             workerDOMap = workerDOS.stream().collect(Collectors.toMap(WorkerDO::getId, workerDO -> workerDO));
            for (SalaryDO salaryDO : salaryDOList) {
                WorkerDO workerDO = workerDOMap.get(salaryDO.getWorkerId());
                if (ObjUtil.isNull(workerDO)) continue;
                workerDO.setPayableAmount(workerDO.getDebtAmount().subtract(salaryDO.getPayableAmount()));
            }
        }
        Map<Long, WorkerDO> finalWorkerDOMap = workerDOMap;
        transactionTemplate.executeWithoutResult(status -> {
            salaryMapper.deleteByIds(ids);
            finalWorkerDOMap.forEach((id, workerDO) -> workerService.updateWorkerAmount(workerDO));
        });
    }


    private SalaryDO validateSalaryExists(Long id) {
        SalaryDO salaryDO = salaryMapper.selectById(id);
        if (salaryDO == null) {
            throw exception(SALARY_NOT_EXISTS);
        }
        return salaryDO;
    }

    @Override
    public SalaryDO getSalary(Long id) {
        return salaryMapper.selectById(id);
    }

    @Override
    public PageResult<SalaryDO> getSalaryPage(SalaryPageReqVO pageReqVO) {
        return salaryMapper.selectPage(pageReqVO);
    }

    @Override
    public BigDecimal getTotalPayableAmount(SalaryPageReqVO pageReqVO) {
        return salaryMapper.getTotalPayableAmount(pageReqVO);
    }

    @Override
    public SalaryImportRespVO importSalaryList(List<SalaryImportExcelVO> list) {
        if (ArrayUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        //校验数据
        for (int i = 0; i < list.size(); i++) {
            SalaryImportExcelVO vo = list.get(i);
            int index = i + 1;
            if (ObjUtil.isNull(vo.getWorkerId())) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行工人编号不能为空", index));
            }
            if (ObjUtil.isNull(vo.getSalaryCycleTime())) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行请填写工资周期", index));
            }
            if (ObjUtil.isNull(vo.getPayableAmount())) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行请填写应发款项", index));
            }
        }
        //遍历去重所有的工人编号，查询出所有的工人，防止没有这个工人
        List<Long> workerIds = list.stream().map(SalaryImportExcelVO::getWorkerId).filter(Objects::nonNull).distinct().toList();
        List<WorkerDO> workerDOList = workerMapper.selectList(new LambdaQueryWrapperX<WorkerDO>()
                .in(WorkerDO::getId, workerIds));
        //因为把所有的工人编号为key的map
        Map<Long, WorkerDO> workerDOMap = workerDOList.stream().collect(Collectors.toMap(WorkerDO::getId, v -> v));
        //遍历列表，从map里面获取对应的工人信息
        for (int i = 0; i < workerIds.size(); i++) {
            Long id = workerIds.get(i);
            WorkerDO workerDO = workerDOMap.get(id);
            if (ObjUtil.isNull(workerDO)) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行导入失败，不存在编号: {} 的工人", i + 1, id));
            }
        }

        ArrayList<SalaryDO> salaryDOS = new ArrayList<>();
        for (SalaryImportExcelVO vo : list) {
            if (ObjUtil.isNotNull(vo.getWorkerId())) {
                vo.setWorkerName(workerDOMap.get(vo.getWorkerId()).getWorkerName());
            }
            SalaryDO salaryDO = BeanUtils.toBean(vo, SalaryDO.class);
            salaryDOS.add(salaryDO);
        }
        salaryMapper.insertBatch(salaryDOS);
        return SalaryImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个工资信息", salaryDOS.size()))
                .build();
    }

    private PaymentOrderDO getPaymentOrderDO(String paymentNo, Long workerId, String workerName,
                                             LocalDateTime settlementTime, BigDecimal payableAmount,
                                             String paymentMethod, String isInvoiced, String paymentCertificate, String remark) {
        PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
        paymentOrderDO.setPaymentNo(paymentNo);
        paymentOrderDO.setPaymentTime(settlementTime);
        paymentOrderDO.setPaymentAmount(payableAmount);
        paymentOrderDO.setPaymentMethod(paymentMethod);
        paymentOrderDO.setIsInvoiced(isInvoiced);
        paymentOrderDO.setPaymentCertificate(paymentCertificate);
        paymentOrderDO.setRemark(remark);
        return paymentOrderDO;
    }

}
