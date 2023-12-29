package com.yyt.os.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * 处方订单对象 os_pre_order
 *
 * @author yyt
 * @date 2023-12-21
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
  @NotNull(message = "参数传递不全")
  private Long patientId;

  /**
   * 挂号订单编号
   */
  @Excel(name = "挂号订单编号")
  @NotNull(message = "参数传递不全")
  private String registerId;

  /**
   * 接诊类型
   */
  @Excel(name = "接诊类型")
  @NotNull(message = "参数传递不全")
  private String type;

  /**
   * 总金额
   */
  @Excel(name = "总金额")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal money;


  /**
   * 支付方式
   */
  @Excel(name = "支付方式")
  private String payMethod;

  /**
   * 支付时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
  private Date payTime;

  /**
   * 处方信息
   */
  private List<OsPrescription> osPrescriptionList;

  private OsCaseHistory caseHistory;
  private OsPhysique physique;
  private OsRegorder regorder;

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

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
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

  public OsCaseHistory getCaseHistory() {
    return caseHistory;
  }

  public void setCaseHistory(OsCaseHistory caseHistory) {
    this.caseHistory = caseHistory;
  }

  public OsPhysique getPhysique() {
    return physique;
  }

  public void setPhysique(OsPhysique physique) {
    this.physique = physique;
  }

  public OsRegorder getRegorder() {
    return regorder;
  }

  public void setRegorder(OsRegorder regorder) {
    this.regorder = regorder;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("preCode", getPreCode())
        .append("patientId", getPatientId())
        .append("registerId", getRegisterId())
        .append("type", getType())
        .append("money", getMoney())
        .append("payMethod", getPayMethod())
        .append("payTime", getPayTime())
        .append("createTime", getCreateTime())
        .append("updateTime", getUpdateTime())
        .append("osPrescriptionList", getOsPrescriptionList())
        .toString();
  }
}
