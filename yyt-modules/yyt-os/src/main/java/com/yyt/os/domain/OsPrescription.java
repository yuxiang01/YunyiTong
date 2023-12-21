package com.yyt.os.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 处方对象 os_prescription
 *
 * @author yyt
 * @date 2023-12-18
 */
public class OsPrescription extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 处方编号
   */
  private Long preId;

  /**
   * 订单编号
   */
  @Excel(name = "订单编号")
  private String preCode;

  /**
   * 项目编号
   */
  @Excel(name = "项目编号")
  private Long checkId;

  // 检查部位
  private String part;


  /**
   * 药品编号
   */
  @Excel(name = "药品编号")
  private Long drugId;

  /**
   * 收费名称
   */
  @Excel(name = "收费名称")
  private String name;

  /**
   * 单价
   */
  @Excel(name = "单价")
  private BigDecimal unitPrice;

  /**
   * 总量
   */
  @Excel(name = "总量")
  private Long total;

  /**
   * 单位
   */
  @Excel(name = "单位")
  private String unit;

  /**
   * 类型
   */
  @Excel(name = "类型")
  private Long type;

  /**
   * 单次用量
   */
  @Excel(name = "单次用量")
  private Long dosage;

  /**
   * 用法
   */
  @Excel(name = "用法")
  private String usage;

  /**
   * 频度
   */
  @Excel(name = "频度")
  private String frequency;


  public void setPreId(Long preId) {
    this.preId = preId;
  }

  public Long getPreId() {
    return preId;
  }

  public void setPreCode(String preCode) {
    this.preCode = preCode;
  }

  public String getPreCode() {
    return preCode;
  }

  public void setCheckId(Long checkId) {
    this.checkId = checkId;
  }

  public Long getCheckId() {
    return checkId;
  }

  public void setDrugId(Long drugId) {
    this.drugId = drugId;
  }

  public Long getDrugId() {
    return drugId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public Long getTotal() {
    return total;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getUnit() {
    return unit;
  }

  public void setType(Long type) {
    this.type = type;
  }

  public Long getType() {
    return type;
  }

  public void setDosage(Long dosage) {
    this.dosage = dosage;
  }

  public Long getDosage() {
    return dosage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getUsage() {
    return usage;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public String getFrequency() {
    return frequency;
  }

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("preId", getPreId())
        .append("preCode", getPreCode())
        .append("checkId", getCheckId())
        .append("part", getPart())
        .append("drugId", getDrugId())
        .append("name", getName())
        .append("unitPrice", getUnitPrice())
        .append("total", getTotal())
        .append("unit", getUnit())
        .append("type", getType())
        .append("dosage", getDosage())
        .append("usage", getUsage())
        .append("frequency", getFrequency())
        .toString();
  }
}
