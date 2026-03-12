package com.lz.module.biz.service.purchaseOrder;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.framework.common.exception.ServiceException;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.id.IdUtils;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderImportRespVO;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderImportVO;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderPageReqVO;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.purchaseOrder.PurchaseOrderDO;
import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import com.lz.module.biz.dal.dataobject.supplier.SupplierDO;
import com.lz.module.biz.dal.mysql.purchaseOrder.PurchaseOrderMapper;
import com.lz.module.biz.dal.mysql.purchaseOrderDetail.PurchaseOrderDetailMapper;
import com.lz.module.biz.dal.mysql.supplier.SupplierMapper;
import com.lz.module.biz.service.supplier.SupplierService;
import com.lz.module.system.dal.dataobject.user.AdminUserDO;
import com.lz.module.system.dal.mysql.user.AdminUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lz.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.lz.framework.common.util.collection.CollectionUtils.convertList;
import static com.lz.framework.common.util.collection.CollectionUtils.diffList;
import static com.lz.module.biz.enums.ErrorCodeConstants.PURCHASE_ORDER_DETAIL_NOT_DATA;
import static com.lz.module.biz.enums.ErrorCodeConstants.PURCHASE_ORDER_NOT_EXISTS;

/**
 * 采购信息 Service 实现类
 *
 * @author YY
 */
