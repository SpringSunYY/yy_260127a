import type {PageParam, PageResult} from '@vben/request';

import {requestClient} from '#/api/request';

export namespace SupplierApi {
  /** 供应商信息信息 */
  export interface Supplier {
    id: number; // 编号
    name?: string; // 供应商名称
    telephone: string; // 电话
    qq: string; // QQ
    weChat: string; // 微信
    email: string; // 邮箱
    areaId: number; // 地区编号
    detailAddress: string; // 详细地址
    remark: string; // 备注
  }
}

/** 查询供应商信息分页 */
export function getSupplierPage(params: PageParam) {
  return requestClient.get<PageResult<SupplierApi.Supplier>>('/biz/supplier/page', { params });
}

/** 查询供应商信息详情 */
export function getSupplier(id: number) {
  return requestClient.get<SupplierApi.Supplier>(`/biz/supplier/get?id=${id}`);
}

/** 新增供应商信息 */
export function createSupplier(data: SupplierApi.Supplier) {
  return requestClient.post('/biz/supplier/create', data);
}

/** 修改供应商信息 */
export function updateSupplier(data: SupplierApi.Supplier) {
  return requestClient.put('/biz/supplier/update', data);
}

/** 删除供应商信息 */
export function deleteSupplier(id: number) {
  return requestClient.delete(`/biz/supplier/delete?id=${id}`);
}

/** 批量删除供应商信息 */
export function deleteSupplierList(ids: number[]) {
  return requestClient.delete(`/biz/supplier/delete-list?ids=${ids.join(',')}`)
}

/** 导出供应商信息 */
export function exportSupplier(params: any) {
  return requestClient.download('/biz/supplier/export-excel', params);
}
