package com.lz.module.biz.service.customer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.customer.vo.CustomerImportRespVO;
import com.lz.module.biz.controller.admin.customer.vo.CustomerImportVO;
import com.lz.module.biz.controller.admin.customer.vo.CustomerPageReqVO;
import com.lz.module.biz.controller.admin.customer.vo.CustomerSaveReqVO;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.module.biz.dal.mysql.customer.CustomerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;

/**
 * 客户信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Long createCustomer(CustomerSaveReqVO createReqVO) {
        // 插入
        CustomerDO customer = BeanUtils.toBean(createReqVO, CustomerDO.class);
        customerMapper.insert(customer);

        // 返回
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerExists(updateReqVO.getId());
        // 更新
        CustomerDO updateObj = BeanUtils.toBean(updateReqVO, CustomerDO.class);
        customerMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
        // 删除
        customerMapper.deleteById(id);
    }

    @Override
    public void deleteCustomerListByIds(List<Long> ids) {
        // 删除
        customerMapper.deleteByIds(ids);
    }


    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
    }

    @Override
    public CustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerDO> getCustomerPage(CustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage(pageReqVO);
    }

    @Override
    public CustomerImportRespVO importCustomerList(List<CustomerImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<CustomerDO> createList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            CustomerImportVO customerImportVO = list.get(i);
            CustomerDO customer = BeanUtils.toBean(customerImportVO, CustomerDO.class);
            createList.add(customer);
        }
        customerMapper.insertBatch(createList);
        return CustomerImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个客户信息", createList.size())).build();
    }
}
