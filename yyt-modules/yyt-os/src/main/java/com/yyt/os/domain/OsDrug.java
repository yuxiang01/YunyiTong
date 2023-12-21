package com.yyt.os.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 药品对象 os_drug
 *
 * @author yyt
 * @date 2023-12-05
 */
public class OsDrug extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 药品编号
   */
  private Long drugId;

  /**
   * 药品名称
   */
  @Excel(name = "药品名称")
  private String name;

  /**
   * 规格
   */
  @Excel(name = "规格")
  private String specification;

  /**
   * 处方类型
   */
  @Excel(name = "处方类型")
  private Integer preType;

  /**
   * 采购价
   */
  @Excel(name = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 售药价
   */
  @Excel(name = "售药价")
  private BigDecimal drugPrice;

  /**
   * 药品状态
   */
  @Excel(name = "药品状态")
  private String status;

  /**
   * 拼音码
   */
  @Excel(name = "拼音码")
  private String pinyinCode;

  /**
   * 药品分类
   */
  @Excel(name = "药品分类")
  private String drugType;

  /**
   * 药品剂型
   */
  @Excel(name = "药品剂型")
  private String drugDosageForm;

  /**
   * 是否OTC
   */
  @Excel(name = "是否OTC")
  private Long isOtc;

  /**
   * 用法
   */
  @Excel(name = "用法")
  private String usage;

  /**
   * 单次用量
   */
  @Excel(name = "单次用量")
  private String dosage;

  @Excel(name = "单次用量单位")
  private String unit;

  // 总量
  private Integer total;

  // 总量单位
  private String totalUnit;

  /**
   * 频率
   */
  @Excel(name = "频率")
  private String frequency;

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

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public String getSpecification() {
    return specification;
  }

  public void setPreType(Integer preType) {
    this.preType = preType;
  }

  public Integer getPreType() {
    return preType;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setDrugPrice(BigDecimal drugPrice) {
    this.drugPrice = drugPrice;
  }

  public BigDecimal getDrugPrice() {
    return drugPrice;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setPinyinCode(String pinyinCode) {
    this.pinyinCode = pinyinCode;
  }

  public String getPinyinCode() {
    return pinyinCode;
  }

  public void setDrugType(String drugType) {
    this.drugType = drugType;
  }

  public String getDrugType() {
    return drugType;
  }

  public void setDrugDosageForm(String drugDosageForm) {
    this.drugDosageForm = drugDosageForm;
  }

  public String getDrugDosageForm() {
    return drugDosageForm;
  }

  public void setIsOtc(Long isOtc) {
    this.isOtc = isOtc;
  }

  public Long getIsOtc() {
    return isOtc;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getUsage() {
    return usage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }

  public String getDosage() {
    return dosage;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public String getFrequency() {
    return frequency;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public String getTotalUnit() {
    return totalUnit;
  }

  public void setTotalUnit(String totalUnit) {
    this.totalUnit = totalUnit;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("drugId", getDrugId())
        .append("name", getName())
        .append("specification", getSpecification())
        .append("preType", getPreType())
        .append("purchasePrice", getPurchasePrice())
        .append("drugPrice", getDrugPrice())
        .append("status", getStatus())
        .append("pinyinCode", getPinyinCode())
        .append("drugType", getDrugType())
        .append("drugDosageForm", getDrugDosageForm())
        .append("isOtc", getIsOtc())
        .append("usage", getUsage())
        .append("dosage", getDosage())
        .append("frequency", getFrequency())
        .append("remark", getRemark())
        .append("total", getTotal())
        .append("totalUnit", getTotalUnit())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .toString();
  }
}
