package com.lz.module.biz.dal.dataobject.salaryPaymentOrder;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工资付款信息 DO
 *
 * @author YY
 */
@TableName("biz_salary_payment_order")
@KeySequence("biz_salary_payment_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPaymentOrderDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 付款单号
     */
    private String paymentNo;
    /**
     * 工资ID
     */
    private Long salaryId;
    /**
     * 工资名称
     */
    private String salaryName;
    /**
     * 工人ID
     */
    private Long workerId;
    /**
     * 工人名称
     */
    private String workerName;
    /**
     * 付款日期
     */
    private LocalDateTime paymentTime;
    /**
     * 付款金额
     */
    private BigDecimal paymentAmount;
    /**
     * 付款方式
     *
     * 枚举 {@link TODO biz_payment_payee_type 对应的类}
     */
    private String paymentMethod;
    /**
     * 付款凭证
     */
    private String paymentCertificate;
    /**
     * 付款事由
     */
    private String paymentPurpose;
    /**
     * 是否开票
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isInvoiced;
    /**
     * 备注
     */
    private String remark;


}