<script lang="ts" setup>
import type { PurchaseOrderApi } from '#/api/biz/purchaseOrder';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select, Tabs } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import {
  createPurchaseOrder,
  getPurchaseOrder,
  updatePurchaseOrder,
} from '#/api/biz/purchaseOrder';
import { getSupplierPage } from '#/api/biz/supplier';
import { $t } from '#/locales';

import { useFormSchema } from '../data';
import PurchaseOrderDetailForm from './purchase-order-detail-form.vue';

const emit = defineEmits(['success']);

// 初始化加载供应商列表
onMounted(() => {
  loadSuppliers();
});

// 供应商搜索状态
const supplierKeyword = ref('');
const supplierOptions = ref<any[]>([]);
const supplierLoading = ref(false);

// 加载供应商列表
const loadSuppliers = async (keyword?: string) => {
  supplierLoading.value = true;
  try {
    const res = await getSupplierPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
    });
    supplierOptions.value = res.list || [];
  } finally {
    supplierLoading.value = false;
  }
};

// 供应商搜索
const handleSupplierSearch = useDebounceFn((_value: string) => {
  supplierKeyword.value = _value;
  loadSuppliers(_value);
}, 300);

// 供应商选择
const handleSupplierChange = (_value: any, option: any) => {
  formApi.setFieldValue('supplierName', option?.name || option?.label || '');
};

// 供应商下拉打开时加载数据
const handleSupplierOpenChange = (open: boolean) => {
  if (open) {
    loadSuppliers();
  }
};

const formData = ref<PurchaseOrderApi.PurchaseOrder>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['采购信息'])
    : $t('ui.actionTitle.create', ['采购信息']);
});

/** 子表的表单 */
const subTabsName = ref('purchaseOrderDetail');
const purchaseOrderDetailFormRef =
  ref<InstanceType<typeof PurchaseOrderDetailForm>>();

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
    if (!valid) return;
    modalApi.lock();
    const data = (await formApi.getValues()) as PurchaseOrderApi.PurchaseOrder;
    data.purchaseOrderDetails = purchaseOrderDetailFormRef.value?.getData();
    try {
      await (formData.value?.id
        ? updatePurchaseOrder(data)
        : createPurchaseOrder(data));
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
    let data = modalApi.getData<PurchaseOrderApi.PurchaseOrder>();
    if (!data) return;
    if (data.id) {
      modalApi.lock();
      try {
        data = await getPurchaseOrder(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    formData.value = data;
    await formApi.setValues(formData.value);
  },
});

defineExpose({});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4">
      <!-- 供应商自定义插槽 -->
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
    <!-- 子表的表单 -->
    <Tabs v-model:active-key="subTabsName">
      <Tabs.TabPane key="purchaseOrderDetail" tab="采购明细" force-render>
        <PurchaseOrderDetailForm
          ref="purchaseOrderDetailFormRef"
          :purchase-id="formData?.id"
        />
      </Tabs.TabPane>
    </Tabs>
  </Modal>
</template>
