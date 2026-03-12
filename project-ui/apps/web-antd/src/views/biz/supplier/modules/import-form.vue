<script lang="ts" setup>
import { importSupplier, importSupplierTemplate } from '#/api/biz/supplier';
import { ImportExcel } from '#/components/import-excel';

import { supplierImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入供应商信息"
    :import-api="importSupplier"
    :template-api="importSupplierTemplate"
    template-file-name="供应商信息导入模板.xls"
    :form-schema="supplierImportFormSchema"
    @success="emit('success')"
  />
</template>
