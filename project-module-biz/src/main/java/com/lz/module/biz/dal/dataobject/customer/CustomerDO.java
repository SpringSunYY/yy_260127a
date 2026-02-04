package com.lz.module.biz.dal.dataobject.customer;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.lz.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户信息 DO
 *
 * @author YY
 */
@TableName("biz_customer")
@KeySequence("biz_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 电话
     */
    private String telephone;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String weChat;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 地区编号
     */
    private Long areaId;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 所属行业
     *
     * 枚举 {@link TODO biz_customer_industry 对应的类}
     */
    private String industry;
    /**
     * 备注
     */
    private String remark;


}
