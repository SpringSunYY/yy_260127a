package com.lz.module.biz.dal.mysql.customer;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.customer.vo.*;

/**
 * 客户信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface CustomerMapper extends BaseMapperX<CustomerDO> {

    default PageResult<CustomerDO> selectPage(CustomerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomerDO>()
                .eqIfPresent(CustomerDO::getId, reqVO.getId())
                .likeIfPresent(CustomerDO::getName, reqVO.getName())
                .likeIfPresent(CustomerDO::getTelephone, reqVO.getTelephone())
                .likeIfPresent(CustomerDO::getQq, reqVO.getQq())
                .likeIfPresent(CustomerDO::getWeChat, reqVO.getWeChat())
                .likeIfPresent(CustomerDO::getEmail, reqVO.getEmail())
                .eqIfPresent(CustomerDO::getDetailAddress, reqVO.getDetailAddress())
                .eqIfPresent(CustomerDO::getIndustry, reqVO.getIndustry())
                .betweenIfPresent(CustomerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomerDO::getId));
    }

}
