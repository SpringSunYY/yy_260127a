package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizPurchaseOrderStatusEnum implements ArrayValuable<String> {
    BIZ_PURCHASE_ORDER_STATUS_1("1", "进行中"),
    BIZ_PURCHASE_ORDER_STATUS_2("2", "已完成"),
    BIZ_PURCHASE_ORDER_STATUS_3("3", "未完成");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizPurchaseOrderStatusEnum::getStatus).toArray(String[]::new);

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
