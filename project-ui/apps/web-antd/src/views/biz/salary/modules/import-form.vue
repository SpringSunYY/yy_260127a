<script lang="ts" setup>
import { ImportExcel } from '#/components/import-excel';
import { importSalary, importSalaryTemplate } from '#/api/biz/salary';

import { salaryImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入工资"
    :import-api="importSalary"
    :template-api="importSalaryTemplate"
    template-file-name="工资导入模板.xls"
    :form-schema="salaryImportFormSchema"
    @success="emit('success')"
  />
</template>
