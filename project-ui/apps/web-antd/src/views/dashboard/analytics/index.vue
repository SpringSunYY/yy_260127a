<script lang="ts" setup>
import type { AnalysisOverviewItem } from '@vben/common-ui';
import type { TabOption } from '@vben/types';

import { markRaw, onMounted, ref } from 'vue';

import {
  AnalysisChartCard,
  AnalysisChartsTabs,
  AnalysisOverview,
} from '@vben/common-ui';
import { SvgCakeIcon, SvgCardIcon, SvgDownloadIcon } from '@vben/icons';

import dayjs from 'dayjs';

import { getPaymentOrderAmount } from '#/api/biz/paymentOrder';
import { getReceiptOrderAmount } from '#/api/biz/receiptOrder';
import { getTotalPayableAmount } from '#/api/biz/salary';

import AnalyticsTrends from './analytics-trends.vue';
import AnalyticsVisitsData from './analytics-visits-data.vue';
import AnalyticsVisitsSales from './analytics-visits-sales.vue';
import AnalyticsVisitsSource from './analytics-visits-source.vue';
import AnalyticsVisits from './analytics-visits.vue';

const overviewItems = ref<AnalysisOverviewItem[]>([
  {
    key: 'payment',
    icon: markRaw(SvgCardIcon),
    title: '本月付款金额',
    totalTitle: '总付款金额',
    totalValue: 0,
    value: 0,
  },
  {
    key: 'receipt',
    icon: markRaw(SvgCakeIcon),
    title: '本月收款金额',
    totalTitle: '总收款金额',
    totalValue: 0,
    value: 0,
  },
  {
    key: 'salary',
    icon: markRaw(SvgDownloadIcon),
    title: '本月工资',
    totalTitle: '总工资',
    totalValue: 0,
    value: 0,
  },
]);

// 获取本月开始和结束日期
const getMonthRange = () => {
  const startOfMonth = dayjs().startOf('month').format('YYYY-MM-DD HH:mm:ss');
  const endOfMonth = dayjs().endOf('month').format('YYYY-MM-DD HH:mm:ss');
  return { startOfMonth, endOfMonth };
};

// 获取付款数据
const fetchPaymentData = async () => {
  const { startOfMonth, endOfMonth } = getMonthRange();
  try {
    // 本月付款金额
    const monthRes = await getPaymentOrderAmount({
      paymentTime: [startOfMonth, endOfMonth] as any,
    } as any);
    // 总付款金额
    const totalRes = await getPaymentOrderAmount({} as any);

    const paymentItem = overviewItems.value.find(
      (item) => item.key === 'payment',
    );
    if (paymentItem) {
      paymentItem.value = monthRes ?? 0;
      paymentItem.totalValue = totalRes ?? 0;
    }
  } catch (error) {
    console.error('获取付款数据失败:', error);
  }
};

// 获取收款数据
const fetchReceiptData = async () => {
  const { startOfMonth, endOfMonth } = getMonthRange();
  try {
    // 本月收款金额
    const monthRes = await getReceiptOrderAmount({
      receiptDate: [startOfMonth, endOfMonth] as any,
    } as any);
    // 总收款金额
    const totalRes = await getReceiptOrderAmount({} as any);

    const receiptItem = overviewItems.value.find(
      (item) => item.key === 'receipt',
    );
    if (receiptItem) {
      // 处理可能的 PageResult 类型或直接 number 类型
      const monthValue = (monthRes as any).data ?? monthRes ?? 0;
      const totalValue = (totalRes as any).data ?? totalRes ?? 0;
      receiptItem.value = typeof monthValue === 'number' ? monthValue : 0;
      receiptItem.totalValue = typeof totalValue === 'number' ? totalValue : 0;
    }
  } catch (error) {
    console.error('获取收款数据失败:', error);
  }
};

// 获取工资数据
const fetchSalaryData = async () => {
  const { startOfMonth, endOfMonth } = getMonthRange();
  try {
    // 本月工资
    const monthRes = await getTotalPayableAmount({
      settlementTime: [startOfMonth, endOfMonth] as any,
    } as any);
    // 总工资
    const totalRes = await getTotalPayableAmount({} as any);

    const salaryItem = overviewItems.value.find(
      (item) => item.key === 'salary',
    );
    if (salaryItem) {
      salaryItem.value = monthRes ?? 0;
      salaryItem.totalValue = totalRes ?? 0;
    }
  } catch (error) {
    console.error('获取工资数据失败:', error);
  }
};

// 组件创建时获取数据
onMounted(async () => {
  await Promise.all([
    fetchPaymentData(),
    fetchReceiptData(),
    fetchSalaryData(),
  ]);
});

// 时间类型
type DateType = 'date' | 'month' | 'year';

// 处理日期变化
const handleDateChange = (value: {
  endDate: string;
  startDate: string;
  type: DateType;
}) => {
  console.log('日期变化:', value);
  const { startDate, endDate, type } = value;

  if (!startDate || !endDate) {
    console.log('日期范围不完整');
    return;
  }
  console.log('开始日期:', startDate);
  console.log('结束日期:', endDate);
  console.log('时间类型:', type);
};

const chartTabs: TabOption[] = [
  {
    label: '付款金额',
    value: 'trends',
  },
  {
    label: '收款金额',
    value: 'visits',
  },
  {
    label: '工资信息',
    value: 'sales',
  },
];
</script>

<template>
  <div class="p-5">
    <AnalysisOverview :items="overviewItems" />
    <AnalysisChartsTabs
      :tabs="chartTabs"
      class="mt-5"
      @date-change="handleDateChange"
    >
      <template #trends>
        <AnalyticsTrends />
      </template>
      <template #visits>
        <AnalyticsVisits />
      </template>
    </AnalysisChartsTabs>

    <div class="mt-5 w-full md:flex">
      <AnalysisChartCard class="mt-5 md:mr-4 md:mt-0 md:w-1/3" title="访问数量">
        <AnalyticsVisitsData />
      </AnalysisChartCard>
      <AnalysisChartCard class="mt-5 md:mr-4 md:mt-0 md:w-1/3" title="访问来源">
        <AnalyticsVisitsSource />
      </AnalysisChartCard>
      <AnalysisChartCard class="mt-5 md:mt-0 md:w-1/3" title="访问来源">
        <AnalyticsVisitsSales />
      </AnalysisChartCard>
    </div>
  </div>
</template>
