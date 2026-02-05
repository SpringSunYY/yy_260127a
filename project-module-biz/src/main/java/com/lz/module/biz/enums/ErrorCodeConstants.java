package com.lz.module.biz.enums;

import com.lz.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {
    // ==========  通用流程处理 模块 1-008-000-000 ==========
    // ==========  客户信息 模块 1-008-000-xxx ==========
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(1_008_000_000, "客户信息不存在");

    // ==========  供应商 模块 1-008-002-xxx ==========
    ErrorCode SUPPLIER_NOT_EXISTS = new ErrorCode(1_008_002_000, "供应商不存在");
    // ========== 原材料信息 1-008-003-xxx  ==========
    ErrorCode RAW_MATERIALS_NOT_EXISTS = new ErrorCode(1_008_003_000, "原材料信息不存在");
    // ========== 采购信息 1-008-004-xxx  ==========
    ErrorCode PURCHASE_ORDER_NOT_EXISTS = new ErrorCode(1_008_004_000, "采购信息不存在");
    ErrorCode PURCHASE_ORDER_DETAIL_NOT_DATA=new ErrorCode(1_008_004_001,"采购明细数据不全，请检查是否是价格或者数量不对");
}
