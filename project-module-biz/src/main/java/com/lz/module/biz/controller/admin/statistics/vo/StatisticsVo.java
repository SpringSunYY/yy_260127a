package com.lz.module.biz.controller.admin.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVo<T> {
    private String name;
    private T value;
}
