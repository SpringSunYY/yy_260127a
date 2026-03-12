package com.lz.module.biz.dal.mysql.rawMaterials;

import java.util.*;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.module.biz.dal.dataobject.rawMaterials.RawMaterialsDO;
import org.apache.ibatis.annotations.Mapper;
import com.lz.module.biz.controller.admin.rawMaterials.vo.*;

/**
 * 原材料信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface RawMaterialsMapper extends BaseMapperX<RawMaterialsDO> {

    default PageResult<RawMaterialsDO> selectPage(RawMaterialsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RawMaterialsDO>()
                .likeIfPresent(RawMaterialsDO::getMaterialName, reqVO.getMaterialName())
                .eqIfPresent(RawMaterialsDO::getMaterialType, reqVO.getMaterialType())
                .betweenIfPresent(RawMaterialsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RawMaterialsDO::getId));
    }

}
