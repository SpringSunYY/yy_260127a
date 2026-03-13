<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { WorkerApi } from '#/api/biz/worker';

import { ref } from 'vue';

import { Page, useVbenModal } from '@vben/common-ui';
import { downloadFileFromBlobPart, isEmpty } from '@vben/utils';

import { message } from 'ant-design-vue';

import { ACTION_ICON, TableAction, useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  deleteWorker,
  deleteWorkerList,
  exportWorker,
  getWorkerPage,
} from '#/api/biz/worker';
import { $t } from '#/locales';
import ImportForm from '#/views/biz/worker/modules/import-form.vue';

import { useGridColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';

type SortOrder = 'asc' | 'desc';

function normalizeSortOrder(order: unknown): SortOrder | undefined {
  if (!order) return undefined;
  const str = String(order).toLowerCase();
  if (str === 'asc' || str === 'ascend') return 'asc';
  if (str === 'desc' || str === 'descend') return 'desc';
  return undefined;
}

function pickSort(ctx: any): { order?: SortOrder; orderBy?: string } {
  // vxe-table 可能传 sort（单列）或 sorts（多列）
  const sorts = Array.isArray(ctx?.sorts) ? ctx.sorts : undefined;
  const first =
    (sorts && sorts.find((s: any) => s?.order)) ||
    (sorts && sorts[0]) ||
    ctx?.sort ||
    undefined;
  const orderBy = first?.field ? String(first.field) : undefined;
  const order = normalizeSortOrder(first?.order);
  return { orderBy, order };
}

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [ImportModal, importModalApi] = useVbenModal({
  connectedComponent: ImportForm,
  destroyOnClose: true,
});

function handleImport() {
  importModalApi.open();
}

/** 刷新表格 */
function onRefresh() {
  gridApi.query();
}

/** 创建工人信息 */
function handleCreate() {
  formModalApi.setData({}).open();
}

/** 编辑工人信息 */
function handleEdit(row: WorkerApi.Worker) {
  formModalApi.setData(row).open();
}

/** 删除工人信息 */
async function handleDelete(row: WorkerApi.Worker) {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting', [row.id]),
    key: 'action_key_msg',
  });
  try {
    await deleteWorker(row.id as number);
    message.success({
      content: $t('ui.actionMessage.deleteSuccess', [row.id]),
      key: 'action_key_msg',
    });
    onRefresh();
  } finally {
    hideLoading();
  }
}

/** 批量删除工人信息 */
async function handleDeleteBatch() {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting'),
    key: 'action_key_msg',
  });
  try {
    await deleteWorkerList(checkedIds.value);
    message.success({
      content: $t('ui.actionMessage.deleteSuccess'),
      key: 'action_key_msg',
    });
    onRefresh();
  } finally {
    hideLoading();
  }
}

const checkedIds = ref<number[]>([]);

function handleRowCheckboxChange({ records }: { records: WorkerApi.Worker[] }) {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出表格 */
async function handleExport() {
  const data = await exportWorker(await gridApi.formApi.getValues());
  downloadFileFromBlobPart({ fileName: '工人信息.xls', source: data });
}

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    schema: useGridFormSchema(),
  },
  gridOptions: {
    columns: useGridColumns(),
    height: 'auto',
    pagerConfig: {
      enabled: true,
    },
    sortConfig: {
      remote: true,
      // 仅允许单列排序：点新列时清除旧列排序状态
      multiple: false,
    },
    proxyConfig: {
      ajax: {
        query: async (ctx, formValues) => {
          const { page } = ctx || {};
          const { orderBy, order } = pickSort(ctx);
          return await getWorkerPage({
            pageNo: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
            ...(orderBy && order ? { orderBy, order } : {}),
          });
        },
      },
    },
    rowConfig: {
      keyField: 'id',
      isHover: true,
    },
    toolbarConfig: {
      refresh: { code: 'query' },
      search: true,
    },
  } as VxeTableGridOptions<WorkerApi.Worker>,
  gridEvents: {
    checkboxAll: handleRowCheckboxChange,
    checkboxChange: handleRowCheckboxChange,
    sortChange: () => gridApi.query(),
  },
});
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <ImportModal @success="onRefresh" />

    <Grid table-title="工人信息列表">
      <template #toolbar-tools>
        <TableAction
          :actions="[
            {
              label: $t('ui.actionTitle.create', ['工人信息']),
              type: 'primary',
              icon: ACTION_ICON.ADD,
              auth: ['biz:worker:create'],
              onClick: handleCreate,
            },
            {
              label: $t('ui.actionTitle.export'),
              type: 'primary',
              icon: ACTION_ICON.DOWNLOAD,
              auth: ['biz:worker:export'],
              onClick: handleExport,
            },
            {
              label: $t('ui.actionTitle.deleteBatch'),
              type: 'primary',
              danger: true,
              icon: ACTION_ICON.DELETE,
              disabled: isEmpty(checkedIds),
              auth: ['biz:worker:delete'],
              onClick: handleDeleteBatch,
            },
            {
              label: $t('ui.actionTitle.import', ['工人']),
              type: 'primary',
              icon: ACTION_ICON.UPLOAD,
              auth: ['biz:worker:create'],
              onClick: handleImport,
            },
          ]"
        />
      </template>
      <template #actions="{ row }">
        <TableAction
          :actions="[
            {
              label: $t('common.edit'),
              type: 'link',
              icon: ACTION_ICON.EDIT,
              auth: ['biz:worker:update'],
              onClick: handleEdit.bind(null, row),
            },
            {
              label: $t('common.delete'),
              type: 'link',
              danger: true,
              icon: ACTION_ICON.DELETE,
              auth: ['biz:worker:delete'],
              popConfirm: {
                title: $t('ui.actionMessage.deleteConfirm', [row.id]),
                confirm: handleDelete.bind(null, row),
              },
            },
          ]"
        />
      </template>
    </Grid>
  </Page>
</template>
