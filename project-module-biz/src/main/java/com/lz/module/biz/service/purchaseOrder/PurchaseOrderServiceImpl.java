package com.lz.module.biz.service.purchaseOrder;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.common.util.object.BeanUtils;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderPageReqVO;
import com.lz.module.biz.controller.admin.purchaseOrder.vo.PurchaseOrderSaveReqVO;
import com.lz.module.biz.dal.dataobject.purchaseOrder.PurchaseOrderDO;
import com.lz.module.biz.dal.dataobject.purchaseOrderDetail.PurchaseOrderDetailDO;
import com.lz.module.biz.dal.mysql.purchaseOrder.PurchaseOrderMapper;
import com.lz.module.biz.dal.mysql.purchaseOrderDetail.PurchaseOrderDetailMapper;
import com.lz.module.biz.service.rawMaterials.RawMaterialsService;
import com.lz.module.biz.service.supplier.SupplierService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

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
    private SupplierService supplierService;

    @Resource
    private RawMaterialsService rawMaterialsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPurchaseOrder(PurchaseOrderSaveReqVO createReqVO) {
        //初始化数据
        initPurchaseOrderData(createReqVO);
        // 插入
        PurchaseOrderDO purchaseOrder = BeanUtils.toBean(createReqVO, PurchaseOrderDO.class);
        purchaseOrderMapper.insert(purchaseOrder);

        // 插入子表
        createPurchaseOrderDetailList(purchaseOrder.getId(), createReqVO.getPurchaseOrderDetails());
        // 返回
        return purchaseOrder.getId();
    }

    private void initPurchaseOrderData(PurchaseOrderSaveReqVO createReqVO) {
        //计算总金额和数量
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalQuantity = new BigDecimal(0);
        String orderNo = createReqVO.getOrderNo();
        //拿到所有的采购明细
        List<PurchaseOrderDetailDO> purchaseOrderDetails = createReqVO.getPurchaseOrderDetails();
        if (ArrayUtil.isEmpty(purchaseOrderDetails)) {
            createReqVO.setTotalAmount(totalAmount);
            createReqVO.setTotalQuantity(totalQuantity);
            return;
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePurchaseOrder(PurchaseOrderSaveReqVO updateReqVO) {
        initPurchaseOrderData(updateReqVO);
        // 校验存在
        validatePurchaseOrderExists(updateReqVO.getId());
        // 更新
        PurchaseOrderDO updateObj = BeanUtils.toBean(updateReqVO, PurchaseOrderDO.class);
        purchaseOrderMapper.updateById(updateObj);

        // 更新子表
        updatePurchaseOrderDetailList(updateReqVO.getId(), updateReqVO.getPurchaseOrderDetails());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchaseOrder(Long id) {
        // 校验存在
        validatePurchaseOrderExists(id);
        // 删除
        purchaseOrderMapper.deleteById(id);

        // 删除子表
        deletePurchaseOrderDetailByPurchaseId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchaseOrderListByIds(List<Long> ids) {
        // 删除
        purchaseOrderMapper.deleteByIds(ids);

        // 删除子表
        deletePurchaseOrderDetailByPurchaseIds(ids);
    }


    private void validatePurchaseOrderExists(Long id) {
        if (purchaseOrderMapper.selectById(id) == null) {
            throw exception(PURCHASE_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public PurchaseOrderDO getPurchaseOrder(Long id) {
        return purchaseOrderMapper.selectById(id);
    }

    @Override
    public PageResult<PurchaseOrderDO> getPurchaseOrderPage(PurchaseOrderPageReqVO pageReqVO) {
        return purchaseOrderMapper.selectPage(pageReqVO);
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
