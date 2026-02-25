<script lang="ts" setup>
import type { SalaryApi } from '#/api/biz/salary';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { createSalary, getSalary, updateSalary } from '#/api/biz/salary';
import { getWorkerPage } from '#/api/biz/worker';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<SalaryApi.Salary>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['工资信息'])
    : $t('ui.actionTitle.create', ['工资信息']);
});

onMounted(() => {
  loadWorkers();
});
// 工人搜索状态
const workerKeyword = ref('');
const workerOptions = ref<any[]>([]);
const workerLoading = ref(false);

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
const handleWorkerChange = (_value: any, option: any) => {
  formApi.setFieldValue(
    'workerName',
    option?.workerName || option?.label || '',
  );
};

// 工人下拉打开时加载数据
const handleWorkerOpenChange = (open: boolean) => {
  if (open) {
    loadWorkers();
  }
};

const [Form, formApi] = useVbenForm({
  commonConfig: {
    componentProps: {
      class: 'w-full',
    },
    formItemClass: 'col-span-1',
    labelWidth: 120,
  },
  layout: 'horizontal',
  schema: useFormSchema(),
  showDefaultActions: false,
  wrapperClass: 'grid-cols-2 gap-x-4',
});

const [Modal, modalApi] = useVbenModal({
  class: 'w-[1000px]',
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) {
      return;
    }
    modalApi.lock();
    // 提交表单
    const data = (await formApi.getValues()) as SalaryApi.Salary;
    try {
      await (formData.value?.id ? updateSalary(data) : createSalary(data));
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
    let data = modalApi.getData<SalaryApi.Salary>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getSalary(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    // 设置到 values
    formData.value = data;
    await formApi.setValues(formData.value);
  },
});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4">
      <!-- 工人自定义插槽 -->
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
    </Form>
  </Modal>
</template>
