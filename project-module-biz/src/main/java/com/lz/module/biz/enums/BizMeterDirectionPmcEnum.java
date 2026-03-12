package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizMeterDirectionPmcEnum implements ArrayValuable<String> {
    BIZ_METER_DIRECTION_1("1", "左"),
    BIZ_METER_DIRECTION_2("2", "右");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizMeterDirectionPmcEnum::getStatus).toArray(String[]::new);

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
