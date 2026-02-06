import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ProjectApi } from '#/api/biz/project';

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
      fieldName: 'projectNo',
      label: '项目编号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目编号',
      },
    },
    {
      fieldName: 'name',
      label: '项目名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'projectType',
      label: '项目类型',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_PROJECT_TYPE, 'string'),
        placeholder: '请选择项目类型',
      },
    },
    {
      fieldName: 'contractNumber',
      label: '合同编号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入合同编号',
      },
    },
    {
      fieldName: 'engineeringType',
      label: '工程类型',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_ENGINEERING_TYPE,
          'string',
        ),
        placeholder: '请选择工程类型',
      },
    },
    {
      fieldName: 'isPmc',
      label: '属于PMC',
      rules: 'required',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'customerId',
      label: '服务商编号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入服务商编号',
      },
    },
    {
      fieldName: 'customerName',
      label: '服务商名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入服务商名称',
      },
    },
    {
      fieldName: 'deliverTime',
      label: '交底时间',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'fiscalYear',
      label: '财年',
      component: 'InputNumber',
      componentProps: {
        placeholder: '请输入财年',
      },
    },
    // {
    //   fieldName: 'visaNum',
    //   label: '签证数',
    //   component: 'Input',
    //   componentProps: {
    //     placeholder: '请输入签证数',
    //   },
    // },
    {
      fieldName: 'projectProgress',
      label: '工程阶段',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_PROJECT_PROGRESS,
          'string',
        ),
        placeholder: '请选择工程阶段',
      },
    },
    {
      fieldName: 'isCompleted',
      label: '竣工图',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'completedFile',
      label: '竣工资料',
      component: 'FileUpload',
    },
    {
      fieldName: 'verification',
      label: '现场核销',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'determinedQuantity',
      label: '竣工工程确定量',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'materialVerification',
      label: '材料核销',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'settlementFile',
      label: '结算审定书',
      component: 'FileUpload',
    },
    {
      fieldName: 'appendixFile',
      label: '附件',
      component: 'FileUpload',
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
      fieldName: 'projectNo',
      label: '项目编号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入项目编号',
      },
    },
    {
      fieldName: 'name',
      label: '项目名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'projectType',
      label: '项目类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_PROJECT_TYPE, 'string'),
        placeholder: '请选择项目类型',
      },
    },
    {
      fieldName: 'contractNumber',
      label: '合同编号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入合同编号',
      },
    },
    {
      fieldName: 'engineeringType',
      label: '工程类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_PROJECT_PROGRESS,
          'string',
        ),
        placeholder: '请选择工程类型',
      },
    },
    {
      fieldName: 'isPmc',
      label: '属于PMC',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择属于PMC',
      },
    },
    {
      fieldName: 'customerName',
      label: '服务商名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入服务商名称',
      },
    },
    {
      fieldName: 'deliverTime',
      label: '交底时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    {
      fieldName: 'fiscalYear',
      label: '财年',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入财年',
      },
    },
    {
      fieldName: 'projectProgress',
      label: '工程阶段',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_PROJECT_PROGRESS,
          'string',
        ),
        placeholder: '请选择工程阶段',
      },
    },
    {
      fieldName: 'isCompleted',
      label: '竣工图',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择竣工图',
      },
    },
    {
      fieldName: 'verification',
      label: '现场核销',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择现场核销',
      },
    },
    {
      fieldName: 'determinedQuantity',
      label: '竣工工程确定量',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择竣工工程确定量',
      },
    },
    {
      fieldName: 'materialVerification',
      label: '材料核销',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择材料核销',
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
export function useGridColumns(): VxeTableGridOptions<ProjectApi.Project>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'projectNo',
      title: '项目编号',
      minWidth: 120,
    },
    {
      field: 'name',
      title: '项目名称',
      minWidth: 120,
    },
    {
      field: 'projectType',
      title: '项目类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_TYPE },
      },
    },
    {
      field: 'contractNumber',
      title: '合同编号',
      minWidth: 120,
    },
    {
      field: 'engineeringType',
      title: '工程类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_PROJECT_PROGRESS },
      },
    },
    {
      field: 'isPmc',
      title: '属于PMC',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'customerId',
      title: '服务商编号',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'customerName',
      title: '服务商名称',
      minWidth: 120,
    },
    {
      field: 'deliverTime',
      title: '交底时间',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'fiscalYear',
      title: '财年',
      minWidth: 120,
    },
    {
      field: 'projectProgress',
      title: '工程阶段',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_PROJECT_PROGRESS },
      },
    },
    {
      field: 'visaNum',
      title: '签证数',
      minWidth: 120,
    },
    {
      field: 'isCompleted',
      title: '竣工图',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'completedFile',
      title: '竣工资料',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'verification',
      title: '现场核销',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'determinedQuantity',
      title: '竣工工程确定量',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'materialVerification',
      title: '材料核销',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'settlementFile',
      title: '结算审定书',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'appendixFile',
      title: '附件',
      visible: false,
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
