<script lang="ts" setup>
import type { PaymentOrderApi } from '#/api/biz/paymentOrder';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import {
  createPaymentOrder,
  getPaymentOrder,
  updatePaymentOrder,
} from '#/api/biz/paymentOrder';
import { getSupplierPage } from '#/api/biz/supplier';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<PaymentOrderApi.PaymentOrder>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['付款信息'])
    : $t('ui.actionTitle.create', ['付款信息']);
});

// 付款对象搜索状态
const supplierKeyword = ref('');
const supplierOptions = ref<any[]>([]);
const supplierLoading = ref(false);

// 加载付款对象列表
const loadPayees = async (keyword?: string) => {
  supplierLoading.value = true;
  try {
    // 供应商
    const res = await getSupplierPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
    });
    // No default
    supplierOptions.value = res?.list
      ? res.list.map((item: any) => ({
          id: item.id,
          name: item.name || item.supplierName || '',
          value: item.id,
          label: item.name || item.supplierName || '',
        }))
      : [];
  } catch (error) {
    console.error(error);
    supplierOptions.value = [];
  } finally {
    supplierLoading.value = false;
  }
};

// 付款对象搜索
const handleSupplierSearch = useDebounceFn((_value: string) => {
  supplierKeyword.value = _value;
  loadPayees(_value);
}, 300);

// 付款对象选择
const handleSupplierChange = (_value: any, option: any) => {
  formApi.setFieldValue('supplierName', option?.name || option?.label || '');
};

// 付款对象下拉打开时加载数据
const handleSupplierOpenChange = (open: boolean) => {
  if (open) {
    loadPayees();
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
    const data = (await formApi.getValues()) as PaymentOrderApi.PaymentOrder;
    try {
      await (formData.value?.id
        ? updatePaymentOrder(data)
        : createPaymentOrder(data));
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
    let data = modalApi.getData<PaymentOrderApi.PaymentOrder>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getPaymentOrder(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    // 设置到 values
    formData.value = data;
    await formApi.setValues(formData.value);

    // 加载付款对象列表
    await loadPayees();
  },
});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4">
      <!-- 付款对象自定义插槽 -->
      <template #supplierId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择供应商"
          :loading="supplierLoading"
          :options="supplierOptions"
          :field-names="{ label: 'name', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handleSupplierSearch"
          @change="handleSupplierChange"
          @dropdown-open-change="handleSupplierOpenChange"
        />
      </template>
    </Form>
  </Modal>
</template>
