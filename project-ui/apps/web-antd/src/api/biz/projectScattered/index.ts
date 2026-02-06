import type { PageParam, PageResult } from '@vben/request';
import type { Dayjs } from 'dayjs';

import { requestClient } from '#/api/request';

export namespace ProjectScatteredApi {
  /** 零散工程信息 */
  export interface ProjectScattered {
    id: number; // 编号
    projectId?: string; // 项目编号
    projectNo?: string; // 项目编号
    projectName?: string; // 项目名称
    scatteredName?: string; // 工程名称
    scatteredTime?: string | Dayjs; // 时间
    projectProgress?: string; // 工程阶段
    completedImage: string; // 竣工图
    verification: string; // 现场核销
    appendixFile: string; // 附件
    remark: string; // 备注
  }
}

/** 查询零散工程分页 */
export function getProjectScatteredPage(params: PageParam) {
  return requestClient.get<PageResult<ProjectScatteredApi.ProjectScattered>>('/biz/project-scattered/page', { params });
}

/** 查询零散工程详情 */
export function getProjectScattered(id: number) {
  return requestClient.get<ProjectScatteredApi.ProjectScattered>(`/biz/project-scattered/get?id=${id}`);
}

/** 新增零散工程 */
export function createProjectScattered(data: ProjectScatteredApi.ProjectScattered) {
  return requestClient.post('/biz/project-scattered/create', data);
}

/** 修改零散工程 */
export function updateProjectScattered(data: ProjectScatteredApi.ProjectScattered) {
  return requestClient.put('/biz/project-scattered/update', data);
}

/** 删除零散工程 */
export function deleteProjectScattered(id: number) {
  return requestClient.delete(`/biz/project-scattered/delete?id=${id}`);
}

/** 批量删除零散工程 */
export function deleteProjectScatteredList(ids: number[]) {
  return requestClient.delete(`/biz/project-scattered/delete-list?ids=${ids.join(',')}`)
}

/** 导出零散工程 */
export function exportProjectScattered(params: any) {
  return requestClient.download('/biz/project-scattered/export-excel', params);
}