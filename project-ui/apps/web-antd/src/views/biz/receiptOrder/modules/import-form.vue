<script lang="ts" setup>
import { ImportExcel } from '#/components/import-excel';
import {
  importReceiptOrder,
  importReceiptOrderTemplate,
} from '#/api/biz/receiptOrder';

import { receiptOrderImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入收款信息"
    :import-api="importReceiptOrder"
    :template-api="importReceiptOrderTemplate"
    template-file-name="收款信息导入模板.xls"
    :form-schema="receiptOrderImportFormSchema"
    @success="emit('success')"
  />
</template>
