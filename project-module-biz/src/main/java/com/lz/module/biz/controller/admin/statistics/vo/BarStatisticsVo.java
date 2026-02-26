package com.lz.module.biz.controller.admin.statistics.vo;

import java.util.List;

/**
* 统计柱形图VO
 */
public class BarStatisticsVo {
   private List<String> names;

    private List<Long> values;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
}
