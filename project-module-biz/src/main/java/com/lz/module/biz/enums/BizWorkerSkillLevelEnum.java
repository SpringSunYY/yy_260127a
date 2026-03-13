package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizWorkerSkillLevelEnum implements ArrayValuable<String> {
    BIZ_WORKER_SKILL_LEVEL_1("1", "低"),
    BIZ_WORKER_SKILL_LEVEL_2("2", "中"),
    BIZ_WORKER_SKILL_LEVEL_3("3", "高");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizWorkerSkillLevelEnum::getStatus).toArray(String[]::new);

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
