package com.lz.framework.common.enums;

import cn.hutool.core.util.ObjUtil;
import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum CommonWhetherEnum implements ArrayValuable<String> {
    COMMON_WHETHER_1("1", "是"),
    COMMON_WHETHER_2("2", "否");

    public static final String[] ARRAYS = Arrays.stream(values()).map(CommonWhetherEnum::getStatus).toArray(String[]::new);

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
