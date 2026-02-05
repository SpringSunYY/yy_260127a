package com.lz.module.biz.dal.dataobject.purchaseOrderDetail;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lz.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 采购明细 DO
 *
 * @author YY
 */
@TableName("biz_purchase_order_detail")
@KeySequence("biz_purchase_order_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailDO extends BaseDO {

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
     * 采购单编号
     */
    private Long purchaseId;
    /**
     * 材料ID
     */
    private Long materialId;
    /**
     * 材料名称
     */
    private String materialName;

    /**
     * 规格型号
     */
    private String materialSpec;
    /**
     * 采购数量
     */
    private BigDecimal quantity;
    /**
     * 计量单位
     * <p>
     * 枚举 {@link TODO biz_raw_materials_unit 对应的类}
     */
    private String unit;
    /**
     * 采购单价
     */
    private BigDecimal unitPrice;
    /**
     * 小计金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;

}
