package com.lz.module.biz.service.rawMaterials;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsImportRespVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsImportVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsPageReqVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsSaveReqVO;
import com.lz.module.biz.dal.dataobject.rawMaterials.RawMaterialsDO;
import com.lz.module.biz.dal.mysql.rawMaterials.RawMaterialsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.RAW_MATERIALS_NOT_EXISTS;

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

    @Override
    public RawMaterialsImportRespVO importRawMaterialsList(List<RawMaterialsImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<RawMaterialsDO> createList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            RawMaterialsImportVO rawMaterialsImportVO = list.get(i);
            int index = i + 1;
            //类别、名称不能为空
            if (StrUtil.isEmpty(rawMaterialsImportVO.getMaterialType())) {
                throw new ServiceException(400, StrUtil.format("第{}行类别不能为空", index));
            }
            if (StrUtil.isEmpty(rawMaterialsImportVO.getMaterialName())) {
                throw new ServiceException(400, StrUtil.format("第{}行名称不能为空", index));
            }
            RawMaterialsDO rawMaterialsDO = BeanUtils.toBean(rawMaterialsImportVO, RawMaterialsDO.class);
            createList.add(rawMaterialsDO);
        }
        rawMaterialsMapper.insertBatch(createList);
        return RawMaterialsImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个原材料信息", createList.size()))
                .build();
    }

}
