package com.lz.module.biz.dal.dataobject.salary;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工资信息 DO
 *
 * @author 芋道源码
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
     * 工人编号
     */
    private Long workerId;
    /**
     * 工人姓名
     */
    private String workerName;
    /**
     * 是否结算
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isSettlement;
    /**
     * 结算日期
     */
    private LocalDateTime settlementTime;
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
     * 应发款项
     */
    private BigDecimal payableAmount;
    /**
     * 备注
     */
    private String remark;


}