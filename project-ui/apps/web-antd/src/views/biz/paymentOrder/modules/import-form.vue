<script lang="ts" setup>
import { ImportExcel } from '#/components/import-excel';
import {
  importPaymentOrder,
  importPaymentOrderTemplate,
} from '#/api/biz/paymentOrder';

import { pymentOrderImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入付款信息"
    :import-api="importPaymentOrder"
    :template-api="importPaymentOrderTemplate"
    template-file-name="付款信息导入模板.xls"
    :form-schema="pymentOrderImportFormSchema"
    @success="emit('success')"
  />
</template>
