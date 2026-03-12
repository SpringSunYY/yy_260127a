<script lang="ts" setup>
import { importCustomer, importCustomerTemplate } from '#/api/biz/customer';
import { ImportExcel } from '#/components/import-excel';

import { customerImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入客户信息"
    :import-api="importCustomer"
    :template-api="importCustomerTemplate"
    template-file-name="客户信息导入模板.xls"
    :form-schema="customerImportFormSchema"
    @success="emit('success')"
  />
</template>
