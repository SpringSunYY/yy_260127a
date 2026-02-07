import type { PageParam, PageResult } from '@vben/request';
import type { Dayjs } from 'dayjs';

import { requestClient } from '#/api/request';

export namespace ProjectOtherApi {
  /** 其他工程信息 */
  export interface ProjectOther {
    id: number; // 编号
    projectName?: string; // 项目名称
    projectType?: string; // 项目类型
    projectAddress: string; // 地址
    projectDate: string | Dayjs; // 时间
    constructionFee: number; // 施工费
    isSettled?: string; // 已结算
    appendixFile: string; // 附件
    materialDesc: string; // 材料说明
    progressStatus?: string; // 进度
    remark: string; // 备注
  }
}

/** 查询其他工程分页 */
export function getProjectOtherPage(params: PageParam) {
  return requestClient.get<PageResult<ProjectOtherApi.ProjectOther>>('/biz/project-other/page', { params });
}

/** 查询其他工程详情 */
export function getProjectOther(id: number) {
  return requestClient.get<ProjectOtherApi.ProjectOther>(`/biz/project-other/get?id=${id}`);
}

/** 新增其他工程 */
export function createProjectOther(data: ProjectOtherApi.ProjectOther) {
  return requestClient.post('/biz/project-other/create', data);
}

/** 修改其他工程 */
export function updateProjectOther(data: ProjectOtherApi.ProjectOther) {
  return requestClient.put('/biz/project-other/update', data);
}

/** 删除其他工程 */
export function deleteProjectOther(id: number) {
  return requestClient.delete(`/biz/project-other/delete?id=${id}`);
}

/** 批量删除其他工程 */
export function deleteProjectOtherList(ids: number[]) {
  return requestClient.delete(`/biz/project-other/delete-list?ids=${ids.join(',')}`)
}

/** 导出其他工程 */
export function exportProjectOther(params: any) {
  return requestClient.download('/biz/project-other/export-excel', params);
}