package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizProjectOtherProjectTypeEnum implements ArrayValuable<String> {
    BIZ_PROJECT_OTHER_PROJECT_TYPE_0("0", "其他工程"),
    BIZ_PROJECT_OTHER_PROJECT_TYPE_1("1", "改管维修工程");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizProjectOtherProjectTypeEnum::getStatus).toArray(String[]::new);

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
