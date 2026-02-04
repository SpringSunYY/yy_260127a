package com.lz.module.biz.service.rawMaterials;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.lz.module.biz.controller.admin.rawMaterials.vo.*;
import com.lz.module.biz.dal.dataobject.rawMaterials.RawMaterialsDO;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.pojo.PageParam;
import com.lz.framework.common.util.object.BeanUtils;

import com.lz.module.biz.dal.mysql.rawMaterials.RawMaterialsMapper;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

/**
 * 原材料信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class RawMaterialsServiceImpl implements RawMaterialsService {

    @Resource
    private RawMaterialsMapper rawMaterialsMapper;

    @Override
    public Long createRawMaterials(RawMaterialsSaveReqVO createReqVO) {
        // 插入
        RawMaterialsDO rawMaterials = BeanUtils.toBean(createReqVO, RawMaterialsDO.class);
        rawMaterialsMapper.insert(rawMaterials);

        // 返回
        return rawMaterials.getId();
    }

    @Override
    public void updateRawMaterials(RawMaterialsSaveReqVO updateReqVO) {
        // 校验存在
        validateRawMaterialsExists(updateReqVO.getId());
        // 更新
        RawMaterialsDO updateObj = BeanUtils.toBean(updateReqVO, RawMaterialsDO.class);
        rawMaterialsMapper.updateById(updateObj);
    }

    @Override
    public void deleteRawMaterials(Long id) {
        // 校验存在
        validateRawMaterialsExists(id);
        // 删除
        rawMaterialsMapper.deleteById(id);
    }

    @Override
        public void deleteRawMaterialsListByIds(List<Long> ids) {
        // 删除
        rawMaterialsMapper.deleteByIds(ids);
        }


    private void validateRawMaterialsExists(Long id) {
        if (rawMaterialsMapper.selectById(id) == null) {
            throw exception(RAW_MATERIALS_NOT_EXISTS);
        }
    }

    @Override
    public RawMaterialsDO getRawMaterials(Long id) {
        return rawMaterialsMapper.selectById(id);
    }

    @Override
    public PageResult<RawMaterialsDO> getRawMaterialsPage(RawMaterialsPageReqVO pageReqVO) {
        return rawMaterialsMapper.selectPage(pageReqVO);
    }

}