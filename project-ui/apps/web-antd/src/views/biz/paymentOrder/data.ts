import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { PaymentOrderApi } from '#/api/biz/paymentOrder';

import { DICT_TYPE, getDictOptions, getRangePickerDefaultProps } from '#/utils';

export interface FormSchemaOptions {
  onProjectTypeChange?: () => void;
  onPayeeTypeChange?: () => void;
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
      fieldName: 'paymentNo',
      label: '付款单号',
      rules: 'required',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款单号',
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
    {
      fieldName: 'projectId',
      label: '项目ID',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目ID',
      },
    },
    {
      fieldName: 'projectName',
      label: '项目名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'payeeType',
      label: '付款对象类型',
      rules: 'required',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_PAYMENT_PAYEE_TYPE, 'string'),
        placeholder: '请选择付款对象类型',
        onChange: options?.onPayeeTypeChange,
      },
    },
    {
      fieldName: 'payeeId',
      label: '付款对象ID',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款对象ID',
      },
    },
    {
      fieldName: 'payeeName',
      label: '付款对象名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入付款对象名称',
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
      component: 'Input',
      componentProps: {
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
      component: 'ImageUpload',
      componentProps: {
        accept: 'jpg/jpeg/png/gif/webp',
        maxSize: 5,
        maxNumber: 5,
      },
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
      component: 'RadioGroup',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
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
      fieldName: 'paymentNo',
      label: '付款单号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入付款单号',
      },
    },
    // {
    //   fieldName: 'projectId',
    //   label: '项目ID',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入项目ID',
    //   },
    // },
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
      fieldName: 'projectName',
      label: '项目名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入项目名称',
      },
    },
    {
      fieldName: 'payeeType',
      label: '付款对象类型',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_PAYMENT_PAYEE_TYPE, 'string'),
        placeholder: '请选择付款对象类型',
      },
    },
    // {
    //   fieldName: 'payeeId',
    //   label: '付款对象ID',
    //   component: 'Input',
    //   componentProps: {
    //     allowClear: true,
    //     placeholder: '请输入付款对象ID',
    //   },
    // },
    {
      fieldName: 'payeeName',
      label: '付款对象名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入付款对象名称',
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
export function useGridColumns(): VxeTableGridOptions<PaymentOrderApi.PaymentOrder>['columns'] {
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
      field: 'projectType',
      title: '项目类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_RECEIPT_PROJECT_TYPE },
      },
    },
    {
      field: 'projectId',
      title: '项目ID',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'projectName',
      title: '项目名称',
      minWidth: 120,
    },
    {
      field: 'payeeType',
      title: '付款对象类型',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_PAYMENT_PAYEE_TYPE },
      },
    },
    {
      field: 'payeeId',
      title: '付款对象ID',
      visible: false,
      minWidth: 120,
    },
    {
      field: 'payeeName',
      title: '付款对象名称',
      minWidth: 120,
    },
    {
      field: 'paymentTime',
      title: '付款日期',
      minWidth: 120,
      formatter: 'formatDate',
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
      minWidth: 120,
      cellRender: {
        name: 'CellImage',
      },
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
