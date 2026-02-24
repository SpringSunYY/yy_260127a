package com.lz.module.biz.dal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付单收款方信息
 *
 * @Project: project
 * @Author: YY
 * @CreateTime: 2026-02-24  16:42
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderPayeeDto {
    /**
     * 编号
     */
    private Long id;
    /**
     * 客户名称
     */
    private String name;
}
