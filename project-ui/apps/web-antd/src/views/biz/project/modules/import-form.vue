<script lang="ts" setup>
import { importProject, importProjectTemplate } from '#/api/biz/project';
import { ImportExcel } from '#/components/import-excel';

import { projectImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入项目信息"
    :import-api="importProject"
    :template-api="importProjectTemplate"
    template-file-name="项目信息导入模板.xls"
    :form-schema="projectImportFormSchema"
    @success="emit('success')"
  />
</template>
