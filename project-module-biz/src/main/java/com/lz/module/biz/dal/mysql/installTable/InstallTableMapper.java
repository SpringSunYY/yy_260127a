package com.lz.module.biz.dal.mysql.installTable;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.module.biz.controller.admin.installTable.vo.InstallTablePageReqVO;
import com.lz.module.biz.dal.dataobject.installTable.InstallTableDO;
import org.apache.ibatis.annotations.Mapper;

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
                .likeIfPresent(InstallTableDO::getFloorHeightStatus, reqVO.getFloorHeightStatus())
                .likeIfPresent(InstallTableDO::getOwnerName, reqVO.getOwnerName())
                .eqIfPresent(InstallTableDO::getExtraLengthFee, reqVO.getExtraLengthFee())
                .likeIfPresent(InstallTableDO::getInstallerName, reqVO.getInstallerName())
                .eqIfPresent(InstallTableDO::getIsHighAltitude, reqVO.getIsHighAltitude())
                .eqIfPresent(InstallTableDO::getIsOpenTee, reqVO.getIsOpenTee())
                .eqIfPresent(InstallTableDO::getAddMeterBox, reqVO.getAddMeterBox())
                .likeIfPresent(InstallTableDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(InstallTableDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InstallTableDO::getId));
    }

}
