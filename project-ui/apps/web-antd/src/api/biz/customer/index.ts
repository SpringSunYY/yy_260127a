import type { PageParam, PageResult } from '@vben/request';
import type { Dayjs } from 'dayjs';

import { requestClient } from '#/api/request';

export namespace CustomerApi {
  /** 客户信息信息 */
  export interface Customer {
    id: number; // 编号
    name: string; // 客户名称
    mobile: string; // 手机
    telephone: string; // 电话
    qq: string; // QQ
    weChat: string; // 微信
    email: string; // 邮箱
    areaId: number; // 地区编号
    detailAddress: string; // 详细地址
    industry: string; // 所属行业
    remark: string; // 备注
  }
}

/** 查询客户信息分页 */
export function getCustomerPage(params: PageParam) {
  return requestClient.get<PageResult<CustomerApi.Customer>>('/biz/customer/page', { params });
}

/** 查询客户信息详情 */
export function getCustomer(id: number) {
  return requestClient.get<CustomerApi.Customer>(`/biz/customer/get?id=${id}`);
}

/** 新增客户信息 */
export function createCustomer(data: CustomerApi.Customer) {
  return requestClient.post('/biz/customer/create', data);
}

/** 修改客户信息 */
export function updateCustomer(data: CustomerApi.Customer) {
  return requestClient.put('/biz/customer/update', data);
}

/** 删除客户信息 */
export function deleteCustomer(id: number) {
  return requestClient.delete(`/biz/customer/delete?id=${id}`);
}

/** 批量删除客户信息 */
export function deleteCustomerList(ids: number[]) {
  return requestClient.delete(`/biz/customer/delete-list?ids=${ids.join(',')}`)
}

/** 导出客户信息 */
export function exportCustomer(params: any) {
  return requestClient.download('/biz/customer/export-excel', params);
}