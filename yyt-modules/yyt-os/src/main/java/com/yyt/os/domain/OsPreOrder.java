package com.yyt.os.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 处方订单对象 os_pre_order
 *
 * @author yyt
 * @date 2023-12-18
 */
public class OsPreOrder extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 订单编号
   */
  private String preCode;

  /**
   * 患者编号
   */
  @Excel(name = "患者编号")
  private Long patientId;

  /**
   * 挂号订单编号
   */
  @Excel(name = "挂号订单编号")
  private String registerId;

  /**
   * 总金额
   */
  @Excel(name = "总金额")
  private BigDecimal money;

  /**
   * 支付方式
   */
  @Excel(name = "支付方式")
  private String payMethod;

  /**
   * 支付时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
  private Date payTime;

  /**
   * 附加费用
   */
  private String addCost;

  /**
   * 处方信息
   */
  private List<OsPrescription> osPrescriptionList;

  public void setPreCode(String preCode) {
    this.preCode = preCode;
  }

  public String getPreCode() {
    return preCode;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setRegisterId(String registerId) {
    this.registerId = registerId;
  }

  public String getRegisterId() {
    return registerId;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setPayMethod(String payMethod) {
    this.payMethod = payMethod;
  }

  public String getPayMethod() {
    return payMethod;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public Date getPayTime() {
    return payTime;
  }

  public List<OsPrescription> getOsPrescriptionList() {
    return osPrescriptionList;
  }

  public void setOsPrescriptionList(List<OsPrescription> osPrescriptionList) {
    this.osPrescriptionList = osPrescriptionList;
  }

  public void setAddCost(String addCost) {
    this.addCost = addCost;
  }

  public String getAddCost() {
    return addCost;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("preCode", getPreCode())
        .append("patientId", getPatientId())
        .append("registerId", getRegisterId())
        .append("money", getMoney())
        .append("addCost", getAddCost())
        .append("payMethod", getPayMethod())
        .append("payTime", getPayTime())
        .append("createTime", getCreateTime())
        .append("updateTime", getUpdateTime())
        .append("osPrescriptionList", getOsPrescriptionList())
        .toString();
  }
}
