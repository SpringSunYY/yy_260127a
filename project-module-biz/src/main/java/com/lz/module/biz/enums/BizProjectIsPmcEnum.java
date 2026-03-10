package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizProjectIsPmcEnum implements ArrayValuable<String> {
    BIZ_PROJECT_IS_PMC_1("1", "PMC"),
    BIZ_PROJECT_IS_PMC_2("2", "PC");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizProjectIsPmcEnum::getStatus).toArray(String[]::new);

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
