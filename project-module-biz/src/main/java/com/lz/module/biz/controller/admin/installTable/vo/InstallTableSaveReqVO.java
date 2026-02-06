package com.lz.module.biz.controller.admin.installTable.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 装表信息新增/修改 Request VO")
@Data
public class InstallTableSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21499")
    private Long id;

    @Schema(description = "安装日期")
    private LocalDateTime installDate;

    @Schema(description = "小区名称", example = "张三")
    private String communityName;

    @Schema(description = "门牌地址")
    private String houseAddress;

    @Schema(description = "DN15镀锌钢管(m)")
    private BigDecimal dn15PipeLength;

    @Schema(description = "DN25镀锌钢管(m)")
    private BigDecimal dn25PipeLength;

    @Schema(description = "DN15弯头(个)")
    private Integer dn15ElbowQty;

    @Schema(description = "DN15内接(个)")
    private Integer dn15InnerConnectorQty;

    @Schema(description = "DN15直接(个)")
    private Integer dn15DirectQty;

    @Schema(description = "管卡(个)")
    private Integer pipeClampQty;

    @Schema(description = "镀锌三通(个)")
    private Integer galvanizedTeeQty;

    @Schema(description = "表前球阀(只)")
    private Integer preMeterValveQty;

    @Schema(description = "双直嘴阀(只)")
    private Integer doubleNozzleValveQty;

    @Schema(description = "单直嘴阀(只)")
    private Integer singleNozzleValveQty;

    @Schema(description = "表接头(只)")
    private Integer meterConnectorQty;

    @Schema(description = "表号")
    private String meterNo;

    @Schema(description = "型号")
    private String meterModel;

    @Schema(description = "表向")
    private String meterDirection;

    @Schema(description = "层高及入住情况", example = "2")
    private String floorHeightStatus;

    @Schema(description = "户主", example = "张三")
    private String ownerName;

    @Schema(description = "联系方式")
    private String contactPhone;

    @Schema(description = "超长费用(元)")
    private BigDecimal extraLengthFee;

    @Schema(description = "加表箱")
    private String addMeterBox;

    @Schema(description = "安装人员", example = "赵六")
    private String installerName;

    @Schema(description = "高空作业")
    private String isHighAltitude;

    @Schema(description = "开T")
    private String isOpenTee;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}