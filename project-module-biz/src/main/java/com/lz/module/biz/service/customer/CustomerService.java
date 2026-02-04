package com.lz.module.biz.service.customer;

import java.util.*;
import jakarta.validation.*;
import com.lz.module.biz.controller.admin.customer.vo.*;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;

/**
 * 客户信息 Service 接口
 *
 * @author YY
 */
public interface CustomerService {

    /**
     * 创建客户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomer(@Valid CustomerSaveReqVO createReqVO);

    /**
     * 更新客户信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomer(@Valid CustomerSaveReqVO updateReqVO);

    /**
     * 删除客户信息
     *
     * @param id 编号
     */
    void deleteCustomer(Long id);

    /**
    * 批量删除客户信息
    *
    * @param ids 编号
    */
    void deleteCustomerListByIds(List<Long> ids);

    /**
     * 获得客户信息
     *
     * @param id 编号
     * @return 客户信息
     */
    CustomerDO getCustomer(Long id);

    /**
     * 获得客户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 客户信息分页
     */
    PageResult<CustomerDO> getCustomerPage(CustomerPageReqVO pageReqVO);

}