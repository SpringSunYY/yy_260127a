package com.lz.module.biz.service.paymentOrder;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportExcelVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderImportRespVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderPageReqVO;
import com.lz.module.biz.controller.admin.paymentOrder.vo.PaymentOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.customer.CustomerDO;
import com.lz.module.biz.dal.dataobject.paymentOrder.PaymentOrderDO;
import com.lz.module.biz.dal.dataobject.project.ProjectDO;
import com.lz.module.biz.dal.dataobject.projectOther.ProjectOtherDO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.dataobject.worker.WorkerDO;
import com.lz.module.biz.dal.dto.PaymentOrderPayeeDto;
import com.lz.module.biz.dal.dto.ProjectCommonDto;
import com.lz.module.biz.dal.mysql.customer.CustomerMapper;
import com.lz.module.biz.dal.mysql.paymentOrder.PaymentOrderMapper;
import com.lz.module.biz.dal.mysql.project.ProjectMapper;
import com.lz.module.biz.dal.mysql.projectOther.ProjectOtherMapper;
import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;
import com.lz.module.biz.dal.mysql.worker.WorkerMapper;
import com.lz.module.biz.enums.BizPaymentPayeeTypeEnum;
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
 * 付款信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectOtherMapper projectOtherMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private WorkerMapper workerMapper;

    @Override
    public Long createPaymentOrder(PaymentOrderSaveReqVO createReqVO) {
        // 插入
        PaymentOrderDO paymentOrder = BeanUtils.toBean(createReqVO, PaymentOrderDO.class);
        initPaymentOrder(paymentOrder);
        paymentOrderMapper.insert(paymentOrder);

        // 返回
        return paymentOrder.getId();
    }

    private void initPaymentOrder(PaymentOrderDO orderDO) {
        judgePaymentProject(orderDO);
        judgePaymentPayeeType(orderDO);
    }

    private void judgePaymentPayeeType(PaymentOrderDO orderDO) {
        if (StrUtil.isEmpty(orderDO.getPayeeType())) {
            return;
        }
        //如果是客户
        if (StrUtil.equals(orderDO.getPayeeType(), BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_3.getStatus())) {
            CustomerDO customerDO = customerMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(customerDO)) {
                throw exception(CUSTOMER_NOT_EXISTS);
            }
            orderDO.setPayeeName(customerDO.getName());
        } else if (StrUtil.equals(orderDO.getPayeeType(), BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_2.getStatus())) {
            SupplierDO supplierDO = supplierMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(supplierDO)) {
                throw exception(SUPPLIER_NOT_EXISTS);
            }
            orderDO.setPayeeName(supplierDO.getName());
        } else {
            orderDO.setPayeeType(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_1.getStatus());
            WorkerDO workerDO = workerMapper.selectById(orderDO.getPayeeId());
            if (ObjUtil.isNull(workerDO)) {
                throw exception(WORKER_NOT_EXISTS);
            }
            orderDO.setPayeeName(workerDO.getWorkerName());
        }
    }

    private void judgePaymentProject(PaymentOrderDO orderDO) {
        //首先判断是否选择了项目类型
        if (StrUtil.isEmpty(orderDO.getProjectType())) {
            return;
        }
        //判断项目类型是什么
        if (StrUtil.equals(orderDO.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_2.getStatus())) {
            //查询项目是否存在
            ProjectOtherDO projectOtherDO = projectOtherMapper.selectById(orderDO.getProjectId());
            if (ObjUtil.isNull(projectOtherDO)) {
                throw exception(PROJECT_OTHER_NOT_EXISTS);
            }
            orderDO.setProjectName(projectOtherDO.getProjectName());
        } else {
            orderDO.setProjectType(BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus());
            //默认就是这个
            ProjectDO projectDO = projectMapper.selectById(orderDO.getProjectId());
            if (ObjUtil.isNull(projectDO)) {
                throw exception(PROJECT_NOT_EXISTS);
            }
            orderDO.setProjectName(projectDO.getName());
        }
    }

    @Override
    public void updatePaymentOrder(PaymentOrderSaveReqVO updateReqVO) {
        // 校验存在
        validatePaymentOrderExists(updateReqVO.getId());
        // 更新
        PaymentOrderDO updateObj = BeanUtils.toBean(updateReqVO, PaymentOrderDO.class);
        initPaymentOrder(updateObj);
        paymentOrderMapper.updateById(updateObj);
    }

    @Override
    public void deletePaymentOrder(Long id) {
        // 校验存在
        validatePaymentOrderExists(id);
        // 删除
        paymentOrderMapper.deleteById(id);
    }

    @Override
    public void deletePaymentOrderListByIds(List<Long> ids) {
        // 删除
        paymentOrderMapper.deleteByIds(ids);
    }


    private void validatePaymentOrderExists(Long id) {
        if (paymentOrderMapper.selectById(id) == null) {
            throw exception(PAYMENT_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public PaymentOrderDO getPaymentOrder(Long id) {
        return paymentOrderMapper.selectById(id);
    }

    @Override
    public PageResult<PaymentOrderDO> getPaymentOrderPage(PaymentOrderPageReqVO pageReqVO) {
        return paymentOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public BigDecimal getPaymentOrderAmount(PaymentOrderPageReqVO pageReqVO) {
        return paymentOrderMapper.getPaymentOrderAmount(pageReqVO);
    }

    @Override
    public PaymentOrderImportRespVO importPaymentOrderList(List<PaymentOrderImportExcelVO> list) {
        //首先校验
        judgeImportData(list);
        //查询到对应的数据库数据
        Map<String, ProjectCommonDto> projectMap = initImportProjectData(list);
        Map<String, PaymentOrderPayeeDto> payeeMap = initImportPayeeData(list);
        //校验数据并转换为do
        List<PaymentOrderDO> paymentOrderDOS = initImportData(list, projectMap, payeeMap);
        paymentOrderMapper.insertBatch(paymentOrderDOS);
        return PaymentOrderImportRespVO.builder().message("导入成功，成功导入" + paymentOrderDOS.size() + "条数据").build();
    }

    private List<PaymentOrderDO> initImportData(List<PaymentOrderImportExcelVO> list, Map<String, ProjectCommonDto> projectMap, Map<String, PaymentOrderPayeeDto> payeeMap) {
        List<PaymentOrderDO> paymentOrderDOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            PaymentOrderDO paymentOrderDO = new PaymentOrderDO();
            PaymentOrderImportExcelVO paymentOrderImportExcelVO = list.get(i);
            BeanUtils.copyProperties(paymentOrderImportExcelVO, paymentOrderDO);
            //如果有项目
            if (ObjUtil.isNotNull(paymentOrderImportExcelVO.getProjectId())) {
                if (StrUtil.isEmpty(paymentOrderImportExcelVO.getProjectType())) {
                    throw new ServiceException(400, "第" + index + "行项目类型不能为空，因为填写了项目ID");
                }
                ProjectCommonDto projectCommonDto = projectMap.get(paymentOrderImportExcelVO.getProjectType() + "-" + paymentOrderImportExcelVO.getProjectId());
                if (ObjUtil.isNull(projectCommonDto)) {
                    throw new ServiceException(400, "第" + index + "行项目不存在,请检查项目编号与类型是否对应");
                }
                paymentOrderDO.setProjectId(projectCommonDto.getId());
                paymentOrderDO.setProjectName(projectCommonDto.getName());
            }
            //如果有收款对象
            if (ObjUtil.isNotNull(paymentOrderImportExcelVO.getPayeeId())) {
                if (StrUtil.isEmpty(paymentOrderImportExcelVO.getPayeeType())) {
                    throw new ServiceException(400, "第" + index + "行收款对象类型不能为空，因为填写了收款对象ID");
                }
                PaymentOrderPayeeDto paymentOrderPayeeDto = payeeMap.get(paymentOrderImportExcelVO.getPayeeType() + "-" + paymentOrderImportExcelVO.getPayeeId());
                if (ObjUtil.isNull(paymentOrderPayeeDto)) {
                    throw new ServiceException(400, "第" + index + "行收款对象不存在,请检查收款对象编号与类型是否对应");
                }
                paymentOrderDO.setPayeeId(paymentOrderPayeeDto.getId());
                paymentOrderDO.setPayeeName(paymentOrderPayeeDto.getName());
            }
            paymentOrderDOList.add(paymentOrderDO);
        }
        return paymentOrderDOList;
    }

    private Map<String, PaymentOrderPayeeDto> initImportPayeeData(List<PaymentOrderImportExcelVO> list) {
        List<Long> customerIds = new ArrayList<>();
        List<Long> supplierIds = new ArrayList<>();
        List<Long> workerIds = new ArrayList<>();
        for (PaymentOrderImportExcelVO item : list) {
            if (StrUtil.isEmpty(item.getPayeeType())) {
                continue;
            }
            if (StrUtil.equals(item.getPayeeType(), BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_3.getStatus())) {
                customerIds.add(item.getPayeeId());
            } else if (StrUtil.equals(item.getPayeeType(), BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_2.getStatus())) {
                supplierIds.add(item.getPayeeId());
            } else {
                workerIds.add(item.getPayeeId());
            }
        }
        List<CustomerDO> customerDOS = new ArrayList<>();
        if (!customerIds.isEmpty()) {
            customerDOS = customerMapper.selectByIds(customerIds);
        }
        List<SupplierDO> supplierDOS = new ArrayList<>();
        if (!supplierIds.isEmpty()) {
            supplierDOS = supplierMapper.selectByIds(supplierIds);
        }
        List<WorkerDO> workerDOS = new ArrayList<>();
        if (!workerIds.isEmpty()) {
            workerDOS = workerMapper.selectByIds(workerIds);
        }
        //创建一个map，key为项目类型-项目id，value为付款对象信息
        Map<String, PaymentOrderPayeeDto> payeeMap = new HashMap<>();
        customerDOS.forEach(item -> {
            payeeMap.put(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_3.getStatus() + "-" + item.getId(), PaymentOrderPayeeDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .build());
        });
        supplierDOS.forEach(item -> {
            payeeMap.put(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_2.getStatus() + "-" + item.getId(), PaymentOrderPayeeDto.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .build());
        });
        workerDOS.forEach(item -> {
            payeeMap.put(BizPaymentPayeeTypeEnum.BIZ_PAYMENT_PAYEE_TYPE_1.getStatus() + "-" + item.getId(), PaymentOrderPayeeDto.builder()
                    .id(item.getId())
                    .name(item.getWorkerName())
                    .build());
        });
        return payeeMap;
    }

    private Map<String, ProjectCommonDto> initImportProjectData(List<PaymentOrderImportExcelVO> list) {
        //查询到所有的项目，其他的和工程的
        List<Long> projectIds = new ArrayList<>();
        List<Long> projectOtherIds = new ArrayList<>();
        for (PaymentOrderImportExcelVO item : list) {
            if (ObjUtil.isNull(item.getProjectId())) {
                continue;
            }
            if (StrUtil.equals(item.getProjectType(), BizReceiptProjectTypeEnum.BIZ_RECEIPT_PROJECT_TYPE_1.getStatus())) {
                projectIds.add(item.getProjectId());
            } else {
                projectOtherIds.add(item.getProjectId());
            }
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
        return projectMap;
    }

    private static void judgeImportData(List<PaymentOrderImportExcelVO> list) {
        if (ArrayUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        for (int i = 0; i < list.size(); i++) {
            //是否开票、付款方式、付款单号、付款金额、付款时间、收款对象、是否开票不能为空
            PaymentOrderImportExcelVO orderImportExcelVO = list.get(i);
            int index = i + 1;
            if (StrUtil.isEmpty(orderImportExcelVO.getIsInvoiced())) {
                throw new ServiceException(400, "第" + index + "行数据，是否开票不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getPaymentMethod())) {
                throw new ServiceException(400, "第" + index + "行数据，付款方式不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getPaymentNo())) {
                throw new ServiceException(400, "第" + index + "行数据，收款单号不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getPaymentAmount())) {
                throw new ServiceException(400, "第" + index + "行数据，付款金额不能为空");
            }
            if (ObjUtil.isNull(orderImportExcelVO.getPaymentTime())) {
                throw new ServiceException(400, "第" + index + "行数据，付款时间不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getPayeeType())) {
                throw new ServiceException(400, "第" + index + "行数据，收款对象类型不能为空");
            }
            if (StrUtil.isEmpty(orderImportExcelVO.getIsInvoiced())) {
                throw new ServiceException(400, "第" + index + "行数据，是否开票不能为空");
            }
        }
    }

}
