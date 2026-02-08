<script lang="ts" setup>
import type { ReceiptOrderApi } from '#/api/biz/receiptOrder';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { getProjectPage } from '#/api/biz/project';
import { getProjectOtherPage } from '#/api/biz/projectOther';
import {
  createReceiptOrder,
  getReceiptOrder,
  updateReceiptOrder,
} from '#/api/biz/receiptOrder';
import { $t } from '#/locales';
import { BIZ_RECEIPT_PROJECT_TYPE } from '#/utils/constants';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<ReceiptOrderApi.ReceiptOrder>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['收款信息'])
    : $t('ui.actionTitle.create', ['收款信息']);
});

onMounted(() => {
  // 移除 onMounted 中的调用，改由 Modal onOpenChange 触发
});
// 项目搜索状态
const projectKeyword = ref('');
const projectOptions = ref<any[]>([]);
const projectLoading = ref(false);

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

const [Form, formApi] = useVbenForm({
  commonConfig: {
    componentProps: {
      class: 'w-full',
    },
    formItemClass: 'col-span-2',
    labelWidth: 80,
  },
  layout: 'horizontal',
  schema: useFormSchema({ onProjectTypeChange: handleProjectTypeChange }),
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
    const data = (await formApi.getValues()) as ReceiptOrderApi.ReceiptOrder;
    try {
      await (formData.value?.id
        ? updateReceiptOrder(data)
        : createReceiptOrder(data));
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
    let data = modalApi.getData<ReceiptOrderApi.ReceiptOrder>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getReceiptOrder(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    // 设置到 values
    formData.value = data;
    // // 处理时间戳字段，转换为 dayjs 对象以适配 DatePicker (valueFormat: 'x')
    // const formattedData = {
    //   ...formData.value,
    //   receiptDate: formData.value.receiptDate
    //     ? dayjs(Number(formData.value.receiptDate))
    //     : undefined,
    // };
    await formApi.setValues(formData.value);

    // 加载项目列表
    loadProjects();
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
    </Form>
  </Modal>
</template>
