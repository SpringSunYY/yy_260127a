package com.lz.module.biz.dal.dataobject.projectVisa;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目签证 DO
 *
 * @author YY
 */
@TableName("biz_project_visa")
@KeySequence("biz_project_visa_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVisaDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 工程名称
     */
    private String visaName;
    /**
     * 签证内容
     */
    private String visaContent;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 附件
     */
    private String appendixFile;
    /**
     * 备注
     */
    private String remark;


}