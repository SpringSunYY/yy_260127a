package com.lz.module.biz.service.salaryPaymentOrder;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.enums.CommonWhetherEnum;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.id.IdUtils;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.SalaryPaymentOrderImportRespVO;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.SalaryPaymentOrderImportVO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
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

    @Override
    public BigDecimal getSalaryPaymentOrderAmount(SalaryPaymentOrderPageReqVO pageReqVO) {
        return salaryPaymentOrderMapper.getSalaryPaymentOrderAmount(pageReqVO);
    }

    @Override
    public SalaryPaymentOrderImportRespVO importSalaryPaymentOrderList(List<SalaryPaymentOrderImportVO> list) {
        //首先校验数据
        judgeImportData(list);
        //初始化工人、供应商数据
        Map<Long, WorkerDO> workerDOMap = new HashMap<>();
        Map<Long, SalaryDO> salaryDOMap = new HashMap<>();
        initImportData(list, workerDOMap, salaryDOMap);
        //校验并更新数据
        List<SalaryPaymentOrderDO> salaryPaymentOrderDOS = getSalaryPaymentOrderDos(list, workerDOMap, salaryDOMap);
        List<SalaryDO> salaryDOS = salaryDOMap.values().stream().toList();
        transactionTemplate.executeWithoutResult(status -> {
            salaryPaymentOrderMapper.insertBatch(salaryPaymentOrderDOS);
            if (ArrayUtil.isNotEmpty(salaryDOS)) {
                salaryMapper.updateById(salaryDOS);
            }
            workerDOMap.forEach((id, workerDO) -> workerService.updateWorkerAmount(workerDO));
        });
        return SalaryPaymentOrderImportRespVO.builder().message("导入成功，成功导入" + salaryPaymentOrderDOS.size() + "条数据").build();
    }

    private List<SalaryPaymentOrderDO> getSalaryPaymentOrderDos(List<SalaryPaymentOrderImportVO> list,
                                                                Map<Long, WorkerDO> workerDOMap,
                                                                Map<Long, SalaryDO> salaryDOMap) {
        List<SalaryPaymentOrderDO> salaryPaymentOrderDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SalaryPaymentOrderImportVO item = list.get(i);
            SalaryPaymentOrderDO orderDO = BeanUtils.toBean(item, SalaryPaymentOrderDO.class);

            int index = i + 1;
            //工人
            WorkerDO workerDO = workerDOMap.get(item.getWorkerId());
            if (ObjUtil.isNull(workerDO)) {
                throw new ServiceException(400, StrUtil.format("第{}行工人信息不存在", index));
            }
            orderDO.setWorkerId(workerDO.getId());
            orderDO.setWorkerName(workerDO.getWorkerName());
            workerDO.setPaymentAmount(workerDO.getPaymentAmount().add(orderDO.getPaymentAmount()));

            //工资
            if (ObjUtil.isNotNull(item.getSalaryId())) {
                SalaryDO salaryDO = salaryDOMap.get(item.getSalaryId());
                if (ObjUtil.isNull(salaryDO)) {
                    throw new ServiceException(400, StrUtil.format("第{}行工资信息不存在", index));
                }
                //如果是已经支付
                if (salaryDO.getIsSettlement().equals(CommonWhetherEnum.COMMON_WHETHER_1.getStatus())) {
                    throw new ServiceException(400, StrUtil.format("第{}行工资信息已支付", index));
                }
                //如果工人和工资不匹配
                if (!salaryDO.getWorkerId().equals(workerDO.getId())) {
                    throw new ServiceException(400, StrUtil.format("第{}行工人和工资不匹配", index));
                }
                orderDO.setSalaryId(salaryDO.getId());
                orderDO.setSalaryName(salaryDO.getName());
            }
            orderDO.setPaymentNo(IdUtils.generateTimeRandomId());
            salaryPaymentOrderDOS.add(orderDO);
        }
        salaryDOMap.forEach((key, value) -> value.setIsSettlement(CommonWhetherEnum.COMMON_WHETHER_1.getStatus()));
        return salaryPaymentOrderDOS;
    }

    private void initImportData(List<SalaryPaymentOrderImportVO> list, Map<Long, WorkerDO> workerDOMap, Map<Long, SalaryDO> salaryDOMap) {
        //工人 ids
        Set<Long> workerIds = new HashSet<>();
        Set<Long> salaryIds = new HashSet<>();
        for (SalaryPaymentOrderImportVO item : list) {
            if (ObjUtil.isNotNull(item.getWorkerId())) {
                workerIds.add(item.getWorkerId());
            }
            if (ObjUtil.isNotNull(item.getSalaryId())) {
                salaryIds.add(item.getSalaryId());
            }
        }
        List<WorkerDO> workerDOS = workerMapper.selectByIds(workerIds);
        if (ArrayUtil.isEmpty(workerDOS)) {
            throw new ServiceException(400, "导入数据工人信息不存在");
        }
        workerDOMap.putAll(workerDOS.stream().collect(Collectors.toMap(WorkerDO::getId, item -> item)));
        List<SalaryDO> salaryDOS = salaryMapper.selectByIds(salaryIds);
        if (ArrayUtil.isNotEmpty(salaryDOS)) {
            salaryDOMap.putAll(salaryDOS.stream().collect(Collectors.toMap(SalaryDO::getId, item -> item)));
        }
    }

    private static void judgeImportData(List<SalaryPaymentOrderImportVO> list) {
        if (ArrayUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        for (int i = 0; i < list.size(); i++) {
            //是否开票、付款方式、付款单号、付款金额、付款时间、收款对象、是否开票不能为空
            SalaryPaymentOrderImportVO importVO = list.get(i);
            int index = i + 1;
            if (StrUtil.isEmpty(importVO.getIsInvoiced())) {
                throw new ServiceException(400, "第" + index + "行数据，是否开票不能为空");
            }
            if (StrUtil.isEmpty(importVO.getPaymentMethod())) {
                throw new ServiceException(400, "第" + index + "行数据，付款方式不能为空");
            }
            if (ObjUtil.isNull(importVO.getPaymentAmount())) {
                throw new ServiceException(400, "第" + index + "行数据，付款金额不能为空");
            }
            if (ObjUtil.isNull(importVO.getPaymentTime())) {
                throw new ServiceException(400, "第" + index + "行数据，付款时间不能为空");
            }
            if (ObjUtil.isNull(importVO.getWorkerId())) {
                throw new ServiceException(400, "第" + index + "行数据，工人ID不能为空");
            }
        }
    }

}
