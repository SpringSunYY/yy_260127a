package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizProjectEngineeringTypeEnum implements ArrayValuable<String> {
    BIZ_PROJECT_ENGINEERING_TYPE_0("1", "其他"),
    BIZ_PROJECT_ENGINEERING_TYPE_1("1", "工商户"),
    BIZ_PROJECT_ENGINEERING_TYPE_2("2", "老户"),
    BIZ_PROJECT_ENGINEERING_TYPE_3("3", "新户"),
    BIZ_PROJECT_ENGINEERING_TYPE_4("4", "市政"),
    BIZ_PROJECT_ENGINEERING_TYPE_5("5", "老旧改");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizProjectEngineeringTypeEnum::getStatus).toArray(String[]::new);

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
