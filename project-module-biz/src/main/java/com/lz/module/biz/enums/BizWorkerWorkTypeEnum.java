package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizWorkerWorkTypeEnum implements ArrayValuable<String> {
    BIZ_WORKER_WORK_TYPE_0("0", "其他"),
    BIZ_WORKER_WORK_TYPE_1("1", "安装工"),
    BIZ_WORKER_WORK_TYPE_2("2", "维修工"),
    BIZ_WORKER_WORK_TYPE_3("3", "焊工");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizWorkerWorkTypeEnum::getStatus).toArray(String[]::new);

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
