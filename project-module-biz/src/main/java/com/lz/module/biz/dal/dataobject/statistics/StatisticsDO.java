package com.lz.module.biz.dal.dataobject.statistics;

import lombok.Data;

/**
 * 统计返回RO
 */
@Data
public class StatisticsDO<T> {
    private String name;
    private T value;
}