@Service
@Validated
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private PurchaseOrderDetailMapper purchaseOrderDetailMapper;

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private SupplierService supplierService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPurchaseOrder(PurchaseOrderSaveReqVO createReqVO) {
        createReqVO.setOrderNo(IdUtils.generateTimeRandomId());
        //初始化数据
        SupplierDO supplierDO = initPurchaseOrderData(createReqVO);
        // 插入
        PurchaseOrderDO purchaseOrder = BeanUtils.toBean(createReqVO, PurchaseOrderDO.class);
        purchaseOrderMapper.insert(purchaseOrder);

        // 插入子表
        createPurchaseOrderDetailList(purchaseOrder.getId(), createReqVO.getPurchaseOrderDetails());
        //更新应付金额
        supplierDO.setPayableAmount(supplierDO.getPayableAmount().add(purchaseOrder.getTotalAmount()));
        supplierService.updateSupplierAmount(supplierDO);
        // 返回
        return purchaseOrder.getId();
    }

    private SupplierDO initPurchaseOrderData(PurchaseOrderSaveReqVO createReqVO) {
        //校验供应商和采购人
        SupplierDO supplier = supplierService.getSupplier(createReqVO.getSupplierId());
        if (ObjUtil.isNull(supplier)) {
            throw exception(PURCHASE_ORDER_NOT_EXISTS);
        }
        createReqVO.setSupplierName(supplier.getName());
        AdminUserDO user = adminUserMapper.selectById(createReqVO.getPurchaseUserId());
        if (ObjUtil.isNull(user)) {
            throw exception(PURCHASE_ORDER_NOT_EXISTS);
        }
        createReqVO.setPurchaserUserName(user.getNickname());
        //计算总金额和数量
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalQuantity = new BigDecimal(0);
        String orderNo = createReqVO.getOrderNo();
        //拿到所有的采购明细
        List<PurchaseOrderDetailDO> purchaseOrderDetails = createReqVO.getPurchaseOrderDetails();
        if (ArrayUtil.isEmpty(purchaseOrderDetails)) {
            createReqVO.setTotalAmount(totalAmount);
            createReqVO.setTotalQuantity(totalQuantity);
            return supplier;
        }
        for (PurchaseOrderDetailDO detail : purchaseOrderDetails) {

            BigDecimal quantity = detail.getQuantity();
            BigDecimal unitPrice = detail.getUnitPrice();
            if (ObjUtil.isNull(quantity) || ObjUtil.isNull(unitPrice)) {
                throw exception(PURCHASE_ORDER_DETAIL_NOT_DATA);
            }
            detail.setOrderNo(orderNo);
            BigDecimal totalPrice = quantity.multiply(unitPrice);
            detail.setTotalPrice(totalPrice);
            totalAmount = totalAmount.add(totalPrice);
            totalQuantity = totalQuantity.add(quantity);
        }
        createReqVO.setTotalAmount(totalAmount);
        createReqVO.setTotalQuantity(totalQuantity);
        return supplier;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePurchaseOrder(PurchaseOrderSaveReqVO updateReqVO) {
        SupplierDO supplierDO = initPurchaseOrderData(updateReqVO);
        // 校验存在
        PurchaseOrderDO purchaseOrderDO = validatePurchaseOrderExists(updateReqVO.getId());
        // 更新
        PurchaseOrderDO updateObj = BeanUtils.toBean(updateReqVO, PurchaseOrderDO.class);
        updateObj.setOrderNo(null);
        purchaseOrderMapper.updateById(updateObj);
        // 更新子表
        updatePurchaseOrderDetailList(updateReqVO.getId(), updateReqVO.getPurchaseOrderDetails());
        //更新应付金额:当前总金额-旧总金额+供应商金额
        supplierDO.setPayableAmount(supplierDO.getPayableAmount().subtract(purchaseOrderDO.getTotalAmount()).add(updateReqVO.getTotalAmount()));
        supplierService.updateSupplierAmount(supplierDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchaseOrder(Long id) {
        // 校验存在
        PurchaseOrderDO purchaseOrderDO = validatePurchaseOrderExists(id);
        // 删除
        purchaseOrderMapper.deleteById(id);

        // 删除子表
        deletePurchaseOrderDetailByPurchaseId(id);
        //查询到供应商
        SupplierDO supplierDO = supplierService.getSupplier(purchaseOrderDO.getSupplierId());
        if (ObjUtil.isNotNull(supplierDO)) {
            //更新应付金额，减去当前金额
            supplierDO.setPayableAmount(supplierDO.getPayableAmount().subtract(purchaseOrderDO.getTotalAmount()));
            supplierService.updateSupplierAmount(supplierDO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchaseOrderListByIds(List<Long> ids) {
        // 删除
        purchaseOrderMapper.deleteByIds(ids);

        // 删除子表
        deletePurchaseOrderDetailByPurchaseIds(ids);
    }


    private PurchaseOrderDO validatePurchaseOrderExists(Long id) {
        PurchaseOrderDO purchaseOrderDO = purchaseOrderMapper.selectById(id);
        if (purchaseOrderDO == null) {
            throw exception(PURCHASE_ORDER_NOT_EXISTS);
        }
        return purchaseOrderDO;
    }

    @Override
    public PurchaseOrderDO getPurchaseOrder(Long id) {
        return purchaseOrderMapper.selectById(id);
    }

    @Override
    public BigDecimal getPurchaseAmount(PurchaseOrderPageReqVO pageReqVO) {
        return purchaseOrderMapper.getPurchaseAmount(pageReqVO);
    }

    @Override
    public PageResult<PurchaseOrderDO> getPurchaseOrderPage(PurchaseOrderPageReqVO pageReqVO) {
        return purchaseOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public PurchaseOrderImportRespVO importPurchaseOrderList(List<PurchaseOrderImportVO> list) {
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不能为空");
        }
        List<Long> userIds = new ArrayList<>();
        List<Long> supplierIds = new ArrayList<>();
        //首先校验数据
        checkInitData(list, userIds, supplierIds);
        //拿到用户和供应商
        Map<Long, AdminUserDO> userMap = new HashMap<>();
        Map<Long, SupplierDO> supplierMap = new HashMap<>();
        getUserAndSupplier(userIds, userMap, supplierIds, supplierMap);
        //拿到dos
        List<PurchaseOrderDO> purchaseOrderDOS = getPurchaseOrderDOS(list, userMap, supplierMap);
        //创建结果并更新数据
        transactionTemplate.executeWithoutResult(status -> {
            purchaseOrderMapper.insertBatch(purchaseOrderDOS);
            supplierMap.forEach((id, supplierDO) -> supplierService.updateSupplierAmount(supplierDO));
        });
        return PurchaseOrderImportRespVO.builder()
                .message(StrUtil.format("成功导入 {} 个采购信息", purchaseOrderDOS.size())).build();
    }

    private List<PurchaseOrderDO> getPurchaseOrderDOS(List<PurchaseOrderImportVO> list, Map<Long, AdminUserDO> userMap, Map<Long, SupplierDO> supplierMap) {
        ArrayList<PurchaseOrderDO> purchaseOrderDOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            PurchaseOrderImportVO purchaseOrderImportVO = list.get(i);
            AdminUserDO user = userMap.get(purchaseOrderImportVO.getPurchaseUserId());
            if (ObjUtil.isNull(user)) {
                throw new ServiceException(400, StrUtil.format("第{}行采购人ID不存在", index));
            }
            SupplierDO supplier = supplierMap.get(purchaseOrderImportVO.getSupplierId());
            if (ObjUtil.isNull(supplier)) {
                throw new ServiceException(400, StrUtil.format("第{}行供应商ID不存在", index));
            }
            PurchaseOrderDO orderDO = BeanUtils.toBean(purchaseOrderImportVO, PurchaseOrderDO.class);
            orderDO.setSupplierName(supplier.getName());
            orderDO.setPurchaserUserName(user.getNickname());
            orderDO.setOrderNo(IdUtils.generateTimeRandomId());
            purchaseOrderDOS.add(orderDO);

            if (ObjUtil.isNotNull(purchaseOrderImportVO.getTotalAmount())) {
                supplier.setPayableAmount(supplier.getPayableAmount().add(orderDO.getTotalAmount()));
            }
        }
        return purchaseOrderDOS;
    }

    private void getUserAndSupplier(List<Long> userIds, Map<Long, AdminUserDO> userMap, List<Long> supplierIds, Map<Long, SupplierDO> supplierMap) {
        LambdaQueryWrapper<AdminUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AdminUserDO::getId, userIds);
        List<AdminUserDO> adminUserDOS = adminUserMapper.selectList(queryWrapper);
        if (ArrayUtil.isEmpty(adminUserDOS)) {
            throw new ServiceException(400, "用户不存在");
        }
        for (AdminUserDO adminUserDO : adminUserDOS) {
            userMap.put(adminUserDO.getId(), adminUserDO);
        }
        LambdaQueryWrapper<SupplierDO> supplierQueryWrapper = new LambdaQueryWrapper<>();
        supplierQueryWrapper.in(SupplierDO::getId, supplierIds);
        List<SupplierDO> supplierDOS = supplierMapper.selectList(supplierQueryWrapper);
        if (ArrayUtil.isEmpty(supplierDOS)) {
            throw new ServiceException(400, "供应商不存在");
        }
        for (SupplierDO supplierDO : supplierDOS) {
            supplierMap.put(supplierDO.getId(), supplierDO);
        }
    }

    /**
     * 校验数据 初始化校验，不查询数据库
     *
     * @param list        数据
     * @param userIds     用户ID
     * @param supplierIds 供应商ID
     */
    private static void checkInitData(List<PurchaseOrderImportVO> list, List<Long> userIds, List<Long> supplierIds) {
        for (int i = 0; i < list.size(); i++) {
            int index = i + 1;
            PurchaseOrderImportVO purchaseOrderImportVO = list.get(i);
            //采购名称、供应商、采购人、状态不能为空
            if (StrUtil.isEmpty(purchaseOrderImportVO.getName())) {
                throw new ServiceException(400, StrUtil.format("第{}行采购名称不能为空", index));
            }
            if (ObjUtil.isNull(purchaseOrderImportVO.getSupplierId())) {
                throw new ServiceException(400, StrUtil.format("第{}行供应商ID不能为空", index));
            }
            if (ObjUtil.isNull(purchaseOrderImportVO.getPurchaseUserId())) {
                throw new ServiceException(400, StrUtil.format("第{}行采购人ID不能为空", index));
            }
            if (StrUtil.isEmpty(purchaseOrderImportVO.getOrderStatus())) {
                throw new ServiceException(400, StrUtil.format("第{}行采购状态不能为空", index));
            }
            userIds.add(purchaseOrderImportVO.getPurchaseUserId());
            supplierIds.add(purchaseOrderImportVO.getSupplierId());
        }
    }

    // ==================== 子表（采购明细） ====================

    @Override
    public List<PurchaseOrderDetailDO> getPurchaseOrderDetailListByPurchaseId(Long purchaseId) {
        return purchaseOrderDetailMapper.selectListByPurchaseId(purchaseId);
    }

    private void createPurchaseOrderDetailList(Long purchaseId, List<PurchaseOrderDetailDO> list) {
        list.forEach(o -> o.setPurchaseId(purchaseId).clean());
        purchaseOrderDetailMapper.insertBatch(list);
    }

    private void updatePurchaseOrderDetailList(Long purchaseId, List<PurchaseOrderDetailDO> list) {
        list.forEach(o -> o.setPurchaseId(purchaseId).clean());
        List<PurchaseOrderDetailDO> oldList = purchaseOrderDetailMapper.selectListByPurchaseId(purchaseId);
        List<List<PurchaseOrderDetailDO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
        });

        // 第二步，批量添加、修改、删除
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            purchaseOrderDetailMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            purchaseOrderDetailMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            purchaseOrderDetailMapper.deleteByIds(convertList(diffList.get(2), PurchaseOrderDetailDO::getId));
        }
    }

    private void deletePurchaseOrderDetailByPurchaseId(Long purchaseId) {
        purchaseOrderDetailMapper.deleteByPurchaseId(purchaseId);
    }

    private void deletePurchaseOrderDetailByPurchaseIds(List<Long> purchaseIds) {
        purchaseOrderDetailMapper.deleteByPurchaseIds(purchaseIds);
    }

}
