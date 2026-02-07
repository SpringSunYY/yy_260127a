package com.lz.module.biz.dal.dataobject.worker;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工人信息 DO
 *
 * @author YY
 */
@TableName("biz_worker")
@KeySequence("biz_worker_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 工人姓名
     */
    private String workerName;
    /**
     * 性别
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private String gender;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 工人类型
     *
     * 枚举 {@link TODO biz_worker_worker_type 对应的类}
     */
    private String workerType;
    /**
     * 工种
     *
     * 枚举 {@link TODO biz_worker_work_type 对应的类}
     */
    private String workType;
    /**
     * 日薪
     */
    private BigDecimal dailySalary;
    /**
     * 月薪
     */
    private BigDecimal monthlySalary;
    /**
     * 薪资说明
     */
    private String salaryDesc;
    /**
     * 技能等级
     *
     * 枚举 {@link TODO biz_worker_skill_level 对应的类}
     */
    private String skillLevel;
    /**
     * 状态
     *
     * 枚举 {@link TODO biz_worker_status 对应的类}
     */
    private String status;
    /**
     * 备注
     */
    private String remark;


}
