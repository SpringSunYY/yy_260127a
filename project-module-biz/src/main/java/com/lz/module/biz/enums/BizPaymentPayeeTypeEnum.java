package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizPaymentPayeeTypeEnum implements ArrayValuable<String> {
    BIZ_PAYMENT_PAYEE_TYPE_1("1", "工人"),
    BIZ_PAYMENT_PAYEE_TYPE_2("2", "供应商"),
    BIZ_PAYMENT_PAYEE_TYPE_3("3", "客户");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizPaymentPayeeTypeEnum::getStatus).toArray(String[]::new);

    /**
     * 状态值
     */
    private final String status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public String[] array() {
        return ARRAYS;
    }
}
