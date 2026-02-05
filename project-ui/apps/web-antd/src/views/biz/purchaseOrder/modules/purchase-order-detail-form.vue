<script lang="ts" setup>
import type { OnActionClickParams } from '#/adapter/vxe-table';
import type { PurchaseOrderApi } from '#/api/biz/purchaseOrder';

import { h, nextTick, ref, watch } from 'vue';

import { Plus } from '@vben/icons';

import { useDebounceFn } from '@vueuse/core';
import { Button, Input, Select, Spin } from 'ant-design-vue';

import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { getPurchaseOrderDetailListByPurchaseId } from '#/api/biz/purchaseOrder';
import { getRawMaterialsPage } from '#/api/biz/rawMaterials';
import { $t } from '#/locales';

import { usePurchaseOrderDetailGridEditColumns } from '../data';

const props = defineProps<{
  purchaseId?: number; // 采购单编号（主表的关联字段）
}>();

// 原材料搜索相关
const rawMaterialsKeyword = ref('');
const rawMaterialsLoading = ref(false);
const rawMaterialsOptions = ref<any[]>([]);

/** 加载原材料列表 */
const loadRawMaterials = useDebounceFn(async (keyword?: string) => {
  rawMaterialsLoading.value = true;
  try {
    const res = await getRawMaterialsPage({
      pageNo: 1,
      pageSize: 50,
      materialName: keyword || '',
    });
    rawMaterialsOptions.value = res.list || [];
  } finally {
    rawMaterialsLoading.value = false;
  }
}, 300);

// 初始化加载原材料
loadRawMaterials();

/** 表格操作按钮的回调函数 */
function onActionClick(
  params: OnActionClickParams<PurchaseOrderApi.PurchaseOrderDetail>,
) {
  const { code, row } = params;
  switch (code) {
    case 'delete': {
      onDelete(row);
      break;
    }
  }
}

const [Grid, gridApi] = useVbenVxeGrid({
  gridOptions: {
    columns: usePurchaseOrderDetailGridEditColumns(onActionClick),
    border: true,
    showOverflow: true,
    autoResize: true,
    keepSource: true,
    rowConfig: {
      keyField: 'id',
    },
    pagerConfig: {
      enabled: false,
    },
    toolbarConfig: {
      enabled: false,
    },
  },
});

/** 添加采购明细 */
const onAdd = async () => {
  await gridApi.grid.insertAt({} as PurchaseOrderApi.PurchaseOrderDetail, -1);
};

/** 删除采购明细 */
const onDelete = async (row: PurchaseOrderApi.PurchaseOrderDetail) => {
  await gridApi.grid.remove(row);
};

/** 计算小计金额 */
const calculateTotalPrice = (row: PurchaseOrderApi.PurchaseOrderDetail) => {
  const quantity = Number.parseFloat(String(row.quantity || '0')) || 0;
  const unitPrice = Number.parseFloat(String(row.unitPrice || '0')) || 0;
  return (quantity * unitPrice).toFixed(2);
};

/** 数量或单价变化时更新小计 */
const onQuantityOrPriceChange = (row: PurchaseOrderApi.PurchaseOrderDetail) => {
  row.totalPrice = Number(calculateTotalPrice(row));
};

/** 原材料选择变化时联动填充其他字段 */
const onMaterialChange = (
  row: PurchaseOrderApi.PurchaseOrderDetail,
  value: number,
) => {
  if (value) {
    // 从选项中查找选中的原材料
    const option = rawMaterialsOptions.value.find((item) => item.id === value);
    if (option) {
      // 自动填充材料名称、规格型号、计量单位、采购单价
      row.materialId = value;
      row.materialName = option.materialName || '';
      row.materialSpec = option.materialSpec || '';
      row.unit = option.unit || '';
      row.unitPrice = option.unitPrice || 0;
      // 计算小计
      row.totalPrice = Number(calculateTotalPrice(row));
    }
  } else {
    // 清空时重置字段
    row.materialId = undefined;
    row.materialName = '';
    row.materialSpec = '';
    row.unit = '';
    row.unitPrice = 0;
    row.totalPrice = 0;
  }
};

/** 搜索原材料 */
const onSearchRawMaterials = useDebounceFn((value: string) => {
  rawMaterialsKeyword.value = value;
  loadRawMaterials(value);
}, 300);

/** 提供获取表格数据的方法供父组件调用 */
defineExpose({
  getData: (): PurchaseOrderApi.PurchaseOrderDetail[] => {
    const data =
      gridApi.grid.getData() as PurchaseOrderApi.PurchaseOrderDetail[];
    const removeRecords =
      gridApi.grid.getRemoveRecords() as PurchaseOrderApi.PurchaseOrderDetail[];
    const insertRecords =
      gridApi.grid.getInsertRecords() as PurchaseOrderApi.PurchaseOrderDetail[];
    return data
      .filter((row) => !removeRecords.some((removed) => removed.id === row.id))
      .concat(insertRecords.map((row: any) => ({ ...row, id: undefined })));
  },
});

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.purchaseId,
  async (val) => {
    if (!val) {
      return;
    }
    await nextTick();
    await gridApi.grid.loadData(
      await getPurchaseOrderDetailListByPurchaseId(props.purchaseId!),
    );
  },
  { immediate: true },
);
</script>

<template>
  <Grid class="mx-4">
    <!--    <template #orderNo="{ row }">-->
    <!--      <Input v-model:value="row.orderNo" />-->
    <!--    </template>-->
    <template #materialId="{ row }">
      <Select
        v-model:value="row.materialId"
        show-search
        class="w-full"
        placeholder="搜索并选择材料"
        :filter-option="false"
        :loading="rawMaterialsLoading"
        :options="rawMaterialsOptions"
        :field-names="{ label: 'materialName', value: 'id' }"
        @search="onSearchRawMaterials"
        @change="(value: string | number) => onMaterialChange(row, value as number)"
      >
        <template #notFoundContent>
          <Spin v-if="rawMaterialsLoading" size="small" />
          <span v-else>无数据</span>
        </template>
      </Select>
    </template>
    <template #materialName="{ row }">
      <Input v-model:value="row.materialName" />
    </template>
    <template #materialSpec="{ row }">
      <Input v-model:value="row.materialSpec" />
    </template>
    <template #quantity="{ row }">
      <Input
        v-model:value="row.quantity"
        type="number"
        @change="() => onQuantityOrPriceChange(row)"
      />
    </template>
    <template #unit="{ row, column }">
      <Select v-model:value="row.unit" class="w-full">
        <Select.Option
          v-for="option in column.params.options"
          :key="option.value"
          :value="option.value"
        >
          {{ option.label }}
        </Select.Option>
      </Select>
    </template>
    <template #unitPrice="{ row }">
      <Input
        v-model:value="row.unitPrice"
        type="number"
        @change="() => onQuantityOrPriceChange(row)"
      />
    </template>
    <template #totalPrice="{ row }">
      <Input v-model:value="row.totalPrice" disabled class="bg-gray-100" />
    </template>
    <template #remark="{ row }">
      <Input v-model:value="row.remark" />
    </template>
    <template #actions="{ row }">
      <Button type="link" danger size="small" @click="onDelete(row)">
        删除
      </Button>
    </template>
  </Grid>
  <div class="-mt-4 flex justify-center">
    <Button
      :icon="h(Plus)"
      type="primary"
      ghost
      @click="onAdd"
      v-access:code="['biz:purchase-order:create']"
    >
      {{ $t('ui.actionTitle.create', ['采购明细']) }}
    </Button>
  </div>
</template>
