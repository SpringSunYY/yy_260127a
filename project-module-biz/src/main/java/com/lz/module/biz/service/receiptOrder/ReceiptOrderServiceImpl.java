package com.lz.module.biz.service.receiptOrder;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.id.IdUtils;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderImportVO;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderImportRespVO;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderPageReqVO;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import com.lz.module.biz.dal.dto.ProjectCommonDto;
import com.lz.module.biz.dal.mysql.customer.CustomerMapper;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;
import com.lz.module.biz.dal.mysql.receiptOrder.ReceiptOrderMapper;
import com.lz.module.biz.enums.BizReceiptProjectTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.module.biz.enums.ErrorCodeConstants.*;

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

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Long createReceiptOrder(ReceiptOrderSaveReqVO createReqVO) {
        // 插入
        ReceiptOrderDO receiptOrder = BeanUtils.toBean(createReqVO, ReceiptOrderDO.class);
        //初始化信息
        judgeProject(receiptOrder);
        //判断客户是否存在
        judgeCustomer(receiptOrder);
        receiptOrder.setReceiptNo(IdUtils.generateTimeRandomId());
        receiptOrderMapper.insert(receiptOrder);

        // 返回
        return receiptOrder.getId();
    }

    private CustomerDO judgeCustomer(ReceiptOrderDO receiptOrder) {
        CustomerDO customerDO = customerMapper.selectById(receiptOrder.getCustomerId());
        if (ObjUtil.isNull(customerDO)) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        receiptOrder.setCustomerName(customerDO.getName());
        return customerDO;
    }

    private void judgeProject(ReceiptOrderDO receiptOrder) {
        //判断项目类型是什么
        if (StrUtil.equals(receiptOrder.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_2.getStatus())) {
            //查询项目是否存在
            ProjectOtherDO projectOtherDO = projectOtherMapper.selectById(receiptOrder.getProjectId());
            if (ObjUtil.isNull(projectOtherDO)) {
                throw exception(PROJECT_OTHER_NOT_EXISTS);
            }
            receiptOrder.setProjectName(projectOtherDO.getProjectName());
            receiptOrder.setProjectNo(null);
            receiptOrder.setFiscalYear(null);
            receiptOrder.setProjectScatteredType(null);
        } else {
            receiptOrder.setProjectType(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus());
            //默认就是这个
            ProjectDO projectDO = projectMapper.selectById(receiptOrder.getProjectId());
            if (ObjUtil.isNull(projectDO)) {
                throw exception(PROJECT_NOT_EXISTS);
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
        ReceiptOrderDO receiptOrderDO = validateReceiptOrderExists(updateReqVO.getId());
        // 更新
        ReceiptOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReceiptOrderDO.class);
        judgeProject(updateObj);
        judgeCustomer(updateObj);
        //如果客户信息不一致
        if (!receiptOrderDO.getCustomerId().equals(updateObj.getCustomerId())) {
            throw exception(RECEIPT_ORDER_CUSTOMER_CANNOT_UPDATE);
        }
        receiptOrderMapper.updateById(updateObj);
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


    private ReceiptOrderDO validateReceiptOrderExists(Long id) {
        ReceiptOrderDO receiptOrderDO = receiptOrderMapper.selectById(id);
        if (receiptOrderDO == null) {
            throw exception(RECEIPT_ORDER_NOT_EXISTS);
        }
        return receiptOrderDO;
    }

    @Override
    public ReceiptOrderDO getReceiptOrder(Long id) {
        return receiptOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReceiptOrderDO> getReceiptOrderPage(ReceiptOrderPageReqVO pageReqVO) {
        return receiptOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public BigDecimal getReceiptOrderAmount(ReceiptOrderPageReqVO pageReqVO) {
        return receiptOrderMapper.getReceiptOrderAmount(pageReqVO);
    }

    @Override
    public ReceiptOrderImportRespVO importReceiptOrderList(List<ReceiptOrderImportVO> list) {
        if (ArrayUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        //校验数据
        for (int i = 0; i < list.size(); i++) {
            //校验数据,收款单号、收款类型、收款方、收款金额、收款方式、是否开票
            ReceiptOrderImportVO orderImportExcelVO = list.get(i);
            int index = i + 1;
            if (StrUtil.isBlank(orderImportExcelVO.getReceiptType())) {
                throw new ServiceException(400, "第" + index + "行收款类型不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getCustomerId())) {
                throw new ServiceException(400, "第" + index + "行客户ID不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getReceiptAmount())) {
                throw new ServiceException(400, "第" + index + "行收款金额不能为空");
            }
            if (StrUtil.isBlank(orderImportExcelVO.getReceiptMethod())) {
                throw new ServiceException(400, "第" + index + "行收款方式不能为空");
            }
            if (StrUtil.isBlank(orderImportExcelVO.getIsInvoiced())) {
                throw new ServiceException(400, "第" + index + "行是否开票不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getProjectType())) {
                throw new ServiceException(400, "第" + index + "行项目类型不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getProjectId())){
                throw new ServiceException(400, "第" + index + "行项目ID不能为空");
            }
        }
        //查询到所有的项目，其他的和工程的
        List<Long> projectIds = new ArrayList<>();
        List<Long> projectOtherIds = new ArrayList<>();
        List<Long> customerIds = new ArrayList<>();
        for (ReceiptOrderImportVO item : list) {
            if (StrUtil.equals(item.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus())) {
                projectIds.add(item.getProjectId());
            } else {
                projectOtherIds.add(item.getProjectId());
            }
            customerIds.add(item.getCustomerId());
        }

        List<ProjectDO> projectDOS = new ArrayList<>();
        if (!projectIds.isEmpty()) {
            projectDOS = projectMapper.selectByIds(projectIds);
        }

        List<ProjectOtherDO> projectOtherDOS = new ArrayList<>();
        if (!projectOtherIds.isEmpty()) {
            projectOtherDOS = projectOtherMapper.selectByIds(projectOtherIds);
        }
        //创建一个map，key为项目类型-项目id，value为项目信息
        Map<String, ProjectCommonDto> projectMap = new HashMap<>();
        projectDOS.forEach(item -> {
            projectMap.put(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus() + "-" + item.getId(),
                    new ProjectCommonDto(item.getId(), item.getProjectNo(), item.getName(), item.getEngineeringType(), item.getFiscalYear()));
        });
        projectOtherDOS.forEach(item -> {
            projectMap.put(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_2.getStatus() + "-" + item.getId(),
                    new ProjectCommonDto(item.getId(), null, item.getProjectName(), null, null));
        });
        Map<Long, CustomerDO> customerMap = new HashMap<>();
        customerIds.forEach(item -> {
            CustomerDO customerDO = customerMapper.selectById(item);
            customerMap.put(customerDO.getId(), customerDO);
        });
        ArrayList<ReceiptOrderDO> dos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ReceiptOrderDO receiptOrderDO = new ReceiptOrderDO();
            ReceiptOrderImportVO orderImportExcelVO = list.get(i);
            BeanUtils.copyProperties(orderImportExcelVO, receiptOrderDO);
            if (StrUtil.isEmpty(orderImportExcelVO.getProjectType())) {
                throw new ServiceException(400, "第" + (i + 1) + "行项目类型不能为空");
            }
            ProjectCommonDto projectCommonDto = projectMap.get(orderImportExcelVO.getProjectType() + "-" + orderImportExcelVO.getProjectId());
            if (ObjUtil.isNull(projectCommonDto)) {
                throw new ServiceException(400, "第" + (i + 1) + "行项目不存在,请检查项目编号与类型是否对应");
            }
            receiptOrderDO.setProjectName(projectCommonDto.getName());

            if (orderImportExcelVO.getProjectType().equals(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus())) {
                receiptOrderDO.setProjectNo(projectCommonDto.getProjectNo());
                receiptOrderDO.setFiscalYear(projectCommonDto.getFiscalYear());
                receiptOrderDO.setProjectScatteredType(projectCommonDto.getEngineeringType());
            }
            //判断 客户
            CustomerDO customerDO = customerMap.get(orderImportExcelVO.getCustomerId());
            if (ObjUtil.isNull(customerDO)){
                throw new ServiceException(400, "第" + (i + 1) + "行客户不存在,请检查客户ID是否正确");
            }
            receiptOrderDO.setCustomerName(customerDO.getName());
            receiptOrderDO.setReceiptNo(IdUtils.generateTimeRandomId());
            dos.add(receiptOrderDO);
        }
        receiptOrderMapper.insertBatch(dos);
        return ReceiptOrderImportRespVO.builder()
                .message("导入成功")
                .build();
    }

}
