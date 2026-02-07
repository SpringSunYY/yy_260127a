<script lang="ts" setup>
import type { WorkerApi } from '#/api/biz/worker';

import { useVbenModal } from '@vben/common-ui';
import { message, Tabs, Checkbox, Input, Textarea, Select,RadioGroup,CheckboxGroup, DatePicker } from 'ant-design-vue';

import { computed, ref } from 'vue';
import { $t } from '#/locales';
import { useVbenForm } from '#/adapter/form';
import { getWorker, createWorker, updateWorker } from '#/api/biz/worker';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
const formData = ref<WorkerApi.Worker>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['工人信息'])
    : $t('ui.actionTitle.create', ['工人信息']);
});


const [Form, formApi] = useVbenForm({
  commonConfig: {
    componentProps: {
      class: 'w-full',
    },
    formItemClass: 'col-span-2',
    labelWidth: 80
  },
  layout: 'horizontal',
  schema: useFormSchema(),
  showDefaultActions: false
});

const [Modal, modalApi] = useVbenModal({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) {
      return;
    }
        modalApi.lock();
    // 提交表单
    const data = (await formApi.getValues()) as WorkerApi.Worker;
        try {
      await (formData.value?.id ? updateWorker(data) : createWorker(data));
      // 关闭并提示
      await modalApi.close();
      emit('success');
      message.success( $t('ui.actionMessage.operationSuccess') );
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
    let data = modalApi.getData<WorkerApi.Worker>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getWorker(data.id);
      } finally {
        modalApi.unlock();
      }
    }
    // 设置到 values
    formData.value = data;
    await formApi.setValues(formData.value);
  }
});
</script>

<template>
  <Modal :title="getTitle">
    <Form class="mx-4" />
      </Modal>
</template>