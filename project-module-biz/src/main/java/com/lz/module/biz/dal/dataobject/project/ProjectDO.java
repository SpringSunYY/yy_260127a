package com.lz.module.biz.dal.dataobject.project;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

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
     * <p>
     */
    private String projectType;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 工程类型
     * <p>
     */
    private String engineeringType;
    /**
     * 属于PMC
     * <p>
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
     * 完工移交时间
     */
    private LocalDateTime completedTime;
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
     * <p>
     */
    private String projectProgress;
    /**
     * 竣工资料
     *
     */
    private String isCompletedFile;
    /**
     * 现场核销
     *
     */
    private String isVerification;
    /**
     * 竣工图
     *
     */
    private String isCompleted;
    /**
     * 竣工工程确定量
     *
     */
    private String isDeterminedQuantity;
    /**
     * 材料核销
     *
     */
    private String isMaterialVerification;
    /**
     * 结算审定书
     *
     */
    private String isSettlementFile;
    /**
     * 附件
     */
    private String appendixFile;
    /**
     * 备注
     */
    private String remark;


}
