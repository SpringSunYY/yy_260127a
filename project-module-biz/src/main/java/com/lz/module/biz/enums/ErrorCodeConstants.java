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
    ErrorCode PURCHASE_ORDER_DETAIL_NOT_DATA = new ErrorCode(1_008_004_001, "采购明细数据不全，请检查是否是价格或者数量不对");
    // ========== 项目 1-008-005-xxx  ==========
    ErrorCode PROJECT_NOT_EXISTS = new ErrorCode(1_008_005_000, "项目信息不存在");
    // ========== 项目签证 1-008-006-xxx  ==========
    ErrorCode PROJECT_VISA_NOT_EXISTS = new ErrorCode(1_008_006_000, "项目签证不存在");
    // ========== 零散工程 1-008-007-xxx  ==========
    ErrorCode PROJECT_SCATTERED_NOT_EXISTS = new ErrorCode(1_008_007_000, "零散工程不存在");
    // ========== 装表信息 1-008-008-xxx  ==========
    ErrorCode INSTALL_TABLE_NOT_EXISTS = new ErrorCode(1_008_008_000, "装表信息不存在");
    // ========== 其他工程 1-008-009-xxx  ==========
    ErrorCode PROJECT_OTHER_NOT_EXISTS = new ErrorCode(1_008_009_000, "其他工程不存在");
    // ========== 工人信息 1-008-010-xxx  ==========
    ErrorCode WORKER_NOT_EXISTS = new ErrorCode(1_008_010_000, "工人信息不存在");
    // ========== 工资信息 1-008-011-xxx  ==========
    ErrorCode SALARY_NOT_EXISTS = new ErrorCode(1_008_011_000, "工资信息不存在");
    // ========== 收款 1-008-012-xxx  ==========
    ErrorCode RECEIPT_ORDER_NOT_EXISTS = new ErrorCode(1_008_012_000, "收款信息不存在");
    // ========== 付款 1-008-013-xxx  ==========
    ErrorCode PAYMENT_ORDER_NOT_EXISTS = new ErrorCode(1_008_013_000, "付款信息不存在");
}
