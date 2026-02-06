// TODO @芋艿：后续再优化
// TODO @芋艿：可以共享么？

import { isObject } from '@vben/utils';

import { useDictStore } from '#/store';

// TODO @dhb52：top-level 调用 导致："getActivePinia()" was called but there was no active Pinia
// 先临时移入到方法中
// const dictStore = useDictStore();

// TODO @dhb: antd 组件的 color 类型
type ColorType = 'error' | 'info' | 'success' | 'warning';

export interface DictDataType {
  dictType?: string;
  label: string;
  value: boolean | number | string;
  colorType?: ColorType;
  cssClass?: string;
}

export interface NumberDictDataType extends DictDataType {
  value: number;
}

export interface StringDictDataType extends DictDataType {
  value: string;
}

/**
 * 获取字典标签
 *
 * @param dictType 字典类型
 * @param value 字典值
 * @returns 字典标签
 */
function getDictLabel(dictType: string, value: any) {
  const dictStore = useDictStore();
  const dictObj = dictStore.getDictData(dictType, value);
  return isObject(dictObj) ? dictObj.label : '';
}

/**
 * 获取字典对象
 *
 * @param dictType 字典类型
 * @param value 字典值
 * @returns 字典对象
 */
function getDictObj(dictType: string, value: any) {
  const dictStore = useDictStore();
  const dictObj = dictStore.getDictData(dictType, value);
  return isObject(dictObj) ? dictObj : null;
}

/**
 * 获取字典数组 用于select radio 等
 *
 * @param dictType 字典类型
 * @param valueType 字典值类型，默认 string 类型
 * @returns 字典数组
 */
function getDictOptions(
  dictType: string,
  valueType: 'boolean' | 'number' | 'string' = 'string',
): DictDataType[] {
  const dictStore = useDictStore();
  const dictOpts = dictStore.getDictOptions(dictType);
  const dictOptions: DictDataType[] = [];
  if (dictOpts.length > 0) {
    let dictValue: boolean | number | string = '';
    dictOpts.forEach((d) => {
      switch (valueType) {
        case 'boolean': {
          dictValue = `${d.value}` === 'true';
          break;
        }
        case 'number': {
          dictValue = Number.parseInt(`${d.value}`);
          break;
        }
        case 'string': {
          dictValue = `${d.value}`;
          break;
        }
        // No default
      }
      dictOptions.push({
        value: dictValue,
        label: d.label,
      });
    });
  }
  return dictOptions.length > 0 ? dictOptions : [];
}

// TODO @dhb52：下面的一系列方法，看看能不能复用 getDictOptions 方法
export const getIntDictOptions = (dictType: string): NumberDictDataType[] => {
  // 获得通用的 DictDataType 列表
  const dictOptions = getDictOptions(dictType) as DictDataType[];
  // 转换成 number 类型的 NumberDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getIntDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: NumberDictDataType[] = [];
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: Number.parseInt(`${dict.value}`),
    });
  });
  return dictOption;
};

// TODO @dhb52：下面的一系列方法，看看能不能复用 getDictOptions 方法
export const getStrDictOptions = (dictType: string) => {
  // 获得通用的 DictDataType 列表
  const dictOptions = getDictOptions(dictType) as DictDataType[];
  // 转换成 string 类型的 StringDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getStrDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: StringDictDataType[] = [];
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: `${dict.value}`,
    });
  });
  return dictOption;
};

// TODO @dhb52：下面的一系列方法，看看能不能复用 getDictOptions 方法
export const getBoolDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = [];
  const dictOptions = getDictOptions(dictType) as DictDataType[];
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: `${dict.value}` === 'true',
    });
  });
  return dictOption;
};

enum DICT_TYPE {
  // ========== biz模块 ==========
  BIZ_CUSTOMER_INDUSTRY = 'biz_customer_industry', // 所属行业
  BIZ_PROJECT_ENGINEERING_TYPE = 'biz_project_engineering_type',
  BIZ_PROJECT_PROJECT_PROGRESS = 'biz_project_project_progress', // 项目进度
  BIZ_PROJECT_TYPE = 'biz_project_type',
  BIZ_PURCHASE_ORDER_STATUS = 'biz_purchase_order_status', // 采购单状态
  BIZ_RAW_MATERIALS_UNIT = 'biz_raw_materials_unit', // 材料单位
  BPM_MODEL_FORM_TYPE = 'bpm_model_form_type',
  // ========== BPM 模块 ==========
  BPM_MODEL_TYPE = 'bpm_model_type',
  BPM_OA_LEAVE_TYPE = 'bpm_oa_leave_type',
  BPM_PROCESS_INSTANCE_STATUS = 'bpm_process_instance_status',
  BPM_PROCESS_LISTENER_TYPE = 'bpm_process_listener_type',
  BPM_PROCESS_LISTENER_VALUE_TYPE = 'bpm_process_listener_value_type',
  BPM_TASK_STATUS = 'bpm_task_status',
  COMMON_STATUS = 'common_status',
  COMMON_WHETHER = 'common_whether',

  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',

  INFRA_CODEGEN_FRONT_TYPE = 'infra_codegen_front_type',
  INFRA_CODEGEN_SCENE = 'infra_codegen_scene',

  INFRA_CODEGEN_TEMPLATE_TYPE = 'infra_codegen_template_type',

  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_FILE_STORAGE = 'infra_file_storage',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',

  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_OPERATE_TYPE = 'infra_operate_type',

  SYSTEM_DATA_SCOPE = 'system_data_scope',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_MAIL_SEND_STATUS = 'system_mail_send_status',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_NOTICE_TYPE = 'system_notice_type',
  SYSTEM_NOTIFY_TEMPLATE_TYPE = 'system_notify_template_type',
  SYSTEM_OAUTH2_GRANT_TYPE = 'system_oauth2_grant_type',
  SYSTEM_ROLE_TYPE = 'system_role_type',

  SYSTEM_SMS_CHANNEL_CODE = 'system_sms_channel_code',
  SYSTEM_SMS_RECEIVE_STATUS = 'system_sms_receive_status',
  SYSTEM_SMS_SEND_STATUS = 'system_sms_send_status',

  SYSTEM_SMS_TEMPLATE_TYPE = 'system_sms_template_type',
  SYSTEM_SOCIAL_TYPE = 'system_social_type',
  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_SEX = 'system_user_sex',
  USER_TYPE = 'user_type',
}

export { DICT_TYPE, getDictLabel, getDictObj, getDictOptions };
