import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ProjectVisaApi } from '#/api/biz/projectVisa';

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
    // 隐藏的 projectId 字段，新增时由父组件传递
    {
      fieldName: 'projectId',
      component: 'Input',
      label: '项目ID',
      dependencies: {
        triggerFields: [''],
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
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'visaName',
      label: '签证名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入签证名称',
      },
    },
    {
      fieldName: 'engineeringName',
      label: '工程名称',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工程名称',
      },
    },
    {
      fieldName: 'visaTime',
      label: '时间',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'amount',
      label: '金额',
      rules: 'required',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入金额',
      },
    },
    {
      fieldName: 'appendixFile',
      label: '附件',
      component: 'FileUpload',
    },
    {
      fieldName: 'visaContent',
      label: '签证内容',
      component: 'Textarea',
      componentProps: {
        placeholder: '请输入签证内容',
        rows: 3,
      },
    },
    {
      fieldName: 'remark',
      label: '备注',
      component: 'Input',
      componentProps: {
        placeholder: '请输入备注',
        row: 3,
      },
    },
  ];
}

/** 列表的搜索表单 */
export function useGridFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'projectId',
      label: '项目ID',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入项目ID',
      },
    },
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
      fieldName: 'engineeringName',
      label: '工程名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工程名称',
      },
    },
    {
      fieldName: 'visaName',
      label: '签证名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入签证名称',
      },
    },
    {
      fieldName: 'visaTime',
      label: '时间',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    // {
    //   fieldName: 'engineeringName',
    //   label: '工程名称',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入工程名称',
    //   },
    // },
    // {
    //   fieldName: 'createTime',
    //   label: '创建时间',
    //   component: 'RangePicker',
    //   componentProps: {
    //     ...getRangePickerDefaultProps(),
    //     allowClear: true,
    //   },
    // },
  ];
}

/** 列表的字段 */
export function useGridColumns(): VxeTableGridOptions<ProjectVisaApi.ProjectVisa>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'projectId',
      title: '项目ID',
      minWidth: 120,
      visible: false,
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
      field: 'visaName',
      title: '签证名称',
      minWidth: 120,
    },
    {
      field: 'visaTime',
      title: '时间',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'engineeringName',
      title: '工程名称',
      minWidth: 120,
    },
    {
      field: 'visaContent',
      title: '签证内容',
      minWidth: 120,
    },
    {
      field: 'amount',
      title: '金额',
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
      visible: false,
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
