package com.lz.module.biz.service.receiptOrder;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.receiptOrder.vo.*;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 收款信息 Service 接口
 *
 * @author YY
 */
public interface ReceiptOrderService {

    /**
     * 创建收款信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReceiptOrder(@Valid ReceiptOrderSaveReqVO createReqVO);

    /**
     * 更新收款信息
     *
     * @param updateReqVO 更新信息
     */
    void updateReceiptOrder(@Valid ReceiptOrderSaveReqVO updateReqVO);

    /**
     * 更新收款信息(包含null值)
     *
     * @param updateReqVO 更新信息
     */
    void updateReceiptOrderWithNull(@Valid ReceiptOrderDO updateReqVO);

    /**
     * 删除收款信息
     *
     * @param id 编号
     */
    void deleteReceiptOrder(Long id);

    /**
    * 批量删除收款信息
    *
    * @param ids 编号
    */
    void deleteReceiptOrderListByIds(List<Long> ids);

    /**
     * 获得收款信息
     *
     * @param id 编号
     * @return 收款信息
     */
    ReceiptOrderDO getReceiptOrder(Long id);

    /**
     * 获得收款信息分页
     *
     * @param pageReqVO 分页查询
     * @return 收款信息分页
     */
    PageResult<ReceiptOrderDO> getReceiptOrderPage(ReceiptOrderPageReqVO pageReqVO);

    /**
     * 导入收款信息列表, 用于 Excel 导入
     *
     * @return 收款信息列表
     */
    ReceiptOrderImportRespVO importReceiptOrderList(List<ReceiptOrderImportExcelVO> list);
}
