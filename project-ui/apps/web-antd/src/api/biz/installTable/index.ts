import type { PageParam, PageResult } from '@vben/request';
import type { Dayjs } from 'dayjs';

import { requestClient } from '#/api/request';

export namespace InstallTableApi {
  /** 装表信息信息 */
  export interface InstallTable {
    id: number; // 编号
    installDate: string | Dayjs; // 安装日期
    communityName: string; // 小区名称
    houseAddress: string; // 门牌地址
    dn15PipeLength: number; // DN15镀锌钢管(m)
    dn25PipeLength: number; // DN25镀锌钢管(m)
    dn15ElbowQty: number; // DN15弯头(个)
    dn15InnerConnectorQty: number; // DN15内接(个)
    dn15DirectQty: number; // DN15直接(个)
    pipeClampQty: number; // 管卡(个)
    galvanizedTeeQty: number; // 镀锌三通(个)
    preMeterValveQty: number; // 表前球阀(只)
    doubleNozzleValveQty: number; // 双直嘴阀(只)
    singleNozzleValveQty: number; // 单直嘴阀(只)
    meterConnectorQty: number; // 表接头(只)
    meterNo: string; // 表号
    meterModel: string; // 型号
    meterDirection: string; // 表向
    floorHeightStatus: string; // 层高及入住情况
    ownerName: string; // 户主
    contactPhone: string; // 联系方式
    extraLengthFee: number; // 超长费用(元)
    addMeterBox: string; // 加表箱
    installerName: string; // 安装人员
    isHighAltitude: string; // 高空作业
    isOpenTee: string; // 开T
    remark: string; // 备注
  }
}

/** 查询装表信息分页 */
export function getInstallTablePage(params: PageParam) {
  return requestClient.get<PageResult<InstallTableApi.InstallTable>>('/biz/install-table/page', { params });
}

/** 查询装表信息详情 */
export function getInstallTable(id: number) {
  return requestClient.get<InstallTableApi.InstallTable>(`/biz/install-table/get?id=${id}`);
}

/** 新增装表信息 */
export function createInstallTable(data: InstallTableApi.InstallTable) {
  return requestClient.post('/biz/install-table/create', data);
}

/** 修改装表信息 */
export function updateInstallTable(data: InstallTableApi.InstallTable) {
  return requestClient.put('/biz/install-table/update', data);
}

/** 删除装表信息 */
export function deleteInstallTable(id: number) {
  return requestClient.delete(`/biz/install-table/delete?id=${id}`);
}

/** 批量删除装表信息 */
export function deleteInstallTableList(ids: number[]) {
  return requestClient.delete(`/biz/install-table/delete-list?ids=${ids.join(',')}`)
}

/** 导出装表信息 */
export function exportInstallTable(params: any) {
  return requestClient.download('/biz/install-table/export-excel', params);
}