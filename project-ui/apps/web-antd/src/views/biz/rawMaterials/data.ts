import type {VbenFormSchema} from '#/adapter/form';
import type {VxeTableGridOptions} from '#/adapter/vxe-table';
import type {RawMaterialsApi} from '#/api/biz/rawMaterials';
import {DICT_TYPE, getDictOptions, getRangePickerDefaultProps} from '#/utils';

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
      fieldName: 'materialSpec',
      label: '规格型号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入规格型号',
      },
    },
    {
      fieldName: 'unit',
      label: '计量单位',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_RAW_MATERIALS_UNIT, 'string'),
        placeholder: '请选择计量单位',
      },
    },
    {
      fieldName: 'unitPrice',
      label: '采购单价',
      rules: 'required',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入采购单价',
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
      fieldName: 'materialSpec',
      label: '规格型号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入规格型号',
      },
    },
    {
      fieldName: 'unit',
      label: '计量单位',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_RAW_MATERIALS_UNIT, 'string'),
        placeholder: '请选择计量单位',
      },
    },
    {
      fieldName: 'unitPrice',
      label: '采购单价',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入采购单价',
      },
    },
    {
      fieldName: 'remark',
      label: '备注',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入备注',
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
    {type: 'checkbox', width: 40},
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
      field: 'materialSpec',
      title: '规格型号',
      minWidth: 120,
    },
    {
      field: 'unit',
      title: '计量单位',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: {type: DICT_TYPE.BIZ_RAW_MATERIALS_UNIT},
      },
    },
    {
      field: 'unitPrice',
      title: '采购单价',
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
      slots: {default: 'actions'},
    },
  ];
}

