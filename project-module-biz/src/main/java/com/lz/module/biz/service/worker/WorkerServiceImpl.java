package com.lz.module.biz.service.worker;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.worker.vo.*;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.worker.WorkerMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

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

}