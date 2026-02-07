import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ProjectOtherApi } from '#/api/biz/projectOther';

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
      fieldName: 'projectName',
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
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_TYPE,
          'string',
        ),
        placeholder: '请选择项目类型',
      },
    },
    {
      fieldName: 'projectAddress',
      label: '地址',
      component: 'Input',
      componentProps: {
        placeholder: '请输入地址',
      },
    },
    {
      fieldName: 'projectDate',
      label: '时间',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'constructionFee',
      label: '施工费',
      component: 'InputNumber',
      componentProps: {
        precision: 2,
        min: 0,
        placeholder: '请输入施工费',
      },
    },
    {
      fieldName: 'isSettled',
      label: '已结算',
      rules: 'required',
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        buttonStyle: 'solid',
        optionType: 'button',
      },
    },
    {
      fieldName: 'appendixFile',
      label: '附件',
      component: 'FileUpload',
    },
    {
      fieldName: 'materialDesc',
      label: '材料说明',
      component: 'Input',
      componentProps: {
        placeholder: '请输入材料说明',
      },
    },
    {
      fieldName: 'progressStatus',
      label: '进度',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_PROGRESS,
          'string',
        ),
        placeholder: '请选择进度',
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
      fieldName: 'projectName',
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
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_TYPE,
          'string',
        ),
        placeholder: '请选择项目类型',
      },
    },
    {
      fieldName: 'projectAddress',
      label: '地址',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入地址',
      },
    },
    {
      fieldName: 'projectDate',
      label: '时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    {
      fieldName: 'isSettled',
      label: '已结算',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择已结算',
      },
    },
    {
      fieldName: 'progressStatus',
      label: '进度',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_PROGRESS,
          'string',
        ),
        placeholder: '请选择进度',
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
export function useGridColumns(): VxeTableGridOptions<ProjectOtherApi.ProjectOther>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'projectName',
      title: '项目名称',
      minWidth: 120,
    },
    {
      field: 'projectType',
      title: '项目类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_TYPE },
      },
    },
    {
      field: 'projectAddress',
      title: '地址',
      minWidth: 120,
    },
    {
      field: 'projectDate',
      title: '时间',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'constructionFee',
      title: '施工费',
      minWidth: 120,
    },
    {
      field: 'isSettled',
      title: '已结算',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'appendixFile',
      title: '附件',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'materialDesc',
      title: '材料说明',
      minWidth: 120,
    },
    {
      field: 'progressStatus',
      title: '进度',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_OTHER_PROJECT_PROGRESS },
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
