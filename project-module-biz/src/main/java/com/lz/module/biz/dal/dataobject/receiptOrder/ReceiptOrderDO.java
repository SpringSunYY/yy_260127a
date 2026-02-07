package com.lz.module.biz.dal.dataobject.receiptOrder;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收款信息 DO
 *
 * @author YY
 */
@TableName("biz_receipt_order")
@KeySequence("biz_receipt_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptOrderDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 收款单号
     */
    private String receiptNo;
    /**
     * 收款类型
     *
     * 枚举 {@link TODO biz_receipt_type 对应的类}
     */
    private String receiptType;
    /**
     * 项目类型
     *
     * 枚举 {@link TODO biz_receipt_project_type 对应的类}
     */
    private String projectType;
    /**
     * 工程类型
     *
     * 枚举 {@link TODO biz_project_engineering_type 对应的类}
     */
    private String projectScatteredType;
    /**
     * 财年
     */
    private Integer fiscalYear;
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
    private String projectName;
    /**
     * 付款方
     */
    private String payerName;
    /**
     * 收款日期
     */
    private LocalDateTime receiptDate;
    /**
     * 收款金额
     */
    private BigDecimal receiptAmount;
    /**
     * 收款方式
     *
     * 枚举 {@link TODO biz_receivable_return_type 对应的类}
     */
    private String receiptMethod;
    /**
     * 收款凭证
     */
    private String receiptCertificate;
    /**
     * 收款事由
     */
    private String receiptPurpose;
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