package com.lz.module.biz.service.salary;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.salary.vo.*;
import com.lz.module.biz.dal.dataobject.salary.SalaryDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 工资信息 Service 接口
 *
 * @author 芋道源码
 */
public interface SalaryService {

    /**
     * 创建工资信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalary(@Valid SalarySaveReqVO createReqVO);

    /**
     * 更新工资信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSalary(@Valid SalarySaveReqVO updateReqVO);

    /**
     * 删除工资信息
     *
     * @param id 编号
     */
    void deleteSalary(Long id);

    /**
    * 批量删除工资信息
    *
    * @param ids 编号
    */
    void deleteSalaryListByIds(List<Long> ids);

    /**
     * 获得工资信息
     *
     * @param id 编号
     * @return 工资信息
     */
    SalaryDO getSalary(Long id);

    /**
     * 获得工资信息分页
     *
     * @param pageReqVO 分页查询
     * @return 工资信息分页
     */
    PageResult<SalaryDO> getSalaryPage(SalaryPageReqVO pageReqVO);

    /**
     * 导入工资信息
     *
     * @param list 文件列表
     * @return 导入结果
     */
    SalaryImportRespVO importSalaryList(List<SalaryImportExcelVO> list);
}
