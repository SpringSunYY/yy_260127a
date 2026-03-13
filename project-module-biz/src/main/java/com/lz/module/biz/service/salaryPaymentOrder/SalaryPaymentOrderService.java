package com.lz.module.biz.service.salaryPaymentOrder;

import java.math.BigDecimal;
import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.salaryPaymentOrder.vo.*;
import com.lz.module.biz.dal.dataobject.salaryPaymentOrder.SalaryPaymentOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 工资付款信息 Service 接口
 *
 * @author YY
 */
public interface SalaryPaymentOrderService {

    /**
     * 创建工资付款信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalaryPaymentOrder(@Valid SalaryPaymentOrderSaveReqVO createReqVO);

    /**
     * 更新工资付款信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSalaryPaymentOrder(@Valid SalaryPaymentOrderSaveReqVO updateReqVO);

    /**
     * 删除工资付款信息
     *
     * @param id 编号
     */
    void deleteSalaryPaymentOrder(Long id);

    /**
    * 批量删除工资付款信息
    *
    * @param ids 编号
    */
    void deleteSalaryPaymentOrderListByIds(List<Long> ids);

    /**
     * 获得工资付款信息
     *
     * @param id 编号
     * @return 工资付款信息
     */
    SalaryPaymentOrderDO getSalaryPaymentOrder(Long id);

    /**
     * 获得工资付款信息分页
     *
     * @param pageReqVO 分页查询
     * @return 工资付款信息分页
     */
    PageResult<SalaryPaymentOrderDO> getSalaryPaymentOrderPage(SalaryPaymentOrderPageReqVO pageReqVO);

    /**
     * 获得工资付款信息金额
     *
     * @param pageReqVO 分页查询
     * @return 工资付款信息金额
     */
    BigDecimal getSalaryPaymentOrderAmount(@Valid SalaryPaymentOrderPageReqVO pageReqVO);
    /**
     * 导入工资付款信息
     *
     * @param list 文件列表
     * @return 导入结果
     */
    SalaryPaymentOrderImportRespVO importSalaryPaymentOrderList(List<SalaryPaymentOrderImportVO> list);
}
