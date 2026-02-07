import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { WorkerApi } from '#/api/biz/worker';

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
      fieldName: 'workerName',
      label: '工人姓名',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工人姓名',
      },
    },
    {
      fieldName: 'gender',
      label: '性别',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.SYSTEM_USER_SEX, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'idCardNo',
      label: '身份证号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入身份证号',
      },
    },
    {
      fieldName: 'phone',
      label: '联系电话',
      component: 'Input',
      componentProps: {
        placeholder: '请输入联系电话',
      },
    },
    {
      fieldName: 'workerType',
      label: '工人类型',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_WORKER_TYPE, 'string'),
        placeholder: '请选择工人类型',
      },
    },
    {
      fieldName: 'workType',
      label: '工种',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_WORK_TYPE, 'string'),
        placeholder: '请选择工种',
      },
    },
    {
      fieldName: 'dailySalary',
      label: '日薪',
      component: 'InputNumber',
      componentProps: {
        precision: 2,
        min: 0,
        placeholder: '请输入日薪',
      },
    },
    {
      fieldName: 'monthlySalary',
      label: '月薪',
      component: 'InputNumber',
      componentProps: {
        precision: 2,
        min: 0,
        placeholder: '请输入月薪',
      },
    },
    {
      fieldName: 'salaryDesc',
      label: '薪资说明',
      component: 'Input',
      componentProps: {
        placeholder: '请输入薪资说明',
      },
    },
    {
      fieldName: 'skillLevel',
      label: '技能等级',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_SKILL_LEVEL, 'string'),
        placeholder: '请选择技能等级',
      },
    },
    {
      fieldName: 'status',
      label: '状态',
      rules: 'required',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_STATUS, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
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
      fieldName: 'workerName',
      label: '工人姓名',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工人姓名',
      },
    },
    {
      fieldName: 'gender',
      label: '性别',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.SYSTEM_USER_SEX, 'string'),
        placeholder: '请选择性别',
      },
    },
    {
      fieldName: 'idCardNo',
      label: '身份证号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入身份证号',
      },
    },
    {
      fieldName: 'phone',
      label: '联系电话',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入联系电话',
      },
    },
    {
      fieldName: 'workerType',
      label: '工人类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_WORKER_TYPE, 'string'),
        placeholder: '请选择工人类型',
      },
    },
    {
      fieldName: 'workType',
      label: '工种',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_WORK_TYPE, 'string'),
        placeholder: '请选择工种',
      },
    },
    {
      fieldName: 'skillLevel',
      label: '技能等级',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_SKILL_LEVEL, 'string'),
        placeholder: '请选择技能等级',
      },
    },
    {
      fieldName: 'status',
      label: '状态',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_WORKER_STATUS, 'string'),
        placeholder: '请选择状态',
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
export function useGridColumns(): VxeTableGridOptions<WorkerApi.Worker>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'workerName',
      title: '工人姓名',
      minWidth: 120,
    },
    {
      field: 'gender',
      title: '性别',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.SYSTEM_USER_SEX },
      },
    },
    {
      field: 'idCardNo',
      title: '身份证号',
      minWidth: 120,
    },
    {
      field: 'phone',
      title: '联系电话',
      minWidth: 120,
    },
    {
      field: 'workerType',
      title: '工人类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_WORKER_WORKER_TYPE },
      },
    },
    {
      field: 'workType',
      title: '工种',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_WORKER_WORK_TYPE },
      },
    },
    {
      field: 'dailySalary',
      title: '日薪',
      minWidth: 120,
    },
    {
      field: 'monthlySalary',
      title: '月薪',
      minWidth: 120,
    },
    {
      field: 'salaryDesc',
      title: '薪资说明',
      minWidth: 120,
    },
    {
      field: 'skillLevel',
      title: '技能等级',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_WORKER_SKILL_LEVEL },
      },
    },
    {
      field: 'status',
      title: '状态',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_WORKER_STATUS },
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
