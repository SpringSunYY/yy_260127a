package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizProjectProgressEnum implements ArrayValuable<String> {
    BIZ_PROJECT_PROGRESS_1("1", "交底"),
    BIZ_PROJECT_PROGRESS_2("2", "施工过程"),
    BIZ_PROJECT_PROGRESS_3("3", "工程完工移交");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizProjectProgressEnum::getStatus).toArray(String[]::new);

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
