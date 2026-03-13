<script lang="ts" setup>
import { importWorker, importWorkerTemplate } from '#/api/biz/worker';
import { ImportExcel } from '#/components/import-excel';
import { workerImportFormSchema } from '#/views/biz/worker/data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入工人信息"
    :import-api="importWorker"
    :template-api="importWorkerTemplate"
    template-file-name="工人信息导入模板.xls"
    :form-schema="workerImportFormSchema"
    @success="emit('success')"
  />
</template>
