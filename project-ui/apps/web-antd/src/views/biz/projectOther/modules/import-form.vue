<script lang="ts" setup>
import {
  importProjectOther,
  importProjectOtherTemplate,
} from '#/api/biz/projectOther';
import { ImportExcel } from '#/components/import-excel';

import { projectOtherImportFormSchema } from '../data';

const emit = defineEmits(['success']);
</script>

<template>
  <ImportExcel
    title="导入其他项目信息"
    :import-api="importProjectOther"
    :template-api="importProjectOtherTemplate"
    template-file-name="其他项目信息导入模板.xls"
    :form-schema="projectOtherImportFormSchema"
    @success="emit('success')"
  />
</template>
