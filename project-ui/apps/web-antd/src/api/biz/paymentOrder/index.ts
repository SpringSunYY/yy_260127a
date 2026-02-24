import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace PaymentOrderApi {
  /** 付款信息信息 */
  export interface PaymentOrder {
    id: number; // 编号
    paymentNo?: string; // 付款单号
    projectId: number; // 项目ID
    projectType: string; // 项目类型
    projectName: string; // 项目名称
    payeeType?: string; // 收款对象类型
    payeeId: number; // 收款对象ID
    payeeName: string; // 收款对象名称
    paymentTime?: Dayjs | string; // 付款日期
    paymentAmount?: number; // 付款金额
    paymentMethod?: string; // 付款方式
    paymentCertificate: string; // 付款凭证
    paymentPurpose: string; // 付款事由
    isInvoiced?: string; // 是否开票
    remark: string; // 备注
  }
}

/** 查询付款信息分页 */
export function getPaymentOrderPage(params: PageParam) {
  return requestClient.get<PageResult<PaymentOrderApi.PaymentOrder>>(
    '/biz/payment-order/page',
    { params },
  );
}

/** 查询付款信息详情 */
export function getPaymentOrder(id: number) {
  return requestClient.get<PaymentOrderApi.PaymentOrder>(
    `/biz/payment-order/get?id=${id}`,
  );
}

/** 新增付款信息 */
export function createPaymentOrder(data: PaymentOrderApi.PaymentOrder) {
  return requestClient.post('/biz/payment-order/create', data);
}

/** 修改付款信息 */
export function updatePaymentOrder(data: PaymentOrderApi.PaymentOrder) {
  return requestClient.put('/biz/payment-order/update', data);
}

/** 删除付款信息 */
export function deletePaymentOrder(id: number) {
  return requestClient.delete(`/biz/payment-order/delete?id=${id}`);
}

/** 批量删除付款信息 */
export function deletePaymentOrderList(ids: number[]) {
  return requestClient.delete(
    `/biz/payment-order/delete-list?ids=${ids.join(',')}`,
  );
}

/** 导出付款信息 */
export function exportPaymentOrder(params: any) {
  return requestClient.download('/biz/payment-order/export-excel', params);
}
