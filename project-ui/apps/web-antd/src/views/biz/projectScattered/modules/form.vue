<script lang="ts" setup>
import type { ProjectScatteredApi } from '#/api/biz/projectScattered';

import { computed, onMounted, ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';

import { useDebounceFn } from '@vueuse/core';
import { message, Select } from 'ant-design-vue';

import { useVbenForm } from '#/adapter/form';
import { getProjectPage } from '#/api/biz/project';
import {
  createProjectScattered,
  getProjectScattered,
  updateProjectScattered,
} from '#/api/biz/projectScattered';
import { $t } from '#/locales';

import { useFormSchema } from '../data';

const emit = defineEmits(['success']);
onMounted(() => {
  loadProjects();
});
// 项目搜索状态
const projectKeyword = ref('');
const projectOptions = ref<any[]>([]);
const projectLoading = ref(false);

// 加载项目列表
const loadProjects = async (keyword?: string) => {
  projectLoading.value = true;
  try {
    const res = await getProjectPage({
      pageNo: 1,
      pageSize: 50,
      name: keyword || '',
      projectType: '1',
    });
    projectOptions.value = res.list || [];
  } finally {
    projectLoading.value = false;
  }
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

const formData = ref<ProjectScatteredApi.ProjectScattered>();
const getTitle = computed(() => {
  return formData.value?.id
    ? $t('ui.actionTitle.edit', ['零散工程'])
    : $t('ui.actionTitle.create', ['零散工程']);
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
    const data =
      (await formApi.getValues()) as ProjectScatteredApi.ProjectScattered;
    try {
      await (formData.value?.id
        ? updateProjectScattered(data)
        : createProjectScattered(data));
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
    let data = modalApi.getData<ProjectScatteredApi.ProjectScattered>();
    if (!data) {
      return;
    }
    if (data.id) {
      modalApi.lock();
      try {
        data = await getProjectScattered(data.id);
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
