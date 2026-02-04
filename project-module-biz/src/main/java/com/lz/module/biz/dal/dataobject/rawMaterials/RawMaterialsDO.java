package com.lz.module.biz.dal.dataobject.rawMaterials;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 原材料信息 DO
 *
 * @author YY
 */
@TableName("biz_raw_materials")
@KeySequence("biz_raw_materials_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialsDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 材料名称
     */
    private String materialName;
    /**
     * 规格型号
     */
    private String materialSpec;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 采购单价
     */
    private BigDecimal unitPrice;
    /**
     * 备注
     */
    private String remark;


}