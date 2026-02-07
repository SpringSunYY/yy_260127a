import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace SalaryApi {
  /** 工资信息信息 */
  export interface Salary {
    id: number; // 编号
    workerId: number; // 工人编号
    workerName: string; // 工人姓名
    isSettlement: string; // 是否结算
    settlementTime: Dayjs | string; // 结算日期
    attendanceDays: number; // 出勤天数
    overtimeDays: number; // 加班天数
    laborFeeAmount: number; // 劳务费金额
    overtimeFee: number; // 加班费
    allowanceAmount: number; // 补贴
    subtotalAmount: number; // 小计
    socialInsurance: number; // 社保
    payableAmount: number; // 应发款项
    remark: string; // 备注
  }
}

/** 查询工资信息分页 */
export function getSalaryPage(params: PageParam) {
  return requestClient.get<PageResult<SalaryApi.Salary>>('/biz/salary/page', {
    params,
  });
}

/** 查询工资信息详情 */
export function getSalary(id: number) {
  return requestClient.get<SalaryApi.Salary>(`/biz/salary/get?id=${id}`);
}

/** 新增工资信息 */
export function createSalary(data: SalaryApi.Salary) {
  return requestClient.post('/biz/salary/create', data);
}

/** 修改工资信息 */
export function updateSalary(data: SalaryApi.Salary) {
  return requestClient.put('/biz/salary/update', data);
}

/** 删除工资信息 */
export function deleteSalary(id: number) {
  return requestClient.delete(`/biz/salary/delete?id=${id}`);
}

/** 批量删除工资信息 */
export function deleteSalaryList(ids: number[]) {
  return requestClient.delete(`/biz/salary/delete-list?ids=${ids.join(',')}`);
}

/** 导出工资信息 */
export function exportSalary(params: any) {
  return requestClient.download('/biz/salary/export-excel', params);
}

/** 导入工资信息模版 */
export function importSalaryTemplate() {
  return requestClient.download('/biz/salary/get-import-template');
}

/** 导入工资信息 */
export function importSalary(file: File) {
  return requestClient.upload('/biz/salary/import', {
    file,
  });
}
