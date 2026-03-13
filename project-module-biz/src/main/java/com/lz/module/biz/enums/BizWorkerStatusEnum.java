package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizWorkerStatusEnum implements ArrayValuable<String> {
    BIZ_WORKER_STATUS_1("1", "在岗"),
    BIZ_WORKER_STATUS_2("2", "离职"),
    BIZ_WORKER_STATUS_3("3", "停用");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizWorkerStatusEnum::getStatus).toArray(String[]::new);

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
