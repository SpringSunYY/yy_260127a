package com.lz.module.biz.service.installTable;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.installTable.vo.*;
import com.lz.module.biz.dal.dataobject.installTable.InstallTableDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 装表信息 Service 接口
 *
 * @author YY
 */
public interface InstallTableService {

    /**
     * 创建装表信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInstallTable(@Valid InstallTableSaveReqVO createReqVO);

    /**
     * 更新装表信息
     *
     * @param updateReqVO 更新信息
     */
    void updateInstallTable(@Valid InstallTableSaveReqVO updateReqVO);

    /**
     * 删除装表信息
     *
     * @param id 编号
     */
    void deleteInstallTable(Long id);

    /**
    * 批量删除装表信息
    *
    * @param ids 编号
    */
    void deleteInstallTableListByIds(List<Long> ids);

    /**
     * 获得装表信息
     *
     * @param id 编号
     * @return 装表信息
     */
    InstallTableDO getInstallTable(Long id);

    /**
     * 获得装表信息分页
     *
     * @param pageReqVO 分页查询
     * @return 装表信息分页
     */
    PageResult<InstallTableDO> getInstallTablePage(InstallTablePageReqVO pageReqVO);

}