import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { SalaryPaymentOrderApi } from '#/api/biz/salaryPaymentOrder';

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
      fieldName: 'paymentNo',
      label: '付款单号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款单号',
      },
      dependencies: {
        triggerFields: ['id'],
        disabled: (values) => !!values.id,
        show: (values) => !!values.id,
      },
    },
    {
      fieldName: 'workerId',
      label: '工人ID',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工人ID',
      },
    },
    {
      fieldName: 'workerName',
      label: '工人名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工人名称',
      },
    },
    {
      fieldName: 'salaryId',
      label: '工资ID',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工资ID',
      },
    },
    {
      fieldName: 'salaryName',
      label: '工资名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入工资名称',
      },
    },
    {
      fieldName: 'paymentTime',
      label: '付款日期',
      rules: 'required',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'paymentAmount',
      label: '付款金额',
      rules: 'required',
      component: 'InputNumber',
      componentProps: {
        precision: 2,
        min: 0,
        placeholder: '请输入付款金额',
      },
    },
    {
      fieldName: 'paymentMethod',
      label: '付款方式',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_METHOD, 'string'),
        placeholder: '请选择付款方式',
      },
    },
    {
      fieldName: 'paymentCertificate',
      label: '付款凭证',
      component: 'FileUpload',
    },
    {
      fieldName: 'paymentPurpose',
      label: '付款事由',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款事由',
      },
    },
    {
      fieldName: 'isInvoiced',
      label: '是否开票',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择是否开票',
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
      fieldName: 'paymentNo',
      label: '付款单号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入付款单号',
      },
    },
    {
      fieldName: 'salaryId',
      label: '工资ID',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工资ID',
      },
    },
    {
      fieldName: 'salaryName',
      label: '工资名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工资名称',
      },
    },
    {
      fieldName: 'workerId',
      label: '工人ID',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工人ID',
      },
    },
    {
      fieldName: 'workerName',
      label: '工人名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入工人名称',
      },
    },
    {
      fieldName: 'paymentTime',
      label: '付款日期',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    {
      fieldName: 'paymentMethod',
      label: '付款方式',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_METHOD, 'string'),
        placeholder: '请选择付款方式',
      },
    },
    {
      fieldName: 'isInvoiced',
      label: '是否开票',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择是否开票',
      },
    },
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
export function useGridColumns(): VxeTableGridOptions<SalaryPaymentOrderApi.SalaryPaymentOrder>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'paymentNo',
      title: '付款单号',
      minWidth: 120,
    },
    {
      field: 'workerId',
      title: '工人ID',
      minWidth: 120,
    },
    {
      field: 'workerName',
      title: '工人名称',
      minWidth: 120,
    },
    {
      field: 'salaryId',
      title: '工资ID',
      minWidth: 120,
    },
    {
      field: 'salaryName',
      title: '工资名称',
      minWidth: 120,
    },
    {
      field: 'paymentTime',
      title: '付款日期',
      minWidth: 120,
      formatter: 'formatDateTime',
    },
    {
      field: 'paymentAmount',
      title: '付款金额',
      minWidth: 120,
    },
    {
      field: 'paymentMethod',
      title: '付款方式',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_RECEIPT_METHOD },
      },
    },
    {
      field: 'paymentCertificate',
      title: '付款凭证',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'paymentPurpose',
      title: '付款事由',
      minWidth: 120,
    },
    {
      field: 'isInvoiced',
      title: '是否开票',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
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
      visible: false,
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

/** 工资付款信息导入的表单 */
export function salaryPaymentOrderImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '工资付款信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
  ];
}
