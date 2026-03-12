<script lang="ts" setup>
import {
  importPurchaseOrder,
  importPurchaseOrderTemplate,
} from '#/api/biz/purchaseOrder';
import { ImportExcel } from '#/components/import-excel';
import { purchaseOrderImportFormSchema } from '#/views/biz/purchaseOrder/data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入采购信息"
    :import-api="importPurchaseOrder"
    :template-api="importPurchaseOrderTemplate"
    template-file-name="采购信息导入模板.xls"
    :form-schema="purchaseOrderImportFormSchema"
    @success="emit('success')"
  />
</template>
