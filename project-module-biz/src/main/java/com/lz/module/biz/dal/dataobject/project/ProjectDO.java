package com.lz.module.biz.dal.dataobject.project;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目信息 DO
 *
 * @author YY
 */
@TableName("biz_project")
@KeySequence("biz_project_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
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
     * 项目类型
     *
     * 枚举 {@link TODO biz_project_type 对应的类}
     */
    private String projectType;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 工程类型
     *
     * 枚举 {@link TODO biz_project_project_progress 对应的类}
     */
    private String engineeringType;
    /**
     * 属于PMC
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isPmc;
    /**
     * 服务商编号
     */
    private Long customerId;
    /**
     * 服务商名称
     */
    private String customerName;
    /**
     * 交底时间
     */
    private LocalDateTime deliverTime;
    /**
     * 财年
     */
    private Integer fiscalYear;
    /**
     * 签证数
     */
    private Integer visaNum;
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
    private String isCompleted;
    /**
     * 竣工资料
     */
    private String completedFile;
    /**
     * 现场核销
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String verification;
    /**
     * 竣工工程确定量
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String determinedQuantity;
    /**
     * 材料核销
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String materialVerification;
    /**
     * 结算审定书
     */
    private String settlementFile;
    /**
     * 附件
     */
    private String appendixFile;
    /**
     * 备注
     */
    private String remark;


}