package com.lz.module.biz.service.salary;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
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
import com.lz.module.biz.dal.mysql.paymentOrder.PaymentOrderMapper;
import com.lz.module.biz.dal.mysql.salary.SalaryMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import com.lz.module.biz.enums.BizPaymentPayeeTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.SALARY_NOT_EXISTS;
import static com.lz.module.biz.enums.ErrorCodeConstants.WORKER_NOT_EXISTS;

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
    private PaymentOrderMapper paymentOrderMapper;

    @Override
    public Long createSalary(SalarySaveReqVO createReqVO) {
        // 插入
        SalaryDO salary = BeanUtils.toBean(createReqVO, SalaryDO.class);
        //查询是否存在工人
        validateWorkerExists(salary);
        //如果工资信息有新增付款信息
        if (createReqVO.getIsAddPayment()) {
            PaymentOrderDO paymentOrderDO = getPaymentOrderDO(createReqVO.getPaymentNo(), createReqVO.getWorkerId(), salary.getWorkerName(),
                    createReqVO.getSettlementTime(), createReqVO.getPayableAmount(),
                    createReqVO.getPaymentMethod(), createReqVO.getIsInvoiced(), createReqVO.getPaymentCertificate(), "新增工资信息，系统自动新增");
            transactionTemplate.executeWithoutResult(status -> {
                paymentOrderMapper.insert(paymentOrderDO);
                salaryMapper.insert(salary);
            });
        } else {
            salaryMapper.insert(salary);
        }
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

    @Override
    public BigDecimal getTotalPayableAmount(SalaryPageReqVO pageReqVO) {
        return salaryMapper.getTotalPayableAmount(pageReqVO);
    }

    @Override
    public SalaryImportRespVO importSalaryList(List<SalaryImportExcelVO> list, Boolean isAddPayment) {
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
            if (ObjUtil.isNull(vo.getSettlementTime())) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行请填写结算时间", index));
            }
            if (ObjUtil.isNull(vo.getPayableAmount())) {
                throw new ServiceException(400,
                        StrUtil.format("第{}行请填写应发款项", index));
            }
            if (!isAddPayment) {
                continue;
            }
            if (ObjUtil.isEmpty(vo.getPaymentNo())) {
                throw new ServiceException(400,
                        StrUtil.format(
                                "第{}行请填写付款单号，因为您需要添加付款信息，付款信息的付款单号不能为空",
                                index));
            }
            if (ObjUtil.isEmpty(vo.getPaymentMethod())) {
                throw new ServiceException(400,
                        StrUtil.format(
                                "第{}行请填写付款方式，因为您需要添加付款信息，付款信息的付款方式不能为空",
                                index));
            }
            if (ObjUtil.isEmpty(vo.getIsInvoiced())) {
                throw new ServiceException(400,
                        StrUtil.format(
                                "第{}行请填写是否开票，因为您需要添加付款信息，付款信息的是否开票不能为空",
                                index));
            }
        }
        //遍历去重所有的工人编号，查询出所有的工人，防止没有这个工人
        List<Long> workerIds = list.stream().map(SalaryImportExcelVO::getWorkerId).filter(Objects::nonNull).distinct().toList();
        List<WorkerDO> workerDOList = workerMapper.selectList(new LambdaQueryWrapperX<WorkerDO>()
                .in(WorkerDO::getDailySalary, workerIds));
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
        ArrayList<PaymentOrderDO> paymentOrderDOS = new ArrayList<>();
        for (SalaryImportExcelVO vo : list) {
            if (ObjUtil.isNotNull(vo.getWorkerId())) {
                vo.setWorkerName(workerDOMap.get(vo.getWorkerId()).getWorkerName());
            }
            SalaryDO salaryDO = BeanUtils.toBean(vo, SalaryDO.class);
            salaryDOS.add(salaryDO);
            if (!isAddPayment) {
                continue;
            }
            PaymentOrderDO paymentOrderDO = getPaymentOrderDO(vo.getPaymentNo(), vo.getWorkerId(),
                    vo.getWorkerName(), vo.getSettlementTime(), vo.getPayableAmount(),
                    vo.getPaymentMethod(), vo.getIsInvoiced(), null, "导入工资信息，系统自动新增");
            paymentOrderDOS.add(paymentOrderDO);
        }
        transactionTemplate.executeWithoutResult(status -> {
            salaryMapper.insertBatch(salaryDOS);
            paymentOrderMapper.insertBatch(paymentOrderDOS);
        });
        return SalaryImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个工资信息", salaryDOS.size()))
                .build();
    }

    private PaymentOrderDO getPaymentOrderDO(String paymentNo, Long workerId, String workerName,
                                             LocalDateTime settlementTime, BigDecimal payableAmount,
                                             String paymentMethod, String isInvoiced, String paymentCertificate, String remark) {
        PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
        paymentOrderDO.setPaymentNo(paymentNo);
        paymentOrderDO.setPayeeType(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_1.getStatus());
        paymentOrderDO.setPayeeId(workerId);
        paymentOrderDO.setPayeeName(workerName);
        paymentOrderDO.setPaymentTime(settlementTime);
        paymentOrderDO.setPaymentAmount(payableAmount);
        paymentOrderDO.setPaymentMethod(paymentMethod);
        paymentOrderDO.setIsInvoiced(isInvoiced);
        paymentOrderDO.setPaymentCertificate(paymentCertificate);
        paymentOrderDO.setRemark(remark);
        return paymentOrderDO;
    }

}
