import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { RawMaterialsApi } from '#/api/biz/rawMaterials';

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
      fieldName: 'materialName',
      label: '材料名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入材料名称',
      },
    },
    {
      fieldName: 'materialType',
      label: '规格类别',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_MATERIAL_TYPE, 'string'),
        placeholder: '请选择规格类别',
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
      fieldName: 'materialName',
      label: '材料名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入材料名称',
      },
    },
    {
      fieldName: 'materialType',
      label: '规格类别',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_MATERIAL_TYPE, 'string'),
        placeholder: '请选择规格类别',
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
export function useGridColumns(): VxeTableGridOptions<RawMaterialsApi.RawMaterials>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'materialName',
      title: '材料名称',
      minWidth: 120,
    },
    {
      field: 'materialType',
      title: '规格类别',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_MATERIAL_TYPE },
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

/** 客户信息导入的表单 */
export function rawMaterialsImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '原材料信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
  ];
}
