<script lang="ts" setup>
import type { SalaryPaymentOrderApi } from '#/api/biz/salaryPaymentOrder';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { getSalaryPage } from '#/api/biz/salary';
import {
  createSalaryPaymentOrder,
  getSalaryPaymentOrder,
  updateSalaryPaymentOrder,
} from '#/api/biz/salaryPaymentOrder';
import { getWorkerPage } from '#/api/biz/worker';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);

onMounted(() => {
  loadWorkers();
  loadSalaries();
});

const formData = ref<SalaryPaymentOrderApi.SalaryPaymentOrder>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['工资付款信息'])
    : $t('ui.actionTitle.create', ['工资付款信息']);
});

// 工人搜索状态
const workerKeyword = ref('');
const workerOptions = ref<any[]>([]);
const workerLoading = ref(false);
// 当前选择的工人ID
const currentWorkerId = ref<number | undefined>();

// 加载工人列表
const loadWorkers = async (keyword?: string) => {
  workerLoading.value = true;
  try {
    const res = await getWorkerPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
    });
    workerOptions.value = res.list || [];
  } finally {
    workerLoading.value = false;
  }
};

// 工人搜索
const handleWorkerSearch = useDebounceFn((_value: string) => {
  workerKeyword.value = _value;
  loadWorkers(_value);
}, 300);

// 工人选择
const handleWorkerChange = async (_value: any, option: any) => {
  formApi.setFieldValue(
    'workerName',
    option?.workerName || option?.label || '',
  );
  // 更新当前工人ID
  currentWorkerId.value = _value;
  // 清空已选中的工资，并重新加载该工人的工资列表
  formApi.setFieldValue('salaryId', undefined);
  formApi.setFieldValue('salaryName', undefined);
  salaryOptions.value = [];
  await loadSalaries();
};

// 工人下拉打开时加载数据
const handleWorkerOpenChange = (open: boolean) => {
  if (open) {
    loadWorkers();
  }
};

// 付款对象搜索状态
const salaryKeyword = ref('');
const salaryOptions = ref<any[]>([]);
const salaryLoading = ref(false);

// 加载付款对象列表
const loadSalaries = async (keyword?: string) => {
  salaryLoading.value = true;
  try {
    // 供应商
    const res = await getSalaryPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
      isSettlement: '2',
      workerId: currentWorkerId.value,
    });
    // No default
    salaryOptions.value = res?.list
      ? res.list.map((item: any) => ({
          id: item.id,
          name: item.name || item.salaryName || '',
          value: item.id,
          label: `${item.workerName || ''}-${item.name || item.salaryName || ''}`,
          payableAmount: item.payableAmount,
        }))
      : [];
  } catch (error) {
    console.error(error);
    salaryOptions.value = [];
  } finally {
    salaryLoading.value = false;
  }
};

// 付款对象搜索
const handleSalarySearch = useDebounceFn((_value: string) => {
  salaryKeyword.value = _value;
  loadSalaries(_value);
}, 300);

// 付款对象选择
const handleSalaryChange = (_value: any, option: any) => {
  formApi.setFieldValue('salaryName', option?.name || option?.label || '');
  formApi.setFieldValue('paymentAmount', option?.payableAmount || 0);
};

// 付款对象下拉打开时加载数据
const handleSalaryOpenChange = (open: boolean) => {
  if (open) {
    loadSalaries();
  }
};

const [Form, formApi] = useVbenForm({
  commonConfig: {
    componentProps: {
      class: 'w-full',
    },
    formItemClass: 'col-span-2',
    labelWidth: 80,
  },
  layout: 'horizontal',
  schema: useFormSchema(),
  showDefaultActions: false,
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) {
      return;
    }
    modalApi.lock();
    // 提交表单
    const data =
      (await formApi.getValues()) as SalaryPaymentOrderApi.SalaryPaymentOrder;
    try {
      await (formData.value?.id
        ? updateSalaryPaymentOrder(data)
        : createSalaryPaymentOrder(data));
      // 关闭并提示
      await modalApi.close();
      emit('success');
      message.success($t('ui.actionMessage.operationSuccess'));
    } finally {
      modalApi.unlock();
    }
  },
  async onOpenChange(isOpen: boolean) {
    if (!isOpen) {
      formData.value = undefined;
      return;
    }
    // 加载数据
    let data = modalApi.getData<SalaryPaymentOrderApi.SalaryPaymentOrder>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getSalaryPaymentOrder(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    // 设置到 values
    formData.value = data;
    currentWorkerId.value = data.workerId;
    await formApi.setValues(formData.value);
  },
});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4">
      <!-- 付款对象自定义插槽 -->
      <template #workerId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择工人"
          :loading="workerLoading"
          :options="workerOptions"
          :field-names="{ label: 'workerName', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handleWorkerSearch"
          @change="handleWorkerChange"
          @dropdown-open-change="handleWorkerOpenChange"
        />
      </template>
      <!-- 付款对象自定义插槽 -->
      <template #salaryId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择工资"
          :loading="salaryLoading"
          :options="salaryOptions"
          :field-names="{ label: 'label', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handleSalarySearch"
          @change="handleSalaryChange"
          @dropdown-open-change="handleSalaryOpenChange"
        />
      </template>
    </Form>
  </Modal>
</template>
