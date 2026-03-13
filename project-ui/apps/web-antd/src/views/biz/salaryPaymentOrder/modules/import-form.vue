<script lang="ts" setup>
import {
  importSalaryPaymentOrder,
  importSalaryPaymentOrderTemplate,
} from '#/api/biz/salaryPaymentOrder';
import { ImportExcel } from '#/components/import-excel';

import { salaryPaymentOrderImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入工资付款信息"
    :import-api="importSalaryPaymentOrder"
    :template-api="importSalaryPaymentOrderTemplate"
    template-file-name="工资付款信息导入模板.xls"
    :form-schema="salaryPaymentOrderImportFormSchema"
    @success="emit('success')"
  />
</template>
