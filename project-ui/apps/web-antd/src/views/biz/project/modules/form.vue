<script lang="ts" setup>
import type { ProjectApi } from '#/api/biz/project';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { getCustomerPage } from '#/api/biz/customer';
import { createProject, getProject, updateProject } from '#/api/biz/project';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
onMounted(() => {
  loadCustomers();
});
// 服务商搜索状态
const customerKeyword = ref('');
const customerOptions = ref<any[]>([]);
const customerLoading = ref(false);

// 加载服务商列表
const loadCustomers = async (keyword?: string) => {
  customerLoading.value = true;
  try {
    const res = await getCustomerPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
    });
    customerOptions.value = res.list || [];
  } finally {
    customerLoading.value = false;
  }
};

// 服务商搜索
const handleCustomerSearch = useDebounceFn((_value: string) => {
  customerKeyword.value = _value;
  loadCustomers(_value);
}, 300);

// 服务商选择
const handleCustomerChange = (_value: any, option: any) => {
  formApi.setFieldValue('customerName', option?.name || option?.label || '');
};

// 服务商下拉打开时加载数据
const handleCustomerOpenChange = (open: boolean) => {
  if (open) {
    loadCustomers();
  }
};

const formData = ref<ProjectApi.Project>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['项目信息'])
    : $t('ui.actionTitle.create', ['项目信息']);
});

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
  class: 'w-[800px]',
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) {
      return;
    }
    modalApi.lock();
    // 提交表单
    const data = (await formApi.getValues()) as ProjectApi.Project;
    try {
      await (formData.value?.id ? updateProject(data) : createProject(data));
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
    let data = modalApi.getData<ProjectApi.Project>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getProject(data.id);
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
      <!-- 服务商自定义插槽 -->
      <template #customerId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择服务商"
          :loading="customerLoading"
          :options="customerOptions"
          :field-names="{ label: 'name', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handleCustomerSearch"
          @change="handleCustomerChange"
          @dropdown-open-change="handleCustomerOpenChange"
        />
      </template>
    </Form>
  </Modal>
</template>
