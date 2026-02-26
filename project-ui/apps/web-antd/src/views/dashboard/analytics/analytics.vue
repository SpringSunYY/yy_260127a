<script lang="ts" setup>
import type { AnalysisOverviewItem } from '@vben/common-ui';
import type { TabOption } from '@vben/types';

import type { StatisticsApi } from '#/api/biz/statistics';

import { markRaw, onMounted, ref } from 'vue';

import { AnalysisChartsTabs, AnalysisOverview } from '@vben/common-ui';
import { SvgCakeIcon, SvgCardIcon, SvgDownloadIcon } from '@vben/icons';

import dayjs from 'dayjs';

import { getPaymentOrderAmount } from '#/api/biz/paymentOrder';
import { getReceiptOrderAmount } from '#/api/biz/receiptOrder';
import { getTotalPayableAmount } from '#/api/biz/salary';
import {
  getPaymentStatistics,
  getReceiptStatistics,
  getSalaryStatistics,
} from '#/api/biz/statistics';
import BarAutoCarouselCharts from '#/views/dashboard/analytics/BarAutoCarouselCharts.vue';
import BarLineZoomCharts from '#/views/dashboard/analytics/BarLineZoomCharts.vue';
import BarTrendCharts from '#/views/dashboard/analytics/BarTrendCharts.vue';

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

const paymentStatisticsData = ref<StatisticsApi.StatisticsResult[]>([]);
const paymentStatisticsName = ref<string>('付款金额');

const receiptStatisticsData = ref<StatisticsApi.StatisticsResult[]>([]);
const receiptStatisticsName = ref<string>('收款金额');

const salaryStatisticsData = ref<StatisticsApi.StatisticsResult[]>([]);
const salaryStatisticsName = ref<string>('工资信息');

// 处理日期变化
const handleDateChange = (value: {
  endDate: string;
  startDate: string;
  type: string;
}) => {
  const { startDate, endDate, type } = value;

  if (!startDate || !endDate) {
    return;
  }
  // 获取支付数据
  getPaymentStatisticsData(startDate, endDate, type);
  // 获取收款数据
  getReceiptStatisticsData(startDate, endDate, type);
  // 获取工资数据
  getSalaryStatisticsData(startDate, endDate, type);
};
const getPaymentStatisticsData = (
  startTime: string,
  endTime: string,
  type: string,
) => {
  getPaymentStatistics({
    startTime,
    endTime,
    type,
  }).then((res) => {
    // 确保数据存在后再赋值
    if (res) {
      // 使用展开运算符创建新数组，确保 Vue 检测到变化
      paymentStatisticsData.value = [...res];
    }
  });
};
const getReceiptStatisticsData = (
  startTime: string,
  endTime: string,
  type: string,
) => {
  getReceiptStatistics({
    startTime,
    endTime,
    type,
  }).then((res) => {
    // 确保数据存在后再赋值
    if (res) {
      // 使用展开运算符创建新数组，确保 Vue 检测到变化
      receiptStatisticsData.value = [...res];
    }
  });
};

const getSalaryStatisticsData = (
  startTime: string,
  endTime: string,
  type: string,
) => {
  getSalaryStatistics({
    startTime,
    endTime,
    type,
  }).then((res) => {
    // 确保数据存在后再赋值
    if (res) {
      // 使用展开运算符创建新数组，确保 Vue 检测到变化
      salaryStatisticsData.value = [...res];
    }
  });
};

const chartTabs: TabOption[] = [
  {
    label: paymentStatisticsName.value,
    value: 'payment',
  },
  {
    label: '收款金额',
    value: 'receipt',
  },
  {
    label: '工资信息',
    value: 'salary',
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
      <template #payment>
        <BarLineZoomCharts
          :chart-data="paymentStatisticsData"
          :chart-title="paymentStatisticsName"
        />
      </template>
      <template #receipt>
        <BarTrendCharts
          :chart-title="receiptStatisticsName"
          :chart-data="receiptStatisticsData"
        />
      </template>
      <template #salary>
        <BarAutoCarouselCharts
          :chart-data="salaryStatisticsData"
          :chart-title="salaryStatisticsName"
        />
      </template>
    </AnalysisChartsTabs>
  </div>
</template>
