package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizProjectOtherProjectProgressEnum implements ArrayValuable<String> {
    BIZ_PROJECT_OTHER_PROJECT_PROGRESS_1("1", "施工中"),
    BIZ_PROJECT_OTHER_PROJECT_PROGRESS_2("2", "完成");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizProjectOtherProjectProgressEnum::getStatus).toArray(String[]::new);

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
