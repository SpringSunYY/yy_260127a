<script lang="ts" setup>
import {
  importProjectVisa,
  importProjectVisaTemplate,
} from '#/api/biz/projectVisa';
import { ImportExcel } from '#/components/import-excel';
import { projectVisaImportFormSchema } from '#/views/biz/projectVisa/data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入项目签证信息"
    :import-api="importProjectVisa"
    :template-api="importProjectVisaTemplate"
    template-file-name="项目签证信息导入模板.xls"
    :form-schema="projectVisaImportFormSchema"
    @success="emit('success')"
  />
</template>
