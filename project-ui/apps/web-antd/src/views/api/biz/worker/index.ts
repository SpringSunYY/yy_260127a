import type { PageParam, PageResult } from '@vben/request';
import type { Dayjs } from 'dayjs';

import { requestClient } from '#/api/request';

export namespace WorkerApi {
  /** 工人信息信息 */
  export interface Worker {
    id: number; // 编号
    workerName?: string; // 工人姓名
    gender: string; // 性别
    idCardNo: string; // 身份证号
    phone: string; // 联系电话
    workerType?: string; // 工人类型
    workType: string; // 工种
    dailySalary: number; // 日薪
    monthlySalary: number; // 月薪
    salaryDesc: string; // 薪资说明
    skillLevel: string; // 技能等级
    status?: string; // 状态
    remark: string; // 备注
  }
}

/** 查询工人信息分页 */
export function getWorkerPage(params: PageParam) {
  return requestClient.get<PageResult<WorkerApi.Worker>>('/biz/worker/page', { params });
}

/** 查询工人信息详情 */
export function getWorker(id: number) {
  return requestClient.get<WorkerApi.Worker>(`/biz/worker/get?id=${id}`);
}

/** 新增工人信息 */
export function createWorker(data: WorkerApi.Worker) {
  return requestClient.post('/biz/worker/create', data);
}

/** 修改工人信息 */
export function updateWorker(data: WorkerApi.Worker) {
  return requestClient.put('/biz/worker/update', data);
}

/** 删除工人信息 */
export function deleteWorker(id: number) {
  return requestClient.delete(`/biz/worker/delete?id=${id}`);
}

/** 批量删除工人信息 */
export function deleteWorkerList(ids: number[]) {
  return requestClient.delete(`/biz/worker/delete-list?ids=${ids.join(',')}`)
}

/** 导出工人信息 */
export function exportWorker(params: any) {
  return requestClient.download('/biz/worker/export-excel', params);
}
