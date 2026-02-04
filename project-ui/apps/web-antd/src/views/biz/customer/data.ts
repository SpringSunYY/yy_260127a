import type {VbenFormSchema} from '#/adapter/form';
import type {VxeTableGridOptions} from '#/adapter/vxe-table';
import type {CustomerApi} from '#/api/biz/customer';
import {DICT_TYPE, getDictOptions, getRangePickerDefaultProps} from '#/utils';
import { z } from '#/adapter/form';
import {getAreaTree} from "#/api/system/area";
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
      label: '客户名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入客户名称',
      },
    },
    {
      fieldName: 'telephone',
      label: '电话',
      component: 'Input',
      componentProps: {
        placeholder: '请输入电话号码',
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
      fieldName: 'industry',
      label: '所属行业',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_CUSTOMER_INDUSTRY, 'string'),
        placeholder: '请选择所属行业',
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
      fieldName: 'id',
      label: '编号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入编号',
      },
    },
    {
      fieldName: 'name',
      label: '客户名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入客户名称',
      },
    },
    {
      fieldName: 'telephone',
      label: '电话',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入电话',
      },
    },
    {
      fieldName: 'qq',
      label: 'QQ',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入QQ',
      },
    },
    {
      fieldName: 'weChat',
      label: '微信',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入微信',
      },
    },
    {
      fieldName: 'email',
      label: '邮箱',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入邮箱',
      },
    },
    {
      fieldName: 'detailAddress',
      label: '详细地址',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入详细地址',
      },
    },
    {
      fieldName: 'industry',
      label: '所属行业',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_CUSTOMER_INDUSTRY, 'string'),
        placeholder: '请选择所属行业',
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
export function useGridColumns(): VxeTableGridOptions<CustomerApi.Customer>['columns'] {
  return [
  { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'name',
      title: '客户名称',
      minWidth: 120,
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
      field: 'industry',
      title: '所属行业',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_CUSTOMER_INDUSTRY },
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

