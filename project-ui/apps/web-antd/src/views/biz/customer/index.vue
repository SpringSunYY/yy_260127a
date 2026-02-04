
芋道管理系统

概览
系统管理
基础设施
代码生成
数据源配置
表单构建
API 接口
API 日志
WebSocket
文件管理
定时任务
配置管理
监控中心
工程管理
工作流程

基础设施
代码生成

请选择租户
搜索Ctrl K
表名称
表描述
创建时间
收起
代码生成列表
数据源

表名称

表描述

实体

创建时间

更新时间

操作
master

biz_customer

客户信息

Customer

2026-02-04 14:29:50

2026-02-04 14:38:42

master

biz_install_table

装表信息

InstallTable

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_payment_order

付款信息

PaymentOrder

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_project

项目信息

Project

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_project_other

其他工程

ProjectOther

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_project_scattered

零散工程

ProjectScattered

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_project_visa

项目签证

ProjectVisa

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_purchase_order

采购信息

PurchaseOrder

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_purchase_order_detail

采购明细

PurchaseOrderDetail

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_raw_materials

原材料信息

RawMaterials

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_receipt_order

收款信息

ReceiptOrder

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_salary

工资信息

Salary

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_supplier

供应商信息

Supplier

2026-02-04 14:29:50

2026-02-04 14:29:50

master

biz_woker

工人信息

Woker

2026-02-04 14:29:50

2026-02-04 14:29:50

共 14 条记录
代码预览

CustomerPageReqVO.java
data.ts
index.vue
<script lang="ts" setup>
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { CustomerApi } from '#/api/biz/customer';

import { Page, useVbenModal } from '@vben/common-ui';
import { message,Tabs } from 'ant-design-vue';
import Form from './modules/form.vue';


import { ref, computed } from 'vue';
import { $t } from '#/locales';
import { ACTION_ICON, TableAction, useVbenVxeGrid } from '#/adapter/vxe-table';
import { getCustomerPage, deleteCustomer, deleteCustomerList, exportCustomer } from '#/api/biz/customer';
import { downloadFileFromBlobPart, isEmpty } from '@vben/utils';

import { useGridColumns, useGridFormSchema } from './data';


const [FormModal, formModalApi] = useVbenModal({
  connectedComponent: Form,
  destroyOnClose: true
});


/** 刷新表格 */
function onRefresh() {
  gridApi.query();
}

/** 创建客户信息 */
function handleCreate() {
  formModalApi.setData({}).open();
}

/** 编辑客户信息 */
function handleEdit(row: CustomerApi.Customer) {
  formModalApi.setData(row).open();
}


/** 删除客户信息 */
async function handleDelete(row: CustomerApi.Customer) {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting', [row.id]),
    key: 'action_key_msg'
  });
  try {
    await deleteCustomer(row.id as number);
    message.success({
      content: $t('ui.actionMessage.deleteSuccess', [row.id]),
      key: 'action_key_msg',
    });
    onRefresh();
  } finally {
    hideLoading();
  }
}

/** 批量删除客户信息 */
async function handleDeleteBatch() {
  const hideLoading = message.loading({
    content: $t('ui.actionMessage.deleting'),
    key: 'action_key_msg'
  });
  try {
    await deleteCustomerList(checkedIds.value);
    message.success({
      content: $t('ui.actionMessage.deleteSuccess'),
      key: 'action_key_msg',
    });
    onRefresh();
  } finally {
    hideLoading();
  }
}

const checkedIds = ref<number[]>([])
function handleRowCheckboxChange({
                                   records
                                 }: {
  records: CustomerApi.Customer[];
}) {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出表格 */
async function handleExport() {
  const data = await exportCustomer(await gridApi.formApi.getValues());
  downloadFileFromBlobPart({ fileName: '客户信息.xls', source: data });
}

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    schema: useGridFormSchema()
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
          return await getCustomerPage({
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
    }
  } as VxeTableGridOptions<CustomerApi.Customer>,
  gridEvents:{
    checkboxAll: handleRowCheckboxChange,
    checkboxChange: handleRowCheckboxChange
  }
});
</script>

<template>
  <Page auto-content-height>
    <FormModal @success="onRefresh" />

    <Grid table-title="客户信息列表">
      <template #toolbar-tools>
        <TableAction
          :actions="[
            {
              label: $t('ui.actionTitle.create', ['客户信息']),
              type: 'primary',
              icon: ACTION_ICON.ADD,
              auth: ['biz:customer:create'],
              onClick: handleCreate,
            },
            {
              label: $t('ui.actionTitle.export'),
              type: 'primary',
              icon: ACTION_ICON.DOWNLOAD,
              auth: ['biz:customer:export'],
              onClick: handleExport,
            },
            {
              label: $t('ui.actionTitle.deleteBatch'),
              type: 'primary',
              danger: true,
              icon: ACTION_ICON.DELETE,
              disabled: isEmpty(checkedIds),
              auth: ['biz:customer:delete'],
              onClick: handleDeleteBatch,
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
              auth: ['biz:customer:update'],
              onClick: handleEdit.bind(null, row),
            },
            {
              label: $t('common.delete'),
              type: 'link',
              danger: true,
              icon: ACTION_ICON.DELETE,
              auth: ['biz:customer:delete'],
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
