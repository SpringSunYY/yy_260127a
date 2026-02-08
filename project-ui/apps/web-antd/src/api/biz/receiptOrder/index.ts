import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace ReceiptOrderApi {
  /** 收款信息信息 */
  export interface ReceiptOrder {
    id: number; // 编号
    receiptNo?: string; // 收款单号
    receiptType?: string; // 收款类型
    projectType: string; // 项目类型
    projectScatteredType: string; // 工程类型
    fiscalYear: number; // 财年
    projectId: number; // 项目ID
    projectNo: string; // 项目编号
    projectName: string; // 项目名称
    payerName?: string; // 付款方
    receiptDate?: Dayjs | string; // 收款日期
    receiptAmount?: number; // 收款金额
    receiptMethod?: string; // 收款方式
    receiptCertificate: string; // 收款凭证
    receiptPurpose: string; // 收款事由
    isInvoiced?: string; // 是否开票
    remark: string; // 备注
  }
}

/** 查询收款信息分页 */
export function getReceiptOrderPage(params: PageParam) {
  return requestClient.get<PageResult<ReceiptOrderApi.ReceiptOrder>>(
    '/biz/receipt-order/page',
    { params },
  );
}

/** 查询收款信息详情 */
export function getReceiptOrder(id: number) {
  return requestClient.get<ReceiptOrderApi.ReceiptOrder>(
    `/biz/receipt-order/get?id=${id}`,
  );
}

/** 新增收款信息 */
export function createReceiptOrder(data: ReceiptOrderApi.ReceiptOrder) {
  return requestClient.post('/biz/receipt-order/create', data);
}

/** 修改收款信息 */
export function updateReceiptOrder(data: ReceiptOrderApi.ReceiptOrder) {
  return requestClient.put('/biz/receipt-order/update', data);
}

/** 删除收款信息 */
export function deleteReceiptOrder(id: number) {
  return requestClient.delete(`/biz/receipt-order/delete?id=${id}`);
}

/** 批量删除收款信息 */
export function deleteReceiptOrderList(ids: number[]) {
  return requestClient.delete(
    `/biz/receipt-order/delete-list?ids=${ids.join(',')}`,
  );
}

/** 导出收款信息 */
export function exportReceiptOrder(params: any) {
  return requestClient.download('/biz/receipt-order/export-excel', params);
}

/** 导入收款信息模版 */
export function importReceiptOrderTemplate() {
  return requestClient.download('/biz/receipt-order/get-import-template');
}

/** 导入收款信息 */
export function importReceiptOrder(file: File) {
  return requestClient.upload('/biz/receipt-order/import', { file });
}
