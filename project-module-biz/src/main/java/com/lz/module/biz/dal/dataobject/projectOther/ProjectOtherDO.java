package com.lz.module.biz.dal.dataobject.projectOther;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 其他工程 DO
 *
 * @author YY
 */
@TableName("biz_project_other")
@KeySequence("biz_project_other_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOtherDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目类型
     * <p>
     * 枚举 {@link TODO biz_project_other_project_type 对应的类}
     */
    private String projectType;
    /**
     * 地址
     */
    private String projectAddress;
    /**
     * 时间
     */
    private LocalDateTime projectDate;
    /**
     * 施工费
     */
    private BigDecimal constructionFee;
    /**
     * 已结算
     * <p>
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isSettled;
    /**
     * 附件
     */
    private String appendixFile;
    /**
     * 材料说明
     */
    private String materialDesc;
    /**
     * 进度
     * <p>
     * 枚举 {@link TODO biz_project_other_project_progress 对应的类}
     */
    private String progressStatus;
    /**
     * 备注
     */
    private String remark;


}
