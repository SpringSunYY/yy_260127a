import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SupplierApi } from '#/api/biz/supplier';

import { z } from '#/adapter/form';
import { getAreaTree } from '#/api/system/area';
import { getRangePickerDefaultProps } from '#/utils';

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
      fieldName: 'name',
      label: '供应商名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入供应商名称',
      },
    },
    {
      fieldName: 'telephone',
      label: '电话',
      component: 'Input',
      componentProps: {
        placeholder: '请输入电话',
      },
    },
    {
      fieldName: 'qq',
      label: 'QQ',
      component: 'Input',
      componentProps: {
        placeholder: '请输入QQ',
      },
    },
    {
      fieldName: 'weChat',
      label: '微信',
      component: 'Input',
      componentProps: {
        placeholder: '请输入微信',
      },
    },
    {
      fieldName: 'email',
      label: '邮箱',
      component: 'Input',
      rules: z.string().email('邮箱格式不正确').or(z.literal('')).optional(),
      componentProps: {
        placeholder: '请输入邮箱',
      },
    },
    {
      fieldName: 'areaId',
      label: '地区编号',
      component: 'ApiTreeSelect',
      componentProps: {
        api: () => getAreaTree(),
        fieldNames: { label: 'name', value: 'id', children: 'children' },
      },
    },
    {
      fieldName: 'detailAddress',
      label: '详细地址',
      component: 'Input',
      componentProps: {
        placeholder: '请输入详细地址',
      },
    },
    {
      fieldName: 'paymentAmount',
      label: '付款金额',
      help: '后台自动计算，请勿随意修改',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入付款金额',
      },
    },
    {
      fieldName: 'debtAmount',
      label: '欠款金额',
      component: 'InputNumber',
      help: '后台自动计算，请勿随意修改',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入欠款金额',
      },
    },
    {
      fieldName: 'payableAmount',
      label: '应付金额',
      component: 'InputNumber',
      help: '后台自动计算，请勿随意修改',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入应付金额',
      },
    },
    {
      fieldName: 'remark',
      label: '备注',
      component: 'Input',
      componentProps: {
        placeholder: '请输入备注',
      },
    },
  ];
}

/** 列表的搜索表单 */
export function useGridFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'name',
      label: '供应商名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入供应商名称',
      },
    },
    // {
    //   fieldName: 'telephone',
    //   label: '电话',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入电话',
    //   },
    // },
    // {
    //   fieldName: 'qq',
    //   label: 'QQ',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入QQ',
    //   },
    // },
    // {
    //   fieldName: 'weChat',
    //   label: '微信',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入微信',
    //   },
    // },
    // {
    //   fieldName: 'email',
    //   label: '邮箱',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入邮箱',
    //   },
    // },
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
export function useGridColumns(): VxeTableGridOptions<SupplierApi.Supplier>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'name',
      title: '供应商名称',
      minWidth: 120,
    },
    {
      field: 'payableAmount',
      title: '应付金额',
      minWidth: 120,
      sortable: true,
    },
    {
      field: 'paymentAmount',
      title: '付款金额',
      minWidth: 120,
      sortable: true,
    },
    {
      field: 'debtAmount',
      title: '欠款金额',
      minWidth: 120,
      sortable: true,
    },
    {
      field: 'telephone',
      title: '电话',
      minWidth: 120,
    },
    {
      field: 'qq',
      title: 'QQ',
      minWidth: 120,
    },
    {
      field: 'weChat',
      title: '微信',
      minWidth: 120,
    },
    {
      field: 'email',
      title: '邮箱',
      minWidth: 120,
    },
    {
      field: 'areaId',
      title: '地区编号',
      minWidth: 120,
    },
    {
      field: 'detailAddress',
      title: '详细地址',
      minWidth: 120,
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

/** 供应商息导入的表单 */
export function supplierImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '供应商信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
  ];
}
