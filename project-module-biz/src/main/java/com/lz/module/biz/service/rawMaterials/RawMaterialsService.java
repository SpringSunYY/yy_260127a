package com.lz.module.biz.service.rawMaterials;

import com.lz.framework.common.pojo.PageResult;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsImportRespVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsImportVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsPageReqVO;
import com.lz.module.biz.controller.admin.rawMaterials.vo.RawMaterialsSaveReqVO;
import com.lz.module.biz.dal.dataobject.rawMaterials.RawMaterialsDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 原材料信息 Service 接口
 *
 * @author YY
 */
public interface RawMaterialsService {

    /**
     * 创建原材料信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRawMaterials(@Valid RawMaterialsSaveReqVO createReqVO);

    /**
     * 更新原材料信息
     *
     * @param updateReqVO 更新信息
     */
    void updateRawMaterials(@Valid RawMaterialsSaveReqVO updateReqVO);

    /**
     * 删除原材料信息
     *
     * @param id 编号
     */
    void deleteRawMaterials(Long id);

    /**
     * 批量删除原材料信息
     *
     * @param ids 编号
     */
    void deleteRawMaterialsListByIds(List<Long> ids);

    /**
     * 获得原材料信息
     *
     * @param id 编号
     * @return 原材料信息
     */
    RawMaterialsDO getRawMaterials(Long id);

    /**
     * 获得原材料信息分页
     *
     * @param pageReqVO 分页查询
     * @return 原材料信息分页
     */
    PageResult<RawMaterialsDO> getRawMaterialsPage(RawMaterialsPageReqVO pageReqVO);

    /**
     * 导原材料信息列表
     *
     * @param list 原材料信息列表
     * @return 导出数量
     */
    RawMaterialsImportRespVO importRawMaterialsList(List<RawMaterialsImportVO> list);
}
