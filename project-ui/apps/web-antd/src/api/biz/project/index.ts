import type { Dayjs } from 'dayjs';

import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace ProjectApi {
  /** 项目信息信息 */
  export interface Project {
    id: number; // 编号
    projectNo?: string; // 项目编号
    name?: string; // 项目名称
    projectType?: string; // 项目类型
    contractNumber: string; // 合同编号
    engineeringType?: string; // 工程类型
    isPmc?: string; // 属于PMC
    customerId: number; // 服务商编号
    customerName: string; // 服务商名称
    deliverTime: Dayjs | string; // 交底时间
    fiscalYear: number; // 财年
    completedTime: Dayjs | string; // 完工移交时间
    visaNum: number; // 签证数
    isCompletedFile: string; // 竣工资料
    projectProgress?: string; // 工程阶段
    isVerification: string; // 现场核销
    isCompleted: string; // 竣工图
    isDeterminedQuantity: string; // 竣工工程确定量
    isMaterialVerification: string; // 材料核销
    isSettlementFile: string; // 结算审定书
    appendixFile: string; // 附件
    remark: string; // 备注
  }
}

/** 查询项目信息分页 */
export function getProjectPage(params: PageParam) {
  return requestClient.get<PageResult<ProjectApi.Project>>(
    '/biz/project/page',
    { params },
  );
}

/** 查询项目信息详情 */
export function getProject(id: number) {
  return requestClient.get<ProjectApi.Project>(`/biz/project/get?id=${id}`);
}

/** 新增项目信息 */
export function createProject(data: ProjectApi.Project) {
  return requestClient.post('/biz/project/create', data);
}

/** 修改项目信息 */
export function updateProject(data: ProjectApi.Project) {
  return requestClient.put('/biz/project/update', data);
}

/** 删除项目信息 */
export function deleteProject(id: number) {
  return requestClient.delete(`/biz/project/delete?id=${id}`);
}

/** 批量删除项目信息 */
export function deleteProjectList(ids: number[]) {
  return requestClient.delete(`/biz/project/delete-list?ids=${ids.join(',')}`);
}

/** 导出项目信息 */
export function exportProject(params: any) {
  return requestClient.download('/biz/project/export-excel', params);
}

/** 导入项目信息模版 */
export function importProjectTemplate() {
  return requestClient.download('/biz/project/get-import-template');
}

/** 导入项目信息 */
export function importProject(file: File) {
  return requestClient.upload('/biz/project/import', {
    file,
  });
}
