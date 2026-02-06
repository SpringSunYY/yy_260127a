<script lang="ts" setup>
import type { ProjectVisaApi } from '#/api/biz/projectVisa';

import { computed, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { message } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import {
  createProjectVisa,
  getProjectVisa,
  updateProjectVisa,
} from '#/api/biz/projectVisa';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<ProjectVisaApi.ProjectVisa>();
// 存储从路由传递过来的 projectId
const parentProjectId = ref<number>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['项目签证'])
    : $t('ui.actionTitle.create', ['项目签证']);
});

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
    const data = (await formApi.getValues()) as ProjectVisaApi.ProjectVisa;
    try {
      // 新增时，确保设置 projectId
      if (!data.id && parentProjectId.value) {
        data.projectId = parentProjectId.value;
      }
      await (formData.value?.id
        ? updateProjectVisa(data)
        : createProjectVisa(data));
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
      parentProjectId.value = undefined;
      return;
    }
    // 加载数据
    let data = modalApi.getData<ProjectVisaApi.ProjectVisa>();
    if (!data) {
      return;
    }
    // 保存 parentProjectId 用于新增时关联项目
    if (data.projectId) {
      parentProjectId.value = data.projectId;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getProjectVisa(data.id);
        parentProjectId.value = data.projectId;
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
    <Form class="mx-4" />
  </Modal>
</template>
