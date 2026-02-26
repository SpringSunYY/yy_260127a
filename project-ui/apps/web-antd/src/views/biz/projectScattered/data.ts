import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ProjectScatteredApi } from '#/api/biz/projectScattered';

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
      fieldName: 'projectId',
      label: '项目编号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目编号',
      },
    },
    // {
    //   fieldName: 'projectNo',
    //   label: '项目编号',
    //   rules: 'required',
    //   component: 'Input',
    //   componentProps: {
    //     placeholder: '请输入项目编号',
    //   },
    // },
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
      fieldName: 'scatteredName',
      label: '工程名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工程名称',
      },
    },
    {
      fieldName: 'scatteredTime',
      label: '时间',
      rules: 'required',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
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
      fieldName: 'completedImage',
      label: '竣工图',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择竣工图',
      },
    },
    {
      fieldName: 'verification',
      label: '现场核销',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择现场核销',
      },
    },
    {
      fieldName: 'appendixFile',
      label: '附件',
      component: 'FileUpload',
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
    // {
    //   fieldName: 'projectId',
    //   label: '项目编号',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入项目编号',
    //   },
    // },
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
      fieldName: 'projectName',
      label: '项目名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'scatteredName',
      label: '工程名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工程名称',
      },
    },
    {
      fieldName: 'scatteredTime',
      label: '时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
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
      fieldName: 'completedImage',
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
export function useGridColumns(): VxeTableGridOptions<ProjectScatteredApi.ProjectScattered>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'projectId',
      title: '项目编号',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'projectNo',
      title: '项目编号',
      minWidth: 120,
    },
    {
      field: 'projectName',
      title: '项目名称',
      minWidth: 120,
    },
    {
      field: 'scatteredName',
      title: '工程名称',
      minWidth: 120,
    },
    {
      field: 'scatteredTime',
      title: '时间',
      minWidth: 120,
      formatter: 'formatDate',
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
      field: 'completedImage',
      title: '竣工图',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
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
