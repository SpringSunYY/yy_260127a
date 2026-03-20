package com.lz.module.biz.service.worker;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.worker.vo.WorkerImportRespVO;
import com.lz.module.biz.controller.admin.worker.vo.WorkerImportVO;
import com.lz.module.biz.controller.admin.worker.vo.WorkerPageReqVO;
import com.lz.module.biz.controller.admin.worker.vo.WorkerSaveReqVO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.WORKER_NOT_EXISTS;

/**
 * 工人信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class WorkerServiceImpl implements WorkerService {

    @Resource
    private WorkerMapper workerMapper;

    @Override
    public Long createWorker(WorkerSaveReqVO createReqVO) {
        // 插入
        WorkerDO worker = BeanUtils.toBean(createReqVO, WorkerDO.class);
        workerMapper.insert(worker);

        // 返回
        return worker.getId();
    }

    @Override
    public void updateWorker(WorkerSaveReqVO updateReqVO) {
        // 校验存在
        validateWorkerExists(updateReqVO.getId());
        // 更新
        WorkerDO updateObj = BeanUtils.toBean(updateReqVO, WorkerDO.class);
        workerMapper.updateById(updateObj);
    }

    @Override
    public void deleteWorker(Long id) {
        // 校验存在
        validateWorkerExists(id);
        // 删除
        workerMapper.deleteById(id);
    }

    @Override
    public void deleteWorkerListByIds(List<Long> ids) {
        // 删除
        workerMapper.deleteByIds(ids);
    }


    private void validateWorkerExists(Long id) {
        if (workerMapper.selectById(id) == null) {
            throw exception(WORKER_NOT_EXISTS);
        }
    }

    @Override
    public WorkerDO getWorker(Long id) {
        return workerMapper.selectById(id);
    }

    @Override
    public PageResult<WorkerDO> getWorkerPage(WorkerPageReqVO pageReqVO) {
        return workerMapper.selectPage(pageReqVO);
    }

    @Override
    public WorkerImportRespVO importWorkerList(List<WorkerImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<WorkerDO> workerDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            WorkerImportVO vo = list.get(i);
            //工人姓名、工人类型、工人状态不能为空
            if (StrUtil.isEmpty(vo.getWorkerName())) {
                throw new ServiceException(400, StrUtil.format("第{}行，工人姓名不能为空", index));
            }
            if (StrUtil.isEmpty(vo.getWorkerType())) {
                throw new ServiceException(400, StrUtil.format("第{}行，工人类型不能为空", index));
            }
            if (StrUtil.isEmpty(vo.getStatus())) {
                throw new ServiceException(400, StrUtil.format("第{}行，工人状态不能为空", index));
            }
            if (ObjUtil.isNull(vo.getDebtAmount())) {
                vo.setDebtAmount(BigDecimal.ZERO);
            }
            if (ObjUtil.isNull(vo.getPaymentAmount())) {
                vo.setPaymentAmount(BigDecimal.ZERO);
            }
            if (ObjUtil.isNull(vo.getPayableAmount())) {
                vo.setPayableAmount(BigDecimal.ZERO);
            }
            WorkerDO bean = BeanUtils.toBean(vo, WorkerDO.class);
            workerDOS.add(bean);
        }
        workerMapper.insertBatch(workerDOS);
        return WorkerImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个工人信息", workerDOS.size())).build();

    }

    @Override
    public void updateWorkerAmount(WorkerDO workerDO) {
        //应付金额-已付金额=欠款金额
        BigDecimal payableAmount = workerDO.getPayableAmount();
        BigDecimal paymentAmount = workerDO.getPaymentAmount();
        if (ObjUtil.isNotNull(payableAmount) && ObjUtil.isNotNull(paymentAmount)) {
            BigDecimal debtAmount = payableAmount.subtract(paymentAmount);
//            if (debtAmount.compareTo(BigDecimal.ZERO) <= 0) {
//                debtAmount = BigDecimal.ZERO;
//            }
            workerDO.setDebtAmount(debtAmount);
            if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
                workerDO.setPaymentAmount(BigDecimal.ZERO);
            }
            if (payableAmount.compareTo(BigDecimal.ZERO) <= 0) {
                workerDO.setPayableAmount(BigDecimal.ZERO);
            }
        }
        workerMapper.updateById(workerDO);
    }

}
