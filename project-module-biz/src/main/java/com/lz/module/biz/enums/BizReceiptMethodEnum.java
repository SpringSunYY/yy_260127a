package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizReceiptMethodEnum implements ArrayValuable<String> {
    BIZ_RECEIPT_METHOD_1("1", "现金"),
    BIZ_RECEIPT_METHOD_2("2", "微信转账"),
    BIZ_RECEIPT_METHOD_3("3", "支付宝转账"),
    BIZ_RECEIPT_METHOD_4("4", "银行卡转账");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizReceiptMethodEnum::getStatus).toArray(String[]::new);

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
