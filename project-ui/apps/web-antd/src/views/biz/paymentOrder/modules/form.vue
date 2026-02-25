<script lang="ts" setup>
import type { PaymentOrderApi } from '#/api/biz/paymentOrder';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { getCustomerPage } from '#/api/biz/customer';
import {
  createPaymentOrder,
  getPaymentOrder,
  updatePaymentOrder,
} from '#/api/biz/paymentOrder';
import { getProjectPage } from '#/api/biz/project';
import { getProjectOtherPage } from '#/api/biz/projectOther';
import { getSupplierPage } from '#/api/biz/supplier';
import { getWorkerPage } from '#/api/biz/worker';
import { $t } from '#/locales';
import { BIZ_PAYMENT_PAYEE_TYPE, BIZ_RECEIPT_PROJECT_TYPE } from '#/utils';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<PaymentOrderApi.PaymentOrder>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['付款信息'])
    : $t('ui.actionTitle.create', ['付款信息']);
});

// 项目搜索状态
const projectKeyword = ref('');
const projectOptions = ref<any[]>([]);
const projectLoading = ref(false);

// 付款对象搜索状态
const payeeKeyword = ref('');
const payeeOptions = ref<any[]>([]);
const payeeLoading = ref(false);

// 加载项目列表
const loadProjects = async (keyword?: string) => {
  projectLoading.value = true;
  try {
    // 优先从 form 状态获取，如果未就绪则根据表单结构默认
    const projectType =
      (formApi?.form as any)?.values?.projectType ||
      formData.value?.projectType;

    if (projectType === BIZ_RECEIPT_PROJECT_TYPE.receipt_project_type_2) {
      // 其他工程
      const res = await getProjectOtherPage({
        pageNo: 1,
        pageSize: 50,
        projectName: keyword || '',
      });
      // 解析结果，把projectName改为name
      projectOptions.value = res.list.map((item: any) => ({
        id: item.id,
        name: item.projectName,
        value: item.id,
        label: item.projectName,
      }));
    } else {
      // 默认项目
      const res = await getProjectPage({
        pageNo: 1,
        pageSize: 50,
        name: keyword || '',
      });
      projectOptions.value = res.list || [];
    }
  } finally {
    projectLoading.value = false;
  }
};

// 项目类型变更
const handleProjectTypeChange = () => {
  formApi.setFieldValue('projectId', undefined);
  formApi.setFieldValue('projectName', undefined);
  projectOptions.value = [];
  loadProjects();
};

// 项目搜索
const handleProjectSearch = useDebounceFn((_value: string) => {
  projectKeyword.value = _value;
  loadProjects(_value);
}, 300);

// 项目选择
const handleProjectChange = (_value: any, option: any) => {
  formApi.setFieldValue('projectName', option?.name || option?.label || '');
};

// 项目下拉打开时加载数据
const handleProjectOpenChange = (open: boolean) => {
  if (open) {
    loadProjects();
  }
};

// 付款对象类型变更
const handlePayeeTypeChange = () => {
  formApi.setFieldValue('payeeId', undefined);
  formApi.setFieldValue('payeeName', undefined);
  payeeOptions.value = [];
  loadPayees();
};

// 加载付款对象列表
const loadPayees = async (keyword?: string) => {
  payeeLoading.value = true;
  try {
    const payeeType =
      (formApi?.form as any)?.values?.payeeType || formData.value?.payeeType;

    if (!payeeType) {
      payeeOptions.value = [];
      return;
    }

    let res: any;
    switch (payeeType) {
      case BIZ_PAYMENT_PAYEE_TYPE.payment_payee_type_1: {
        // 工人
        res = await getWorkerPage({
          pageNo: 1,
          pageSize: 50,
          name: keyword || '',
        });

        break;
      }
      case BIZ_PAYMENT_PAYEE_TYPE.payment_payee_type_2: {
        // 供应商
        res = await getSupplierPage({
          pageNo: 1,
          pageSize: 50,
          name: keyword || '',
        });

        break;
      }
      case BIZ_PAYMENT_PAYEE_TYPE.payment_payee_type_3: {
        // 客户
        res = await getCustomerPage({
          pageNo: 1,
          pageSize: 50,
          name: keyword || '',
        });

        break;
      }
      // No default
    }

    if (res?.list) {
      payeeOptions.value = res.list.map((item: any) => ({
        id: item.id,
        name:
          item.name ||
          item.workerName ||
          item.customerName ||
          item.supplierName ||
          '', // 兼容不同字段名
        value: item.id,
        label:
          item.name ||
          item.workerName ||
          item.customerName ||
          item.supplierName ||
          '',
      }));
    } else {
      payeeOptions.value = [];
    }
  } catch (error) {
    console.error(error);
    payeeOptions.value = [];
  } finally {
    payeeLoading.value = false;
  }
};

// 付款对象搜索
const handlePayeeSearch = useDebounceFn((_value: string) => {
  payeeKeyword.value = _value;
  loadPayees(_value);
}, 300);

// 付款对象选择
const handlePayeeChange = (_value: any, option: any) => {
  formApi.setFieldValue('payeeName', option?.name || option?.label || '');
};

// 付款对象下拉打开时加载数据
const handlePayeeOpenChange = (open: boolean) => {
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
  schema: useFormSchema({
    onProjectTypeChange: handleProjectTypeChange,
    onPayeeTypeChange: handlePayeeTypeChange,
  }),
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

    // 加载项目列表
    await loadProjects();

    // 加载付款对象列表
    await loadPayees();
  },
});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4">
      <!-- 项目自定义插槽 -->
      <template #projectId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择项目"
          :loading="projectLoading"
          :options="projectOptions"
          :field-names="{ label: 'name', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handleProjectSearch"
          @change="handleProjectChange"
          @dropdown-open-change="handleProjectOpenChange"
        />
      </template>

      <!-- 付款对象自定义插槽 -->
      <template #payeeId="slotProps">
        <Select
          v-bind="slotProps"
          show-search
          allow-clear
          placeholder="请选择付款对象"
          :loading="payeeLoading"
          :options="payeeOptions"
          :field-names="{ label: 'name', value: 'id' }"
          :filter-option="false"
          class="w-full"
          @search="handlePayeeSearch"
          @change="handlePayeeChange"
          @dropdown-open-change="handlePayeeOpenChange"
        />
      </template>
    </Form>
  </Modal>
</template>
