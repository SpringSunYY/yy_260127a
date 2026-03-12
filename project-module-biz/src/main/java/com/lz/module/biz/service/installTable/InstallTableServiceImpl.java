package com.lz.module.biz.service.installTable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.installTable.vo.InstallTableImportRespVO;
import com.lz.module.biz.controller.admin.installTable.vo.InstallTableImportVO;
import com.lz.module.biz.controller.admin.installTable.vo.InstallTablePageReqVO;
import com.lz.module.biz.controller.admin.installTable.vo.InstallTableSaveReqVO;
import com.lz.module.biz.dal.dataobject.installTable.InstallTableDO;
import com.lz.module.biz.dal.mysql.installTable.InstallTableMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.INSTALL_TABLE_NOT_EXISTS;

/**
 * 装表信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class InstallTableServiceImpl implements InstallTableService {

    @Resource
    private InstallTableMapper installTableMapper;

    @Override
    public Long createInstallTable(InstallTableSaveReqVO createReqVO) {
        // 插入
        InstallTableDO installTable = BeanUtils.toBean(createReqVO, InstallTableDO.class);
        installTableMapper.insert(installTable);

        // 返回
        return installTable.getId();
    }

    @Override
    public void updateInstallTable(InstallTableSaveReqVO updateReqVO) {
        // 校验存在
        validateInstallTableExists(updateReqVO.getId());
        // 更新
        InstallTableDO updateObj = BeanUtils.toBean(updateReqVO, InstallTableDO.class);
        installTableMapper.updateById(updateObj);
    }

    @Override
    public void deleteInstallTable(Long id) {
        // 校验存在
        validateInstallTableExists(id);
        // 删除
        installTableMapper.deleteById(id);
    }

    @Override
    public void deleteInstallTableListByIds(List<Long> ids) {
        // 删除
        installTableMapper.deleteByIds(ids);
    }


    private void validateInstallTableExists(Long id) {
        if (installTableMapper.selectById(id) == null) {
            throw exception(INSTALL_TABLE_NOT_EXISTS);
        }
    }

    @Override
    public InstallTableDO getInstallTable(Long id) {
        return installTableMapper.selectById(id);
    }

    @Override
    public PageResult<InstallTableDO> getInstallTablePage(InstallTablePageReqVO pageReqVO) {
        return installTableMapper.selectPage(pageReqVO);
    }

    @Override
    public InstallTableImportRespVO importInstallTableList(List<InstallTableImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<InstallTableDO> createList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            InstallTableImportVO installTableImportVO = list.get(i);
            InstallTableDO installTableDO = BeanUtils.toBean(installTableImportVO, InstallTableDO.class);
            createList.add(installTableDO);
        }
        installTableMapper.insertBatch(createList);
        return InstallTableImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个装表信息", createList.size())).build();
    }

}
