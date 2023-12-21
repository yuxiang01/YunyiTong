package com.yyt.os.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 检查项目对象 os_check_item
 *
 * @author yyt
 * @date 2023-12-19
 */
public class OsCheckItem extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 项目编号
   */
  private Long checkId;

  /**
   * 项目名称
   */
  @Excel(name = "项目名称")
  private String name;

  /**
   * 项目类别
   */
  @Excel(name = "项目类别")
  private String type;

  /**
   * 部位
   */
  @Excel(name = "部位")
  private String part;

  /**
   * 零售价
   */
  @Excel(name = "零售价")
  private BigDecimal retailPrice;

  /**
   * 单位
   */
  @Excel(name = "单位")
  private String unit;

  /**
   * 项目状态
   */
  @Excel(name = "项目状态")
  private Long status;

  public void setCheckId(Long checkId) {
    this.checkId = checkId;
  }

  public Long getCheckId() {
    return checkId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public String getPart() {
    return part;
  }

  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getUnit() {
    return unit;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public Long getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("checkId", getCheckId())
        .append("name", getName())
        .append("type", getType())
        .append("part", getPart())
        .append("retailPrice", getRetailPrice())
        .append("unit", getUnit())
        .append("status", getStatus())
        .append("remark", getRemark())
        .append("createTime", getCreateTime())
        .append("updateTime", getUpdateTime())
        .toString();
  }
}
