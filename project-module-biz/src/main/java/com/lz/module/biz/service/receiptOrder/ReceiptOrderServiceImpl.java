package com.lz.module.biz.service.receiptOrder;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.pojo.PageResult;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderPageReqVO;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;
import com.lz.module.biz.dal.mysql.receiptOrder.ReceiptOrderMapper;
import com.lz.module.biz.enums.BizReceiptProjectTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.RECEIPT_ORDER_NOT_EXISTS;

/**
 * 收款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class ReceiptOrderServiceImpl implements ReceiptOrderService {

    @Resource
    private ReceiptOrderMapper receiptOrderMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectOtherMapper projectOtherMapper;

    @Override
    public Long createReceiptOrder(ReceiptOrderSaveReqVO createReqVO) {
        // 插入
        ReceiptOrderDO receiptOrder = BeanUtils.toBean(createReqVO, ReceiptOrderDO.class);
        //初始化信息
        initReceiptOrder(receiptOrder);
        receiptOrderMapper.insert(receiptOrder);

        // 返回
        return receiptOrder.getId();
    }

    private void initReceiptOrder(ReceiptOrderDO receiptOrder) {
        //首先判断是否选择了项目类型
        if (StrUtil.isEmpty(receiptOrder.getProjectType())) {
            return;
        }
        //判断项目类型是什么
        if (StrUtil.equals(receiptOrder.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_2.getStatus())) {
            //查询项目是否存在
            ProjectOtherDO projectOtherDO = projectOtherMapper.selectById(receiptOrder.getProjectId());
            if (ObjUtil.isNull(projectOtherDO)) {
                throw exception(RECEIPT_ORDER_NOT_EXISTS);
            }
            receiptOrder.setProjectName(projectOtherDO.getProjectName());
            receiptOrder.setProjectNo(null);
            receiptOrder.setFiscalYear(null);
            receiptOrder.setProjectScatteredType(null);
        } else {
            //默认就是这个
            ProjectDO projectDO = projectMapper.selectById(receiptOrder.getProjectId());
            if (ObjUtil.isNull(projectDO)) {
                throw exception(RECEIPT_ORDER_NOT_EXISTS);
            }
            receiptOrder.setProjectNo(projectDO.getProjectNo());
            receiptOrder.setProjectName(projectDO.getName());
            receiptOrder.setFiscalYear(projectDO.getFiscalYear());
            receiptOrder.setProjectScatteredType(projectDO.getEngineeringType());
        }
    }

    @Override
    public void updateReceiptOrder(ReceiptOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReceiptOrderExists(updateReqVO.getId());
        // 更新
        ReceiptOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReceiptOrderDO.class);
        initReceiptOrder(updateObj);
        updateReceiptOrderWithNull(updateObj);
    }

    @Override
    public void updateReceiptOrderWithNull(ReceiptOrderDO receiptOrderDO) {
        // 校验存在
        validateReceiptOrderExists(receiptOrderDO.getId());
        // 更新
        ReceiptOrderDO updateObj = BeanUtils.toBean(receiptOrderDO, ReceiptOrderDO.class);
        initReceiptOrder(updateObj);

        LambdaUpdateWrapper<ReceiptOrderDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ReceiptOrderDO::getId, receiptOrderDO.getId());

        // Helper method to set null-able fields
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptNo, receiptOrderDO.getReceiptNo());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptType, receiptOrderDO.getReceiptType());
        setNullIfPresent(wrapper, ReceiptOrderDO::getProjectType, receiptOrderDO.getProjectType());
        setNullIfPresent(wrapper, ReceiptOrderDO::getProjectScatteredType, receiptOrderDO.getProjectScatteredType());
        setNullIfPresent(wrapper, ReceiptOrderDO::getFiscalYear, receiptOrderDO.getFiscalYear());
        setNullIfPresent(wrapper, ReceiptOrderDO::getProjectId, receiptOrderDO.getProjectId());
        setNullIfPresent(wrapper, ReceiptOrderDO::getProjectNo, updateObj.getProjectNo());
        setNullIfPresent(wrapper, ReceiptOrderDO::getProjectName, updateObj.getProjectName());
        setNullIfPresent(wrapper, ReceiptOrderDO::getPayerName, receiptOrderDO.getPayerName());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptDate, receiptOrderDO.getReceiptDate());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptAmount, receiptOrderDO.getReceiptAmount());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptMethod, receiptOrderDO.getReceiptMethod());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptCertificate, receiptOrderDO.getReceiptCertificate());
        setNullIfPresent(wrapper, ReceiptOrderDO::getReceiptPurpose, receiptOrderDO.getReceiptPurpose());
        setNullIfPresent(wrapper, ReceiptOrderDO::getIsInvoiced, receiptOrderDO.getIsInvoiced());
        setNullIfPresent(wrapper, ReceiptOrderDO::getRemark, receiptOrderDO.getRemark());

        receiptOrderMapper.update(null, wrapper);
    }

    private <T> void setNullIfPresent(LambdaUpdateWrapper<T> wrapper, SFunction<T, ?> column, Object value) {
        wrapper.set(column, value);
    }

    @Override
    public void deleteReceiptOrder(Long id) {
        // 校验存在
        validateReceiptOrderExists(id);
        // 删除
        receiptOrderMapper.deleteById(id);
    }

    @Override
    public void deleteReceiptOrderListByIds(List<Long> ids) {
        // 删除
        receiptOrderMapper.deleteByIds(ids);
    }


    private void validateReceiptOrderExists(Long id) {
        if (receiptOrderMapper.selectById(id) == null) {
            throw exception(RECEIPT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ReceiptOrderDO getReceiptOrder(Long id) {
        return receiptOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReceiptOrderDO> getReceiptOrderPage(ReceiptOrderPageReqVO pageReqVO) {
        return receiptOrderMapper.selectPage(pageReqVO);
    }

}
