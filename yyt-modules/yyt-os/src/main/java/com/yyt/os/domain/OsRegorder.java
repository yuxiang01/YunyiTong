package com.yyt.os.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 挂号订单对象 os_regorder
 *
 * @author yyt
 * @date 2023-11-24
 */
public class OsRegorder extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 挂号单号
   */
  @Excel(name = "挂号单号")
  private String regId;

  /**
   * 患者编号
   */
  @Excel(name = "患者编号")
  private Long patientId;

  private String patient;

  /**
   * 接诊类型
   */
  @Excel(name = "接诊类型")
  private String type;

  /**
   * 挂单费用
   */
  private String orderFee;

  /**
   * 诊疗费
   */
  @Excel(name = "诊疗费")
  private String fee;

  /**
   * 科室
   */
  @Excel(name = "科室")
  private Long deptId;

  private String deptName;

  /**
   * 接诊医生
   */
  @Excel(name = "接诊医生")
  private Long doctorId;

  private String doctor;

  /**
   * 就诊时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "就诊时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
  private Date receTime;

  /**
   * 就诊状态
   */
  @Excel(name = "就诊状态")
  private String status;

  /**
   * 实收金额
   */
  @Excel(name = "实收金额")
  private BigDecimal money;

  /**
   * 支付方式
   */
  @Excel(name = "支付方式")
  private String payMethod;

  /**
   * 收费日期
   */
  private Date payTime;

  public void setRegId(String regId) {
    this.regId = regId;
  }

  public String getRegId() {
    return regId;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setOrderFee(String orderFee) {
    this.orderFee = orderFee;
  }

  public String getOrderFee() {
    return orderFee;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public String getFee() {
    return fee;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDoctorId(Long doctorId) {
    this.doctorId = doctorId;
  }

  public Long getDoctorId() {
    return doctorId;
  }

  public void setReceTime(Date receTime) {
    this.receTime = receTime;
  }

  public Date getReceTime() {
    return receTime;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
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

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getDoctor() {
    return doctor;
  }

  public void setDoctor(String doctor) {
    this.doctor = doctor;
  }

  public String getPatient() {
    return patient;
  }

  public void setPatient(String patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("regId", getRegId())
        .append("patientId", getPatientId())
        .append("patient", getPatient())
        .append("type", getType())
        .append("orderFee", getOrderFee())
        .append("fee", getFee())
        .append("deptId", getDeptId())
        .append("deptName", getDeptName())
        .append("doctorId", getDoctorId())
        .append("doctor", getDoctor())
        .append("receTime", getReceTime())
        .append("status", getStatus())
        .append("money", getMoney())
        .append("payMethod", getPayMethod())
        .append("payTime", getPayTime())
        .toString();
  }
}
