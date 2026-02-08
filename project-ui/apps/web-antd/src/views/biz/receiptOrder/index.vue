<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ReceiptOrderApi } from '#/api/biz/receiptOrder';

import { ref } from 'vue';

import { Page, useVbenModal } from '@vben/common-ui';
import { downloadFileFromBlobPart, isEmpty } from '@vben/utils';

import { message } from 'ant-design-vue';

import { ACTION_ICON, TableAction, useVbenVxeGrid } from '#/adapter/vxe-table';
import {
  deleteReceiptOrder,
  deleteReceiptOrderList,
  exportReceiptOrder,
  getReceiptOrderAmount,
  getReceiptOrderPage,
} from '#/api/biz/receiptOrder';
import { $t } from '#/locales';
import ImportForm from '#/views/biz/receiptOrder/modules/import-form.vue';

import { useGridColumns, useGridFormSchema } from './data';
import Form from './modules/form.vue';

const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true,
});

const [ImportModal, importModalApi] = useVbenModal({
  connectedComponent: ImportForm,
  destroyOnClose: true,
});

/** 刷新表格 */
function onRefresh() {
  gridApi.query();
}

/** 创建收款信息 */
function handleCreate() {
  formModalApi.setData({}).open();
}

/** 编辑收款信息 */
function handleEdit(row: ReceiptOrderApi.ReceiptOrder) {
  formModalApi.setData(row).open();
}

/** 删除收款信息 */
async function handleDelete(row: ReceiptOrderApi.ReceiptOrder) {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting', [row.id]),
    key: 'action_key_msg',
  });
  try {
    await deleteReceiptOrder(row.id as number);
    message.success({
      content: $t('ui.actionMessage.deleteSuccess', [row.id]),
      key: 'action_key_msg',
    });
    onRefresh();
  } finally {
    hideLoading();
  }
}

/** 批量删除收款信息 */
async function handleDeleteBatch() {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting'),
    key: 'action_key_msg',
  });
  try {
    await deleteReceiptOrderList(checkedIds.value);
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
const totalAmount = ref<number>(0);

function handleRowCheckboxChange({
  records,
}: {
  records: ReceiptOrderApi.ReceiptOrder[];
}) {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出表格 */
async function handleExport() {
  const data = await exportReceiptOrder(await gridApi.formApi.getValues());
  downloadFileFromBlobPart({ fileName: '收款信息.xls', source: data });
}

function handleImport() {
  importModalApi.open();
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
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          // 获取总金额
          try {
            totalAmount.value = await getReceiptOrderAmount(formValues);
          } catch (error) {
            console.error('Failed to fetch total amount', error);
          }

          return await getReceiptOrderPage({
            pageNo: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
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
  } as VxeTableGridOptions<ReceiptOrderApi.ReceiptOrder>,
  gridEvents: {
    checkboxAll: handleRowCheckboxChange,
    checkboxChange: handleRowCheckboxChange,
  },
});
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />
    <ImportModal @success="onRefresh" />
    <Grid>
      <template #table-title>
        <div class="flex items-center">
          <span class="mr-4 text-lg font-bold">收款信息列表</span>
          <span class="text-primary text-lg font-bold">
            总金额: {{ totalAmount }}
          </span>
        </div>
      </template>
      <template #toolbar-tools>
        <TableAction
          :actions="[
            {
              label: $t('ui.actionTitle.create', ['收款信息']),
              type: 'primary',
              icon: ACTION_ICON.ADD,
              auth: ['biz:receipt-order:create'],
              onClick: handleCreate,
            },
            {
              label: $t('ui.actionTitle.export'),
              type: 'primary',
              icon: ACTION_ICON.DOWNLOAD,
              auth: ['biz:receipt-order:export'],
              onClick: handleExport,
            },
            {
              label: $t('ui.actionTitle.deleteBatch'),
              type: 'primary',
              danger: true,
              icon: ACTION_ICON.DELETE,
              disabled: isEmpty(checkedIds),
              auth: ['biz:receipt-order:delete'],
              onClick: handleDeleteBatch,
            },
            {
              label: $t('ui.actionTitle.import', ['工资']),
              type: 'primary',
              icon: ACTION_ICON.UPLOAD,
              auth: ['biz:receipt-order:create'],
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
              auth: ['biz:receipt-order:update'],
              onClick: handleEdit.bind(null, row),
            },
            {
              label: $t('common.delete'),
              type: 'link',
              danger: true,
              icon: ACTION_ICON.DELETE,
              auth: ['biz:receipt-order:delete'],
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
