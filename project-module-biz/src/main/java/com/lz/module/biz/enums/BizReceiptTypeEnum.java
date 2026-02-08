package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizReceiptTypeEnum implements ArrayValuable<String> {
    BIZ_RECEIPT_TYPE_1("1", "进度款"),
    BIZ_RECEIPT_TYPE_2("2", "结算款"),
    BIZ_RECEIPT_TYPE_3("3", "质保金");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizReceiptTypeEnum::getStatus).toArray(String[]::new);

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
