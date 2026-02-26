import { requestClient } from '#/api/request';

export namespace StatisticsApi {
  /** 统计信息 */
  export interface StatisticsParams {
    /** 开始时间 */
    startTime: string;
    /** 结束时间 */
    endTime: string;
    /** 时间类型 */
    type: string;
  }

  /** 统计返回结果 */
  export interface StatisticsResult {
    /** 名称 */
    name: string;
    /** 值 */
    value: number;
  }
}

interface Result {
  data: StatisticsApi.StatisticsResult[];
}

/** 查询工资信息分页 */
export function getPaymentStatistics(params: StatisticsApi.StatisticsParams) {
  return requestClient.get<Result>('/biz/statistics/payment', {
    params,
  });
}
