<script lang="ts" setup>
import {
  importProjectScattered,
  importProjectScatteredTemplate,
} from '#/api/biz/projectScattered';
import { ImportExcel } from '#/components/import-excel';
import { projectScatteredImportFormSchema } from '#/views/biz/projectScattered/data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入零散工程信息"
    :import-api="importProjectScattered"
    :template-api="importProjectScatteredTemplate"
    template-file-name="零散工程信息导入模板.xls"
    :form-schema="projectScatteredImportFormSchema"
    @success="emit('success')"
  />
</template>
