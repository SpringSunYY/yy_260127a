package com.lz.module.biz.dal.dataobject.installTable;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 装表信息 DO
 *
 * @author YY
 */
@TableName("biz_install_table")
@KeySequence("biz_install_table_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallTableDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 安装日期
     */
    private LocalDateTime installDate;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 门牌地址
     */
    private String houseAddress;
    /**
     * DN15镀锌钢管(m)
     */
    private BigDecimal dn15PipeLength;
    /**
     * DN25镀锌钢管(m)
     */
    private BigDecimal dn25PipeLength;
    /**
     * DN15弯头(个)
     */
    private Integer dn15ElbowQty;
    /**
     * DN15内接(个)
     */
    private Integer dn15InnerConnectorQty;
    /**
     * DN15直接(个)
     */
    private Integer dn15DirectQty;
    /**
     * 管卡(个)
     */
    private Integer pipeClampQty;
    /**
     * 镀锌三通(个)
     */
    private Integer galvanizedTeeQty;
    /**
     * 表前球阀(只)
     */
    private Integer preMeterValveQty;
    /**
     * 双直嘴阀(只)
     */
    private Integer doubleNozzleValveQty;
    /**
     * 单直嘴阀(只)
     */
    private Integer singleNozzleValveQty;
    /**
     * 表接头(只)
     */
    private Integer meterConnectorQty;
    /**
     * 表号
     */
    private String meterNo;
    /**
     * 型号
     */
    private String meterModel;
    /**
     * 表向
     *
     * 枚举 {@link TODO biz_meter_direction 对应的类}
     */
    private String meterDirection;
    /**
     * 层高及入住情况
     */
    private String floorHeightStatus;
    /**
     * 户主
     */
    private String ownerName;
    /**
     * 联系方式
     */
    private String contactPhone;
    /**
     * 超长费用(元)
     */
    private BigDecimal extraLengthFee;
    /**
     * 加表箱
     */
    private String addMeterBox;
    /**
     * 安装人员
     */
    private String installerName;
    /**
     * 高空作业
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isHighAltitude;
    /**
     * 开T
     *
     * 枚举 {@link TODO common_whether 对应的类}
     */
    private String isOpenTee;
    /**
     * 备注
     */
    private String remark;


}