import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { ReceiptOrderApi } from '#/api/biz/receiptOrder';

import { DICT_TYPE, getDictOptions, getRangePickerDefaultProps } from '#/utils';

export interface FormSchemaOptions {
  onProjectTypeChange?: () => void;
}

/** 新增/修改的表单 */
export function useFormSchema(options?: FormSchemaOptions): VbenFormSchema[] {
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
      fieldName: 'receiptNo',
      label: '收款单号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入收款单号',
      },
    },
    {
      fieldName: 'receiptType',
      label: '收款类型',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_TYPE, 'string'),
        placeholder: '请选择收款类型',
      },
    },
    {
      fieldName: 'projectType',
      label: '项目类型',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_PROJECT_TYPE, 'string'),
        placeholder: '请选择项目类型',
        onChange: options?.onProjectTypeChange,
      },
    },
    // {
    //   fieldName: 'projectScatteredType',
    //   label: '工程类型',
    //   component: 'Select',
    //   componentProps: {
    //     options: getDictOptions(
    //       DICT_TYPE.BIZ_PROJECT_ENGINEERING_TYPE,
    //       'string',
    //     ),
    //     placeholder: '请选择工程类型',
    //   },
    // },
    // {
    //   fieldName: 'fiscalYear',
    //   label: '财年',
    //   component: 'Input',
    //   componentProps: {
    //     placeholder: '请输入财年',
    //   },
    // },
    {
      fieldName: 'projectId',
      label: '项目ID',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目ID',
      },
    },
    // {
    //   fieldName: 'projectNo',
    //   label: '项目编号',
    //   component: 'Input',
    //   componentProps: {
    //     placeholder: '请输入项目编号',
    //   },
    // },
    // {
    //   fieldName: 'projectName',
    //   label: '项目名称',
    //   component: 'Input',
    //   componentProps: {
    //     placeholder: '请输入项目名称',
    //   },
    // },
    {
      fieldName: 'payerName',
      label: '付款方',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款方',
      },
    },
    {
      fieldName: 'receiptDate',
      label: '收款日期',
      rules: 'required',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'receiptAmount',
      label: '收款金额',
      rules: 'required',
      component: 'InputNumber',
      componentProps: {
        precision: 2,
        min: 0,
        placeholder: '请输入收款金额',
      },
    },
    {
      fieldName: 'receiptMethod',
      label: '收款方式',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_METHOD, 'string'),
        placeholder: '请选择收款方式',
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
      fieldName: 'receiptCertificate',
      label: '收款凭证',
      component: 'ImageUpload',
      componentProps: {
        accept: 'jpg/jpeg/png/gif/webp',
        maxSize: 5,
        maxNumber: 5,
      },
    },
    {
      fieldName: 'receiptPurpose',
      label: '收款事由',
      component: 'Input',
      componentProps: {
        placeholder: '请输入收款事由',
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
      fieldName: 'receiptNo',
      label: '收款单号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入收款单号',
      },
    },
    {
      fieldName: 'receiptType',
      label: '收款类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_TYPE, 'string'),
        placeholder: '请选择收款类型',
      },
    },
    {
      fieldName: 'projectType',
      label: '项目类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_PROJECT_TYPE, 'string'),
        placeholder: '请选择项目类型',
      },
    },
    {
      fieldName: 'projectScatteredType',
      label: '工程类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(
          DICT_TYPE.BIZ_PROJECT_ENGINEERING_TYPE,
          'string',
        ),
        placeholder: '请选择工程类型',
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
      fieldName: 'fiscalYear',
      label: '财年',
      component: 'InputNumber',
      componentProps: {
        allowClear: true,
        placeholder: '请输入财年',
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
      fieldName: 'payerName',
      label: '付款方',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入付款方',
      },
    },
    {
      fieldName: 'receiptDate',
      label: '收款日期',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    {
      fieldName: 'receiptMethod',
      label: '收款方式',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_RECEIPT_METHOD, 'string'),
        placeholder: '请选择收款方式',
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
export function useGridColumns(): VxeTableGridOptions<ReceiptOrderApi.ReceiptOrder>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'receiptNo',
      title: '收款单号',
      minWidth: 120,
    },
    {
      field: 'receiptType',
      title: '收款类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_RECEIPT_TYPE },
      },
    },
    {
      field: 'projectType',
      title: '项目类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_RECEIPT_PROJECT_TYPE },
      },
    },
    {
      field: 'projectScatteredType',
      title: '工程类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PROJECT_ENGINEERING_TYPE },
      },
    },
    {
      field: 'fiscalYear',
      title: '财年',
      minWidth: 120,
    },
    {
      field: 'projectId',
      title: '项目ID',
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
      field: 'payerName',
      title: '付款方',
      minWidth: 120,
    },
    {
      field: 'receiptDate',
      title: '收款日期',
      minWidth: 120,
      formatter: 'formatDateTime',
    },
    {
      field: 'receiptAmount',
      title: '收款金额',
      minWidth: 120,
    },
    {
      field: 'receiptMethod',
      title: '收款方式',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_RECEIPT_METHOD },
      },
    },
    {
      field: 'receiptCertificate',
      title: '收款凭证',
      minWidth: 120,
      cellRender: {
        name: 'CellImage',
      },
    },
    {
      field: 'receiptPurpose',
      title: '收款事由',
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

/** 收款信息导入的表单 */
export function receiptOrderImportFormSchema(): VbenFormSchema[] {
  return [
    {
      fieldName: 'file',
      label: '收款信息',
      component: 'Upload',
      rules: 'required',
      help: '仅允许导入 xls、xlsx 格式文件',
    },
  ];
}
