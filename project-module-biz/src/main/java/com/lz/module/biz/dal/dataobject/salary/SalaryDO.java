package com.lz.module.biz.dal.dataobject.salary;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工资信息 DO
 *
 * @author YY
 */
@TableName("biz_salary")
@KeySequence("biz_salary_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 工人编号
     */
    private Long workerId;
    /**
     * 工人姓名
     */
    private String workerName;
    /**
     * 工资周期
     */
    private String salaryCycleTime;
    /**
     * 出勤天数
     */
    private Integer attendanceDays;
    /**
     * 加班天数
     */
    private Integer overtimeDays;
    /**
     * 劳务费金额
     */
    private BigDecimal laborFeeAmount;
    /**
     * 加班费
     */
    private BigDecimal overtimeFee;
    /**
     * 补贴
     */
    private BigDecimal allowanceAmount;
    /**
     * 小计
     */
    private BigDecimal subtotalAmount;
    /**
     * 社保
     */
    private BigDecimal socialInsurance;
    /**
     * 扣款
     */
    private BigDecimal deduction;
    /**
     * 应发款项
     */
    private BigDecimal payableAmount;
    /**
     * 备注
     */
    private String remark;


}
