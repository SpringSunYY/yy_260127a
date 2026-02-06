import type { VbenFormSchema } from '#/adapter/form';
import type { VxeTableGridOptions } from '#/adapter/vxe-table';
import type { InstallTableApi } from '#/api/biz/installTable';

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
      fieldName: 'installDate',
      label: '安装日期',
      component: 'DatePicker',
      componentProps: {
        showTime: true,
        format: 'YYYY-MM-DD',
        valueFormat: 'x',
      },
    },
    {
      fieldName: 'communityName',
      label: '小区名称',
      component: 'Input',
      componentProps: {
        placeholder: '请输入小区名称',
      },
    },
    {
      fieldName: 'houseAddress',
      label: '门牌地址',
      component: 'Input',
      componentProps: {
        placeholder: '请输入门牌地址',
      },
    },
    {
      fieldName: 'dn15PipeLength',
      label: 'DN15镀锌钢管(m)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入DN15镀锌钢管(m)',
      },
    },
    {
      fieldName: 'dn25PipeLength',
      label: 'DN25镀锌钢管(m)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入DN25镀锌钢管(m)',
      },
    },
    {
      fieldName: 'dn15ElbowQty',
      label: 'DN15弯头(个)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入DN15弯头(个)',
      },
    },
    {
      fieldName: 'dn15InnerConnectorQty',
      label: 'DN15内接(个)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入DN15内接(个)',
      },
    },
    {
      fieldName: 'dn15DirectQty',
      label: 'DN15直接(个)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入DN15直接(个)',
      },
    },
    {
      fieldName: 'pipeClampQty',
      label: '管卡(个)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入管卡(个)',
      },
    },
    {
      fieldName: 'galvanizedTeeQty',
      label: '镀锌三通(个)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入镀锌三通(个)',
      },
    },
    {
      fieldName: 'preMeterValveQty',
      label: '表前球阀(只)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入表前球阀(只)',
      },
    },
    {
      fieldName: 'doubleNozzleValveQty',
      label: '双直嘴阀(只)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入双直嘴阀(只)',
      },
    },
    {
      fieldName: 'singleNozzleValveQty',
      label: '单直嘴阀(只)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入单直嘴阀(只)',
      },
    },
    {
      fieldName: 'meterConnectorQty',
      label: '表接头(只)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        placeholder: '请输入表接头(只)',
      },
    },
    {
      fieldName: 'meterNo',
      label: '表号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入表号',
      },
    },
    {
      fieldName: 'meterModel',
      label: '型号',
      component: 'Input',
      componentProps: {
        placeholder: '请输入型号',
      },
    },
    {
      fieldName: 'meterDirection',
      label: '表向',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.BIZ_METER_DIRECTION, 'string'),
        placeholder: '请选择表向',
      },
    },
    {
      fieldName: 'floorHeightStatus',
      label: '层高及入住情况',
      component: 'Input',
      componentProps: {
        placeholder: '请输入层高及入住情况',
      },
    },
    {
      fieldName: 'ownerName',
      label: '户主',
      component: 'Input',
      componentProps: {
        placeholder: '请输入户主',
      },
    },
    {
      fieldName: 'contactPhone',
      label: '联系方式',
      component: 'Input',
      componentProps: {
        placeholder: '请输入联系方式',
      },
    },
    {
      fieldName: 'extraLengthFee',
      label: '超长费用(元)',
      component: 'InputNumber',
      componentProps: {
        min: 0,
        precision: 2,
        placeholder: '请输入超长费用(元)',
      },
    },
    {
      fieldName: 'addMeterBox',
      label: '加表箱',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择加表箱',
      },
    },
    {
      fieldName: 'installerName',
      label: '安装人员',
      component: 'Input',
      componentProps: {
        placeholder: '请输入安装人员',
      },
    },
    {
      fieldName: 'isHighAltitude',
      label: '高空作业',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择高空作业',
      },
    },
    {
      fieldName: 'isOpenTee',
      label: '开T',
      component: 'Select',
      componentProps: {
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择开T',
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
      fieldName: 'installDate',
      label: '安装日期',
      component: 'RangePicker',
      componentProps: {
        ...getRangePickerDefaultProps(),
        allowClear: true,
      },
    },
    {
      fieldName: 'communityName',
      label: '小区名称',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入小区名称',
      },
    },
    {
      fieldName: 'meterNo',
      label: '表号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入表号',
      },
    },
    {
      fieldName: 'meterModel',
      label: '型号',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入型号',
      },
    },
    {
      fieldName: 'meterDirection',
      label: '表向',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.BIZ_METER_DIRECTION, 'string'),
        placeholder: '请选择表向',
      },
    },
    {
      fieldName: 'extraLengthFee',
      label: '超长费用(元)',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入超长费用(元)',
      },
    },
    {
      fieldName: 'installerName',
      label: '安装人员',
      component: 'Input',
      componentProps: {
        allowClear: true,
        placeholder: '请输入安装人员',
      },
    },
    {
      fieldName: 'addMeterBox',
      label: '加箱表',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择加箱表',
      },
    },
    {
      fieldName: 'isHighAltitude',
      label: '高空作业',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择高空作业',
      },
    },
    {
      fieldName: 'isOpenTee',
      label: '开T',
      component: 'Select',
      componentProps: {
        allowClear: true,
        options: getDictOptions(DICT_TYPE.COMMON_WHETHER, 'string'),
        placeholder: '请选择开T',
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
export function useGridColumns(): VxeTableGridOptions<InstallTableApi.InstallTable>['columns'] {
  return [
    { type: 'checkbox', width: 40 },
    {
      field: 'id',
      title: '编号',
      minWidth: 120,
    },
    {
      field: 'installDate',
      title: '安装日期',
      minWidth: 120,
      formatter: 'formatDate',
    },
    {
      field: 'communityName',
      title: '小区名称',
      minWidth: 120,
    },
    {
      field: 'houseAddress',
      title: '门牌地址',
      minWidth: 120,
    },
    {
      field: 'dn15PipeLength',
      title: 'DN15镀锌钢管',
      minWidth: 120,
    },
    {
      field: 'dn25PipeLength',
      title: 'DN25镀锌钢管',
      minWidth: 120,
    },
    {
      field: 'dn15ElbowQty',
      title: 'DN15弯头(个)',
      minWidth: 120,
    },
    {
      field: 'dn15InnerConnectorQty',
      title: 'DN15内接(个)',
      minWidth: 120,
    },
    {
      field: 'dn15DirectQty',
      title: 'DN15直接(个)',
      minWidth: 120,
    },
    {
      field: 'pipeClampQty',
      title: '管卡(个)',
      minWidth: 120,
    },
    {
      field: 'galvanizedTeeQty',
      title: '镀锌三通(个)',
      minWidth: 120,
    },
    {
      field: 'preMeterValveQty',
      title: '表前球阀(只)',
      minWidth: 120,
    },
    {
      field: 'doubleNozzleValveQty',
      title: '双直嘴阀(只)',
      minWidth: 120,
    },
    {
      field: 'singleNozzleValveQty',
      title: '单直嘴阀(只)',
      minWidth: 120,
    },
    {
      field: 'meterConnectorQty',
      title: '表接头(只)',
      minWidth: 120,
    },
    {
      field: 'meterNo',
      title: '表号',
      minWidth: 120,
    },
    {
      field: 'meterModel',
      title: '型号',
      minWidth: 120,
    },
    {
      field: 'meterDirection',
      title: '表向',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.BIZ_METER_DIRECTION },
      },
    },
    {
      field: 'floorHeightStatus',
      title: '层高及入住情况',
      minWidth: 120,
    },
    {
      field: 'ownerName',
      title: '户主',
      minWidth: 120,
    },
    {
      field: 'contactPhone',
      title: '联系方式',
      minWidth: 120,
    },
    {
      field: 'extraLengthFee',
      title: '超长费用(元)',
      minWidth: 120,
    },
    {
      field: 'addMeterBox',
      title: '加表箱',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'installerName',
      title: '安装人员',
      minWidth: 120,
    },
    {
      field: 'isHighAltitude',
      title: '高空作业',
      minWidth: 120,
      cellRender: {
        name: 'CellDict',
        props: { type: DICT_TYPE.COMMON_WHETHER },
      },
    },
    {
      field: 'isOpenTee',
      title: '开T',
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
