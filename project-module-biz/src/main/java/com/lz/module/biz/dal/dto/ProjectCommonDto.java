package com.lz.module.biz.dal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目公用dto
 *
 * @Project: project
 * @Author: YY
 * @CreateTime: 2026-02-08  18:24
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCommonDto {
    private Long id;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String name;

    /**
     * 工程类型
     */
    private String engineeringType;

    /**
     * 财年
     */
    private Integer fiscalYear;
}
