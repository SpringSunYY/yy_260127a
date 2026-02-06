import type { PageParam, PageResult } from '@vben/request';

import { requestClient } from '#/api/request';

export namespace ProjectVisaApi {
  /** 项目签证信息 */
  export interface ProjectVisa {
    id: number; // 编号
    projectId?: number; // 项目ID
    projectNo?: string; // 项目编号
    name?: string; // 项目名称
    visaName?: string; // 工程名称
    visaContent: string; // 签证内容
    amount?: number; // 金额
    appendixFile: string; // 附件
    remark: string; // 备注
  }
}

/** 查询项目签证分页 */
export function getProjectVisaPage(params: PageParam) {
  return requestClient.get<PageResult<ProjectVisaApi.ProjectVisa>>(
    '/biz/project-visa/page',
    { params },
  );
}

/** 查询项目签证详情 */
export function getProjectVisa(id: number) {
  return requestClient.get<ProjectVisaApi.ProjectVisa>(
    `/biz/project-visa/get?id=${id}`,
  );
}

/** 新增项目签证 */
export function createProjectVisa(data: ProjectVisaApi.ProjectVisa) {
  return requestClient.post('/biz/project-visa/create', data);
}

/** 修改项目签证 */
export function updateProjectVisa(data: ProjectVisaApi.ProjectVisa) {
  return requestClient.put('/biz/project-visa/update', data);
}

/** 删除项目签证 */
export function deleteProjectVisa(id: number) {
  return requestClient.delete(`/biz/project-visa/delete?id=${id}`);
}

/** 批量删除项目签证 */
export function deleteProjectVisaList(ids: number[]) {
  return requestClient.delete(
    `/biz/project-visa/delete-list?ids=${ids.join(',')}`,
  );
}

/** 导出项目签证 */
export function exportProjectVisa(params: any) {
  return requestClient.download('/biz/project-visa/export-excel', params);
}
