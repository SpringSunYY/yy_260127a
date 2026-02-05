package com.lz.module.biz.dal.dataobject.purchaseOrder;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 采购信息 DO
 *
 * @author YY
 */
@TableName("biz_purchase_order")
@KeySequence("biz_purchase_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 采购单号
     */
    private String orderNo;
    /**
     * 采购名称
     */
    private String name;
    /**
     * 供应商
     */
    private Long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 采购人
     */
    private String purchaserName;
    /**
     * 采购金额
     */
    private BigDecimal totalAmount;
    /**
     * 采购数量
     */
    private BigDecimal totalQuantity;
    /**
     * 期望到货日期
     */
    private LocalDateTime expectedTime;
    /**
     * 实际到货日期
     */
    private LocalDateTime actualTime;
    /**
     * 采购状态
     *
     * 枚举 {@link TODO biz_purchase_order_status 对应的类}
     */
    private String orderStatus;
    /**
     * 备注
     */
    private String remark;


}