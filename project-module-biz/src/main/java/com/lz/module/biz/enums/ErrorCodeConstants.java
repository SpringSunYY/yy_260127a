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
}
