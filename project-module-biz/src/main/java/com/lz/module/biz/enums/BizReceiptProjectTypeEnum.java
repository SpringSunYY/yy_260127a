package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizReceiptProjectTypeEnum implements ArrayValuable<String> {
    BIZ_RECEIPT_PROJECT_TYPE_1("1", "工程项目"),
    BIZ_RECEIPT_PROJECT_TYPE_2("2", "其他");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizReceiptProjectTypeEnum::getStatus).toArray(String[]::new);

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
