package com.lz.module.biz.controller.admin.installTable.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lz.framework.excel.core.annotations.DictFormat;
import com.lz.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lz.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 装表信息 导入 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class InstallTableImportVO {

    @Schema(description = "安装日期")
    @ExcelProperty("安装日期")
    @com.alibaba.excel.annotation.format.DateTimeFormat(value = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime installDate;

    @Schema(description = "小区名称", example = "张三")
    @ExcelProperty("小区名称")
    private String communityName;

    @Schema(description = "门牌地址")
    @ExcelProperty("门牌地址")
    private String houseAddress;

    @Schema(description = "DN15镀锌钢管(m)")
    @ExcelProperty("DN15镀锌钢管(m)")
    private BigDecimal dn15PipeLength;

    @Schema(description = "DN25镀锌钢管(m)")
    @ExcelProperty("DN25镀锌钢管(m)")
    private BigDecimal dn25PipeLength;

    @Schema(description = "DN15弯头(个)")
    @ExcelProperty("DN15弯头(个)")
    private Integer dn15ElbowQty;

    @Schema(description = "DN15内接(个)")
    @ExcelProperty("DN15内接(个)")
    private Integer dn15InnerConnectorQty;

    @Schema(description = "DN15直接(个)")
    @ExcelProperty("DN15直接(个)")
    private Integer dn15DirectQty;

    @Schema(description = "管卡(个)")
    @ExcelProperty("管卡(个)")
    private Integer pipeClampQty;

    @Schema(description = "镀锌三通(个)")
    @ExcelProperty("镀锌三通(个)")
    private Integer galvanizedTeeQty;

    @Schema(description = "表前球阀(只)")
    @ExcelProperty("表前球阀(只)")
    private Integer preMeterValveQty;

    @Schema(description = "双直嘴阀(只)")
    @ExcelProperty("双直嘴阀(只)")
    private Integer doubleNozzleValveQty;

    @Schema(description = "单直嘴阀(只)")
    @ExcelProperty("单直嘴阀(只)")
    private Integer singleNozzleValveQty;

    @Schema(description = "表接头(只)")
    @ExcelProperty("表接头(只)")
    private Integer meterConnectorQty;

    @Schema(description = "表号")
    @ExcelProperty("表号")
    private String meterNo;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String meterModel;

    @Schema(description = "表向")
    @ExcelProperty(value = "表向", converter = DictConvert.class)
    @DictFormat("biz_meter_direction")
    private String meterDirection;

    @Schema(description = "层高及入住情况", example = "2")
    @ExcelProperty("层高及入住情况")
    private String floorHeightStatus;

    @Schema(description = "户主", example = "张三")
    @ExcelProperty("户主")
    private String ownerName;

    @Schema(description = "联系方式")
    @ExcelProperty("联系方式")
    private String contactPhone;

    @Schema(description = "超长费用(元)")
    @ExcelProperty("超长费用(元)")
    private BigDecimal extraLengthFee;

    @Schema(description = "加表箱")
    @ExcelProperty(value = "加表箱", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String addMeterBox;

    @Schema(description = "安装人员", example = "赵六")
    @ExcelProperty("安装人员")
    private String installerName;

    @Schema(description = "高空作业")
    @ExcelProperty(value = "高空作业", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isHighAltitude;

    @Schema(description = "开T")
    @ExcelProperty(value = "开T", converter = DictConvert.class)
    @DictFormat("common_whether")
    private String isOpenTee;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;
}
