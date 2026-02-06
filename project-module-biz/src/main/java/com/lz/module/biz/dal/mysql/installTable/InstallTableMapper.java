package com.lz.module.biz.dal.mysql.installTable;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.installTable.InstallTableDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.installTable.vo.*;

/**
 * 装表信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface InstallTableMapper extends BaseMapperX<InstallTableDO> {

    default PageResult<InstallTableDO> selectPage(InstallTablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InstallTableDO>()
                .betweenIfPresent(InstallTableDO::getInstallDate, reqVO.getInstallDate())
                .likeIfPresent(InstallTableDO::getCommunityName, reqVO.getCommunityName())
                .likeIfPresent(InstallTableDO::getMeterNo, reqVO.getMeterNo())
                .likeIfPresent(InstallTableDO::getMeterModel, reqVO.getMeterModel())
                .eqIfPresent(InstallTableDO::getMeterDirection, reqVO.getMeterDirection())
                .eqIfPresent(InstallTableDO::getExtraLengthFee, reqVO.getExtraLengthFee())
                .likeIfPresent(InstallTableDO::getInstallerName, reqVO.getInstallerName())
                .eqIfPresent(InstallTableDO::getIsHighAltitude, reqVO.getIsHighAltitude())
                .eqIfPresent(InstallTableDO::getIsOpenTee, reqVO.getIsOpenTee())
                .betweenIfPresent(InstallTableDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InstallTableDO::getId));
    }

}