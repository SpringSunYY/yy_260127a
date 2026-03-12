package com.lz.module.biz.enums;

import com.lz.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BizCustomerIndustryEnum implements ArrayValuable<String> {
    BIZ_CUSTOMER_INDUSTRY_1("1", "IT"),
    BIZ_CUSTOMER_INDUSTRY_2("2", "金融业"),
    BIZ_CUSTOMER_INDUSTRY_3("3", "房地产"),
    BIZ_CUSTOMER_INDUSTRY_4("4", "商业服务"),
    BIZ_CUSTOMER_INDUSTRY_5("5", "运输/物流"),
    BIZ_CUSTOMER_INDUSTRY_6("6", "生产"),
    BIZ_CUSTOMER_INDUSTRY_7("7", "政府"),
    BIZ_CUSTOMER_INDUSTRY_8("8", "文化传媒"),
    BIZ_CUSTOMER_INDUSTRY_9("9", "建筑业");

    public static final String[] ARRAYS = Arrays.stream(values()).map(BizCustomerIndustryEnum::getStatus).toArray(String[]::new);

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
