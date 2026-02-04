package com.lz.module.biz.dal.mysql.supplier;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.supplier.vo.*;

/**
 * 供应商信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface SupplierMapper extends BaseMapperX<SupplierDO> {

    default PageResult<SupplierDO> selectPage(SupplierPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SupplierDO>()
                .likeIfPresent(SupplierDO::getName, reqVO.getName())
                .likeIfPresent(SupplierDO::getTelephone, reqVO.getTelephone())
                .likeIfPresent(SupplierDO::getQq, reqVO.getQq())
                .likeIfPresent(SupplierDO::getWeChat, reqVO.getWeChat())
                .likeIfPresent(SupplierDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(SupplierDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SupplierDO::getId));
    }

}