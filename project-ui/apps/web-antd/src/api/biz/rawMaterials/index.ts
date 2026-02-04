import type {PageParam, PageResult} from '@vben/request';

import {requestClient} from '#/api/request';

export namespace RawMaterialsApi {
  /** 原材料信息信息 */
  export interface RawMaterials {
    id: number; // 编号
    materialName?: string; // 材料名称
    materialSpec: string; // 规格型号
    unit?: string; // 计量单位
    unitPrice?: number; // 采购单价
    remark: string; // 备注
  }
}

/** 查询原材料信息分页 */
export function getRawMaterialsPage(params: PageParam) {
  return requestClient.get<PageResult<RawMaterialsApi.RawMaterials>>('/biz/raw-materials/page', { params });
}

/** 查询原材料信息详情 */
export function getRawMaterials(id: number) {
  return requestClient.get<RawMaterialsApi.RawMaterials>(`/biz/raw-materials/get?id=${id}`);
}

/** 新增原材料信息 */
export function createRawMaterials(data: RawMaterialsApi.RawMaterials) {
  return requestClient.post('/biz/raw-materials/create', data);
}

/** 修改原材料信息 */
export function updateRawMaterials(data: RawMaterialsApi.RawMaterials) {
  return requestClient.put('/biz/raw-materials/update', data);
}

/** 删除原材料信息 */
export function deleteRawMaterials(id: number) {
  return requestClient.delete(`/biz/raw-materials/delete?id=${id}`);
}

/** 批量删除原材料信息 */
export function deleteRawMaterialsList(ids: number[]) {
  return requestClient.delete(`/biz/raw-materials/delete-list?ids=${ids.join(',')}`)
}

/** 导出原材料信息 */
export function exportRawMaterials(params: any) {
  return requestClient.download('/biz/raw-materials/export-excel', params);
}
