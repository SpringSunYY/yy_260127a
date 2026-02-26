package com.lz.module.biz.controller.admin.statistics.vo;

/**
 * 饼图统计VO
 */
public class PieStatisticsVo {
    private String name;
    private Long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
