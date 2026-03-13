package com.lz.module.biz.service.salaryPaymentOrder;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.id.IdUtils;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.SalaryPaymentOrderPageReqVO;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.SalaryPaymentOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.module.biz.dal.dataobject.salaryPaymentOrder.SalaryPaymentOrderDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.mysql.salary.SalaryMapper;
import com.lz.module.biz.dal.mysql.salaryPaymentOrder.SalaryPaymentOrderMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import com.lz.module.biz.service.worker.WorkerService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 工资付款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class SalaryPaymentOrderServiceImpl implements SalaryPaymentOrderService {

    @Resource
    private SalaryPaymentOrderMapper salaryPaymentOrderMapper;

    @Resource
    private WorkerService workerService;

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private WorkerMapper workerMapper;

    @Override
    public Long createSalaryPaymentOrder(SalaryPaymentOrderSaveReqVO createReqVO) {
        // 插入
        SalaryPaymentOrderDO salaryPaymentOrder = BeanUtils.toBean(createReqVO, SalaryPaymentOrderDO.class);
        salaryPaymentOrder.setPaymentNo(IdUtils.generateTimeRandomId());
        //判断工人是否存在
        WorkerDO workerDO = judgeWorker(salaryPaymentOrder);
        workerDO.setPaymentAmount(workerDO.getPaymentAmount().add(salaryPaymentOrder.getPaymentAmount()));
        //判断是否有月薪
        SalaryDO salaryDO = judgeSalary(salaryPaymentOrder);
        transactionTemplate.executeWithoutResult(status -> {
            salaryPaymentOrderMapper.insert(salaryPaymentOrder);
            workerService.updateWorkerAmount(workerDO);
            if (ObjUtil.isNotNull(salaryDO)) {
                salaryMapper.updateById(salaryDO);
            }
        });

        // 返回
        return salaryPaymentOrder.getId();
    }

    private SalaryDO judgeSalary(SalaryPaymentOrderDO orderDO) {
        if (ObjUtil.isNull(orderDO.getSalaryId())) {
            return null;
        }
        SalaryDO salaryDO = salaryMapper.selectById(orderDO.getSalaryId());
        if (ObjUtil.isNull(salaryDO)) {
            throw exception(SALARY_NOT_EXISTS);
        }
        if (salaryDO.getIsSettlement().equals(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())) {
            throw exception(SALARY_PAYMENT_ORDER_SALARY_PAYMENT);
        }
        if (!salaryDO.getWorkerId().equals(orderDO.getWorkerId())) {
            throw exception(SALARY_PAYMENT_ORDER_WORKER_NOT_EQUAL);
        }
        salaryDO.setIsSettlement(CommonWhetherEnum.COMMON_WHETHER_1.getStatus());
        return salaryDO;
    }

    private WorkerDO judgeWorker(SalaryPaymentOrderDO orderDO) {
        WorkerDO workerDO = workerService.getWorker(orderDO.getWorkerId());
        if (workerDO == null) {
            throw exception(WORKER_NOT_EXISTS);
        }
        orderDO.setWorkerName(workerDO.getWorkerName());
        return workerDO;
    }

    @Override
    @Transactional
    public void updateSalaryPaymentOrder(SalaryPaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        SalaryPaymentOrderDO salaryPaymentOrderDO = validateSalaryPaymentOrderExists(updateReqVO.getId());
        // 更新
        SalaryPaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, SalaryPaymentOrderDO.class);
        //如果工人信息被修改了
        if (!salaryPaymentOrderDO.getWorkerId().equals(updateObj.getWorkerId())) {
            throw exception(SALARY_PAYMENT_ORDER_WORKER_CANNOT_UPDATE);
        }
        //如果工资信息被修改了
        if (!salaryPaymentOrderDO.getSalaryId().equals(updateObj.getSalaryId())) {
            throw exception(SALARY_PAYMENT_ORDER_SALARY_CANNOT_UPDATE);
        }
        WorkerDO workerDO = judgeWorker(updateObj);
        if (ObjUtil.isNotNull(workerDO) && workerDO.getDebtAmount().compareTo(updateObj.getPaymentAmount()) != 0) {
            BigDecimal currentAmount = updateReqVO.getPaymentAmount().subtract(salaryPaymentOrderDO.getPaymentAmount());
            workerDO.setPaymentAmount(workerDO.getDebtAmount().add(currentAmount));
            workerService.updateWorkerAmount(workerDO);
        }
        salaryPaymentOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteSalaryPaymentOrder(Long id) {
        // 校验存在
        SalaryPaymentOrderDO salaryPaymentOrderDO = validateSalaryPaymentOrderExists(id);
        //查询工资信息
        SalaryDO salaryDO = salaryMapper.selectById(salaryPaymentOrderDO.getSalaryId());
        if (ObjUtil.isNotNull(salaryDO)) {
            salaryDO.setIsSettlement(CommonWhetherEnum.COMMON_WHETHER_2.getStatus());
        }
        //查询工人信息
        WorkerDO worker = workerService.getWorker(salaryPaymentOrderDO.getWorkerId());
        if (ObjUtil.isNotNull(worker)) {
            worker.setPaymentAmount(worker.getPaymentAmount().subtract(salaryPaymentOrderDO.getPaymentAmount()));
        }
        transactionTemplate.executeWithoutResult(status -> {
            salaryPaymentOrderMapper.deleteById(id);
            if (ObjUtil.isNotNull(worker)) {
                workerService.updateWorkerAmount(worker);
            }
            if (ObjUtil.isNotNull(salaryDO)) {
                salaryMapper.selectById(salaryDO);
            }
        });
    }

    @Override
    public void deleteSalaryPaymentOrderListByIds(List<Long> ids) {
        //首先查到所有的工资付款单
        List<SalaryPaymentOrderDO> salaryPaymentOrderDOS = salaryPaymentOrderMapper.selectByIds(ids);
        if (ArrayUtil.isEmpty(salaryPaymentOrderDOS)) {
            return;
        }
        //拿到所有的工人Id信息
        List<Long> workerIds = salaryPaymentOrderDOS.stream().map(SalaryPaymentOrderDO::getWorkerId).collect(Collectors.toList());
        Map<Long, WorkerDO> workerDOMap = new HashMap<>();
        List<WorkerDO> workerDOS = workerMapper.selectByIds(workerIds);
        if (ArrayUtil.isNotEmpty(workerDOS)) {
            workerDOMap = workerDOS.stream().collect(Collectors.toMap(WorkerDO::getId, item -> item));
        }
        //拿到所有的工资单
        List<Long> salaryIds = salaryPaymentOrderDOS.stream().map(SalaryPaymentOrderDO::getSalaryId).collect(Collectors.toList());
        List<SalaryDO> salaryDOS = salaryMapper.selectByIds(salaryIds);
        if (ArrayUtil.isNotEmpty(salaryDOS)) {
            salaryDOS.forEach(item -> item.setIsSettlement(CommonWhetherEnum.COMMON_WHETHER_2.getStatus()));
        }
        for (SalaryPaymentOrderDO item : salaryPaymentOrderDOS) {
            WorkerDO workerDO = workerDOMap.get(item.getWorkerId());
            if (ObjUtil.isNotNull(workerDO)) {
                workerDO.setPaymentAmount(workerDO.getPaymentAmount().subtract(item.getPaymentAmount()));
            }
        }
        Map<Long, WorkerDO> finalWorkerDOMap = workerDOMap;
        transactionTemplate.executeWithoutResult(status -> {
            salaryPaymentOrderMapper.deleteByIds(ids);
            if (ArrayUtil.isNotEmpty(workerDOS)) {
                finalWorkerDOMap.values().forEach(item -> workerService.updateWorkerAmount(item));
            }
            if (ArrayUtil.isNotEmpty(salaryDOS)) {
                salaryMapper.selectByIds(salaryDOS);
            }
        });
    }


    private SalaryPaymentOrderDO validateSalaryPaymentOrderExists(Long id) {
        SalaryPaymentOrderDO salaryPaymentOrderDO = salaryPaymentOrderMapper.selectById(id);
        if (salaryPaymentOrderDO == null) {
            throw exception(SALARY_PAYMENT_ORDER_NOT_EXISTS);
        }
        return salaryPaymentOrderDO;
    }

    @Override
    public SalaryPaymentOrderDO getSalaryPaymentOrder(Long id) {
        return salaryPaymentOrderMapper.selectById(id);
    }

    @Override
    public PageResult<SalaryPaymentOrderDO> getSalaryPaymentOrderPage(SalaryPaymentOrderPageReqVO pageReqVO) {
        return salaryPaymentOrderMapper.selectPage(pageReqVO);
    }

}
