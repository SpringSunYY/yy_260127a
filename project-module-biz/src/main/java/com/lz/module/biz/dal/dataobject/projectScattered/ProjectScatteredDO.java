package com.lz.module.biz.dal.dataobject.projectScattered;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 零散工程 DO
 *
 * @author YY
 */
@TableName("biz_project_scattered")
@KeySequence("biz_project_scattered_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectScatteredDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目编号
     */
    private String projectId;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 工程名称
     */
    private String scatteredName;
    /**
     * 时间
     */
    private LocalDateTime scatteredTime;
    /**
     * 工程阶段
     *
     * 枚举 {@link TODO biz_project_project_progress 对应的类}
     */
    private String projectProgress;
    /**
     * 竣工图
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String completedImage;
    /**
     * 现场核销
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String verification;
    /**
     * 附件
     */
    private String appendixFile;
    /**
     * 备注
     */
    private String remark;


}