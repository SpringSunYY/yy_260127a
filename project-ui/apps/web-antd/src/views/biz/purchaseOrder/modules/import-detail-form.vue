<script lang="ts" setup>
import {
  importPurchaseOrderDetail,
  importPurchaseOrderDetailTemplate,
} from '#/api/biz/purchaseOrder';
import { ImportExcel } from '#/components/import-excel';
import { purchaseOrderDetailImportFormSchema } from '#/views/biz/purchaseOrder/data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入采购明细信息"
    :import-api="importPurchaseOrderDetail"
    :template-api="importPurchaseOrderDetailTemplate"
    template-file-name="采购明细信息导入模板.xls"
    :form-schema="purchaseOrderDetailImportFormSchema"
    @success="emit('success')"
  />
</template>
