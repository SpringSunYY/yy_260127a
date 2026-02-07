package com.lz.module.biz.service.worker;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.worker.vo.*;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 工人信息 Service 接口
 *
 * @author YY
 */
public interface WorkerService {

    /**
     * 创建工人信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorker(@Valid WorkerSaveReqVO createReqVO);

    /**
     * 更新工人信息
     *
     * @param updateReqVO 更新信息
     */
    void updateWorker(@Valid WorkerSaveReqVO updateReqVO);

    /**
     * 删除工人信息
     *
     * @param id 编号
     */
    void deleteWorker(Long id);

    /**
    * 批量删除工人信息
    *
    * @param ids 编号
    */
    void deleteWorkerListByIds(List<Long> ids);

    /**
     * 获得工人信息
     *
     * @param id 编号
     * @return 工人信息
     */
    WorkerDO getWorker(Long id);

    /**
     * 获得工人信息分页
     *
     * @param pageReqVO 分页查询
     * @return 工人信息分页
     */
    PageResult<WorkerDO> getWorkerPage(WorkerPageReqVO pageReqVO);

}