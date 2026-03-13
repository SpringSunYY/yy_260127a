import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace SalaryPaymentOrderApi {
  /** 工资付款信息信息 */
  export interface SalaryPaymentOrder {
    id: number; // 编号
    paymentNo?: string; // 付款单号
    salaryId: number; // 工资ID
    salaryName: string; // 工资名称
    workerId: number; // 工人ID
    workerName: string; // 工人名称
    paymentTime?: Dayjs | string; // 付款日期
    paymentAmount?: number; // 付款金额
    paymentMethod?: string; // 付款方式
    paymentCertificate: string; // 付款凭证
    paymentPurpose: string; // 付款事由
    isInvoiced?: string; // 是否开票
    remark: string; // 备注
  }
}

/** 查询工资付款信息分页 */
export function getSalaryPaymentOrderPage(params: PageParam) {
  return requestClient.get<
    PageResult<SalaryPaymentOrderApi.SalaryPaymentOrder>
  >('/biz/salary-payment-order/page', { params });
}

/** 查询工资付款信息详情 */
export function getSalaryPaymentOrder(id: number) {
  return requestClient.get<SalaryPaymentOrderApi.SalaryPaymentOrder>(
    `/biz/salary-payment-order/get?id=${id}`,
  );
}

/** 新增工资付款信息 */
export function createSalaryPaymentOrder(
  data: SalaryPaymentOrderApi.SalaryPaymentOrder,
) {
  return requestClient.post('/biz/salary-payment-order/create', data);
}

/** 修改工资付款信息 */
export function updateSalaryPaymentOrder(
  data: SalaryPaymentOrderApi.SalaryPaymentOrder,
) {
  return requestClient.put('/biz/salary-payment-order/update', data);
}

/** 删除工资付款信息 */
export function deleteSalaryPaymentOrder(id: number) {
  return requestClient.delete(`/biz/salary-payment-order/delete?id=${id}`);
}

/** 批量删除工资付款信息 */
export function deleteSalaryPaymentOrderList(ids: number[]) {
  return requestClient.delete(
    `/biz/salary-payment-order/delete-list?ids=${ids.join(',')}`,
  );
}

/** 导出工资付款信息 */
export function exportSalaryPaymentOrder(params: any) {
  return requestClient.download(
    '/biz/salary-payment-order/export-excel',
    params,
  );
}

/** 导入工资付款信息模版 */
export function importSalaryPaymentOrderTemplate() {
  return requestClient.download(
    '/biz/salary-payment-order/get-import-template',
  );
}

/** 导入工资付款信息 */
export function importSalaryPaymentOrder(file: File) {
  return requestClient.upload('/biz/salary-payment-order/import', { file });
}
