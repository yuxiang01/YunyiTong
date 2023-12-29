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

  // 患者姓名
  private String patient;

  // 患者sex
  private String sex;

  // 患者age
  private Integer age;

  // 患者phone
  private String phone;
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

  // 时间范围查询
  private Date[] queryTime;

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

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date[] getQueryTime() {
    return queryTime;
  }

  public void setQueryTime(Date[] queryTime) {
    this.queryTime = queryTime;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("regId", getRegId())
        .append("patientId", getPatientId())
        .append("patient", getPatient())
        .append("sex", getSex())
        .append("age", getAge())
        .append("phone", getPhone())
        .append("type", getType())
        .append("orderFee", getOrderFee())
        .append("deptId", getDeptId())
        .append("deptName", getDeptName())
        .append("doctorId", getDoctorId())
        .append("doctor", getDoctor())
        .append("receTime", getReceTime())
        .append("status", getStatus())
        .append("queryTime", getQueryTime())
        .toString();
  }
}
