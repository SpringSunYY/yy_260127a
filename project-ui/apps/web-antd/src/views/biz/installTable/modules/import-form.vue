<script lang="ts" setup>
import {
  importInstallTable,
  importInstallTableTemplate,
} from '#/api/biz/installTable';
import { ImportExcel } from '#/components/import-excel';

import { installTableImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入装表信息"
    :import-api="importInstallTable"
    :template-api="importInstallTableTemplate"
    template-file-name="装表信息导入模板.xls"
    :form-schema="installTableImportFormSchema"
    @success="emit('success')"
  />
</template>
