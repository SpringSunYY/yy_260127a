import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace PurchaseOrderApi {
  /** 采购明细信息 */
  export interface PurchaseOrderDetail {
    id: number; // 编号
    orderNo?: string; // 采购单号
    purchaseId?: number; // 采购单编号
    materialId: number | string; // 材料ID
    materialName?: string; // 材料名称
    materialSpec?: string; // 规格型号
    quantity?: number | string; // 采购数量
    unit?: string; // 计量单位
    unitPrice?: number | string; // 采购单价
    totalPrice?: number; // 小计金额
    remark?: string; // 备注
  }

  /** 采购信息信息 */
  export interface PurchaseOrder {
    id: number; // 编号
    orderNo?: string; // 采购单号
    name?: string; // 采购名称
    supplierId: number; // 供应商
    supplierName?: string; // 供应商名称
    purchaserName?: string; // 采购人
    totalAmount?: number; // 采购金额
    totalQuantity?: number; // 采购数量
    expectedTime: Dayjs | string; // 期望到货日期
    actualTime: Dayjs | string; // 实际到货日期
    orderStatus?: string; // 采购状态
    remark: string; // 备注
    PurchaseOrderDetails?: PurchaseOrderDetail[];
  }
}

/** 查询采购信息分页 */
export function getPurchaseOrderPage(params: PageParam) {
  return requestClient.get<PageResult<PurchaseOrderApi.PurchaseOrder>>(
    '/biz/purchase-order/page',
    { params },
  );
}

/** 查询采购信息详情 */
export function getPurchaseOrder(id: number) {
  return requestClient.get<PurchaseOrderApi.PurchaseOrder>(
    `/biz/purchase-order/get?id=${id}`,
  );
}

/** 新增采购信息 */
export function createPurchaseOrder(data: PurchaseOrderApi.PurchaseOrder) {
  return requestClient.post('/biz/purchase-order/create', data);
}

/** 修改采购信息 */
export function updatePurchaseOrder(data: PurchaseOrderApi.PurchaseOrder) {
  return requestClient.put('/biz/purchase-order/update', data);
}

/** 删除采购信息 */
export function deletePurchaseOrder(id: number) {
  return requestClient.delete(`/biz/purchase-order/delete?id=${id}`);
}

/** 批量删除采购信息 */
export function deletePurchaseOrderList(ids: number[]) {
  return requestClient.delete(
    `/biz/purchase-order/delete-list?ids=${ids.join(',')}`,
  );
}

/** 导出采购信息 */
export function exportPurchaseOrder(params: any) {
  return requestClient.download('/biz/purchase-order/export-excel', params);
}

// ==================== 子表（采购明细） ====================

/** 获得采购明细列表 */
export function getPurchaseOrderDetailListByPurchaseId(purchaseId: number) {
  return requestClient.get<PurchaseOrderApi.PurchaseOrderDetail[]>(
    `/biz/purchase-order/purchase-order-detail/list-by-purchase-id?purchaseId=${purchaseId}`,
  );
}
