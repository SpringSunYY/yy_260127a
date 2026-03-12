<script lang="ts" setup>
import {
  importRawMaterials,
  importRawMaterialsTemplate,
} from '#/api/biz/rawMaterials';
import { ImportExcel } from '#/components/import-excel';

import { rawMaterialsImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入原材料信息"
    :import-api="importRawMaterials"
    :template-api="importRawMaterialsTemplate"
    template-file-name="原材料信息导入模板.xls"
    :form-schema="rawMaterialsImportFormSchema"
    @success="emit('success')"
  />
</template>
