import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SalaryApi } from '#/api/biz/salary';

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
      fieldName: 'name',
      label: '名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入名称',
      },
    },
    {
      fieldName: 'workerId',
      label: '工人编号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工人编号',
      },
    },
    {
      fieldName: 'workerName',
      label: '工人姓名',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工人姓名',
      },
    },
    {
      fieldName: 'isSettlement',
      label: '是否结算',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
      dependencies: {
        triggerFields: ['id'],
        show: (values) => !!values.id,
      },
    },
    {
      fieldName: 'salaryCycleTime',
      label: '工资周期',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'attendanceDays',
      label: '出勤天数',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入出勤天数',
      },
    },
    {
      fieldName: 'overtimeDays',
      label: '加班天数',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入加班天数',
      },
    },
    {
      fieldName: 'laborFeeAmount',
      label: '劳务费金额',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入劳务费金额',
      },
    },
    {
      fieldName: 'overtimeFee',
      label: '加班费',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入加班费',
      },
    },
    {
      fieldName: 'allowanceAmount',
      label: '补贴',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入补贴',
      },
    },
    {
      fieldName: 'subtotalAmount',
      label: '小计',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入小计',
      },
    },
    {
      fieldName: 'socialInsurance',
      label: '社保',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入社保',
      },
    },
    {
      fieldName: 'deduction',
      label: '扣款',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入扣款',
      },
    },
    {
      fieldName: 'payableAmount',
      label: '应发款项',
      rules: 'required',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入应发款项',
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
      fieldName: 'name',
      label: '名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入名称',
      },
    },
    {
      fieldName: 'workerName',
      label: '工人姓名',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工人姓名',
      },
    },
    {
      fieldName: 'isSettlement',
      label: '是否结算',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择是否结算',
      },
    },
    {
      fieldName: 'salaryCycleTime',
      label: '工资周期',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
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
export function useGridColumns(): VxeTableGridOptions<SalaryApi.Salary>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'name',
      title: '名称',
      minWidth: 120,
    },
    {
      field: 'workerId',
      title: '工人编号',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'workerName',
      title: '工人姓名',
      minWidth: 120,
    },
    {
      field: 'isSettlement',
      title: '是否结算',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'salaryCycleTime',
      title: '工资周期',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'attendanceDays',
      title: '出勤天数',
      minWidth: 120,
    },
    {
      field: 'overtimeDays',
      title: '加班天数',
      minWidth: 120,
    },
    {
      field: 'laborFeeAmount',
      title: '劳务费金额',
      minWidth: 120,
    },
    {
      field: 'overtimeFee',
      title: '加班费',
      minWidth: 120,
    },
    {
      field: 'allowanceAmount',
      title: '补贴',
      minWidth: 120,
    },
    {
      field: 'subtotalAmount',
      title: '小计',
      minWidth: 120,
    },
    {
      field: 'socialInsurance',
      title: '社保',
      minWidth: 120,
    },
    {
      field: 'deduction',
      title: '扣款',
      minWidth: 120,
    },
    {
      field: 'payableAmount',
      title: '应发款项',
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

/** 工资导入的表单 */
export function salaryImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '工资数据',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
  ];
}
