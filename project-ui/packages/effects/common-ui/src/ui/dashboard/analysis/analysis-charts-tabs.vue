<script setup lang="ts">
import type { TabOption } from '@vben/types';

import { computed, defineAsyncComponent, onMounted, ref } from 'vue';

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@vben-core/shadcn-ui';

// @ts-ignore
import dayjs from 'dayjs';

defineOptions({
  name: 'AnalysisChartsTabs',
});

const props = withDefaults(defineProps<Props>(), {
  tabs: () => [],
});

// emit 事件
const emit = defineEmits<{
  (
    e: 'dateChange',
    value: { endDate: string; startDate: string; type: string },
  ): void;
}>();

// 从 ant-design-vue 动态导入 RangePicker
// @ts-ignore
const RangePicker = defineAsyncComponent(() =>
  import('ant-design-vue/es/date-picker').then((res) => res.RangePicker),
);
// @ts-ignore
const Button = defineAsyncComponent(() =>
  import('ant-design-vue/es/button').then((res) => res.default),
);

interface Props {
  tabs?: TabOption[];
}

// 时间类型
type DateType = 'date' | 'month' | 'year';

// 当前选中的时间类型
const dateType = ref<DateType>('month');

// 日期范围 [开始, 结束]
const dateRange = ref<[any, any] | null>(null);

// 默认值
const defaultValue = computed(() => {
  return props.tabs?.[0]?.value;
});

// RangePicker 的 picker 属性
const pickerMode = computed(() => dateType.value);

// 时间类型对应的格式
const dateFormat = computed(() => {
  if (dateType.value === 'year') return 'YYYY';
  if (dateType.value === 'month') return 'YYYY-MM';
  return 'YYYY-MM-DD';
});

// 格式化日期为年月日时分秒
const formatDateTime = (value: any) => {
  if (!value) return '';
  return value.format(dateFormat.value);
};

// 初始化默认时间范围
const initDefaultRange = () => {
  const end = dayjs();

  if (dateType.value === 'year') {
    // 年：近10年
    const start = end.subtract(9, 'year');
    dateRange.value = [start.startOf('year'), end.endOf('year')];
  } else if (dateType.value === 'month') {
    // 月：近12个月
    const start = end.subtract(11, 'month');
    dateRange.value = [start.startOf('month'), end.endOf('month')];
  } else {
    // 日：近30天
    const start = end.subtract(29, 'day');
    dateRange.value = [start.startOf('day'), end.endOf('day')];
  }

  // 发送默认时间范围
  sendDateChange();
};

// 发送日期变化事件
const sendDateChange = () => {
  if (!dateRange.value || !dateRange.value[0] || !dateRange.value[1]) {
    emit('dateChange', { startDate: '', endDate: '', type: dateFormat.value });
    return;
  }

  const start = formatDateTime(dateRange.value[0]);
  const end = formatDateTime(dateRange.value[1]);

  // 格式化完整日期时间
  let startDateTime = '';
  let endDateTime = '';

  if (dateType.value === 'year') {
    startDateTime = `${start}-01-01 00:00:00`;
    endDateTime = `${end}-12-31 23:59:59`;
  } else if (dateType.value === 'month') {
    const [startYear, startMonth] = start.split('-');
    const [endYear, endMonth] = end.split('-');
    const startMonthEnd = dayjs(`${startYear}-${startMonth}`)
      .endOf('month')
      .format('YYYY-MM-DD');
    const endMonthEnd = dayjs(`${endYear}-${endMonth}`)
      .endOf('month')
      .format('YYYY-MM-DD');
    startDateTime = `${startMonthEnd} 00:00:00`;
    endDateTime = `${endMonthEnd} 23:59:59`;
  } else {
    startDateTime = `${start} 00:00:00`;
    endDateTime = `${end} 23:59:59`;
  }

  // 返回时间格式：YYYY, YYYY-MM, YYYY-MM-DD
  emit('dateChange', {
    startDate: startDateTime,
    endDate: endDateTime,
    type: dateFormat.value,
  });
};

// 时间类型切换
const handleTypeChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  dateType.value = target.value as DateType;
  // 重新初始化默认范围
  initDefaultRange();
};

// 日期范围变化 - 只更新数据，不触发事件
const handleDateRangeChange = () => {
  // 不触发事件，等待确认
};

// 点击确认按钮
const handleConfirm = () => {
  sendDateChange();
};

// 组件挂载时初始化默认时间范围
onMounted(() => {
  initDefaultRange();
});
</script>

<template>
  <div class="card-box w-full px-4 pb-5 pt-3">
    <Tabs :default-value="defaultValue">
      <div class="mb-4 flex items-center justify-between">
        <TabsList>
          <template v-for="tab in tabs" :key="tab.label">
            <TabsTrigger :value="tab.value"> {{ tab.label }} </TabsTrigger>
          </template>
        </TabsList>
        <div class="flex items-center">
          <a-radio-group
            v-model:value="dateType"
            button-style="solid"
            size="small"
            @change="handleTypeChange"
          >
            <a-radio-button value="year">年</a-radio-button>
            <a-radio-button value="month">月</a-radio-button>
            <a-radio-button value="date">日</a-radio-button>
          </a-radio-group>
          <RangePicker
            v-model:value="dateRange"
            :picker="pickerMode"
            :format="dateFormat"
            class="ml-3"
            @change="handleDateRangeChange"
          />
          <Button
            type="primary"
            size="small"
            class="ml-2"
            @click="handleConfirm"
          >
            确定
          </Button>
        </div>
      </div>
      <template v-for="tab in tabs" :key="tab.label">
        <TabsContent :value="tab.value" class="pt-4" style="height: 400px">
          <slot :name="tab.value"></slot>
        </TabsContent>
      </template>
    </Tabs>
  </div>
</template>
