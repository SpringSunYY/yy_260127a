import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { PurchaseOrderApi } from '#/api/biz/purchaseOrder';

import { DICT_TYPE, getDictOptions, getRangePickerDefaultProps } from '#/utils';

/** 新增/修改的表单 */
export function useFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'id',
      component: 'Input',
      dependencies: {
        triggerFields: [''],
        show: () => false,
      },
    },
    {
      fieldName: 'orderNo',
      label: '采购单号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入采购单号',
      },
      dependencies: {
        triggerFields: ['id'],
        disabled: (values) => !!values.id,
        show: (values) => !!values.id,
      },
    },
    {
      fieldName: 'name',
      label: '采购名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入采购名称',
      },
    },
    {
      fieldName: 'supplierId',
      label: '供应商',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请选择供应商',
      },
    },
    {
      fieldName: 'supplierName',
      label: '供应商名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入供应商名称',
      },
    },
    {
      fieldName: 'purchaseUserId',
      label: '采购人ID',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入采购人ID',
      },
    },
    {
      fieldName: 'purchaserUserName',
      label: '采购人',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入采购人',
      },
    },
    {
      fieldName: 'expectedTime',
      label: '期望到货日期',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'actualTime',
      label: '实际到货日期',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'orderStatus',
      rules: 'required',
      label: '状态',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_PURCHASE_ORDER_STATUS, 'string'),
      },
    },
    {
      fieldName: 'totalAmount',
      label: '采购金额',
      component: 'InputNumber',
      disabled: true,
      componentProps: {
        placeholder: '请输入采购金额',
      },
    },
    {
      fieldName: 'remark',
      label: '备注',
      component: 'Textarea',
      formItemClass: 'col-span-2',
      componentProps: {
        placeholder: '请输入备注',
        rows: 3,
      },
    },
  ];
}

/** 列表的搜索表单 */
export function useGridFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'orderNo',
      label: '采购单号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入采购单号',
      },
    },
    {
      fieldName: 'name',
      label: '采购名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入采购名称',
      },
    },
    {
      fieldName: 'supplierId',
      label: '供应商ID',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请选择供应商',
      },
    },
    {
      fieldName: 'supplierName',
      label: '供应商名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入供应商名称',
      },
    },
    {
      fieldName: 'purchaseUserId',
      label: '采购人ID',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入采购人ID',
      },
    },
    {
      fieldName: 'purchaserUserName',
      label: '采购人',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入采购人',
      },
    },
    {
      fieldName: 'orderStatus',
      label: '采购状态',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_PURCHASE_ORDER_STATUS, 'string'),
        placeholder: '请选择采购状态',
      },
    },
    {
      fieldName: 'createTime',
      label: '创建时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
  ];
}

/** 列表的字段 */
export function useGridColumns(): VxeTableGridOptions<PurchaseOrderApi.PurchaseOrder>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'orderNo',
      title: '采购单号',
      minWidth: 120,
    },
    {
      field: 'name',
      title: '采购名称',
      minWidth: 120,
    },
    {
      field: 'supplierId',
      title: '供应商',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'supplierName',
      title: '供应商名称',
      minWidth: 120,
    },
    {
      field: 'purchaseUserId',
      title: '采购人ID',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'purchaserUserName',
      title: '采购人',
      minWidth: 120,
    },
    {
      field: 'totalAmount',
      title: '采购金额',
      minWidth: 120,
    },
    {
      field: 'totalQuantity',
      title: '采购数量',
      minWidth: 120,
    },
    {
      field: 'expectedTime',
      title: '期望到货日期',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'actualTime',
      title: '实际到货日期',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'orderStatus',
      title: '采购状态',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PURCHASE_ORDER_STATUS },
      },
    },
    {
      field: 'remark',
      title: '备注',
      minWidth: 120,
    },
    {
      field: 'createTime',
      title: '创建时间',
      minWidth: 120,
      formatter: 'formatDateTime',
    },
    {
      title: '操作',
      width: 200,
      fixed: 'right',
      slots: { default: 'actions' },
    },
  ];
}

// ==================== 子表（采购明细） ====================

/** 新增/修改列表的字段 */
export function usePurchaseOrderDetailGridEditColumns(): VxeTableGridOptions<PurchaseOrderApi.PurchaseOrderDetail>['columns'] {
  return [
    // {
    //   field: 'orderNo',
    //   title: '采购单号',
    //   minWidth: 120,
    //   slots: { default: 'orderNo' },
    // },
    {
      field: 'materialId',
      title: '材料ID',
      minWidth: 120,
      slots: { default: 'materialId' },
    },
    {
      field: 'materialName',
      title: '材料名称',
      minWidth: 120,
      slots: { default: 'materialName' },
    },
    {
      field: 'materialType',
      title: '规格型号',
      minWidth: 120,
      slots: { default: 'materialType' },
      params: {
        options: getDictOptions(DICT_TYPE.BIZ_MATERIAL_TYPE, 'string'),
      },
    },
    {
      field: 'quantity',
      title: '采购数量',
      minWidth: 120,
      slots: { default: 'quantity' },
    },
    {
      field: 'unitPrice',
      title: '采购单价',
      minWidth: 120,
      slots: { default: 'unitPrice' },
    },
    {
      field: 'totalPrice',
      title: '小计金额',
      minWidth: 120,
      slots: { default: 'totalPrice' },
    },
    {
      field: 'remark',
      title: '备注',
      minWidth: 120,
      slots: { default: 'remark' },
    },
    {
      title: '操作',
      width: 200,
      fixed: 'right',
      slots: { default: 'actions' },
    },
  ];
}

/** 采购信息导入的表单 */
export function purchaseOrderImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '采购信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件，导入时如果有总金额会更新供应商应付款金额',
    },
  ];
}

/** 采购明细信息导入的表单 */
export function purchaseOrderDetailImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '采购明细信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件，导入时会重新计算总金额并更新供应商应付款金额',
    },
  ];
}
