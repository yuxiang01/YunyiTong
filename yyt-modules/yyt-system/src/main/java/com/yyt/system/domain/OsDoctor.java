package com.yyt.system.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 医生对象 os_doctor
 *
 * @author yyt
 * @date 2023-11-23
 */
public class OsDoctor extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  private Long doctorId;

  /**
   * 科室
   */
  private Long deptId;

  @Excel(name = "科室")
  private String deptName;

  /**
   * 姓名
   */
  @Excel(name = "姓名")
  private String name;


  /**
   * 手机号
   */
  @Excel(name = "手机号")
  private String phone;

  /**
   * 性别
   */
  @Excel(name = "性别")
  private String sex;

  /**
   * 职位
   */
  @Excel(name = "职位")
  private String postName;

  private Long position;

  /**
   * 年龄
   */
  @Excel(name = "年龄")
  private Long age;

  /**
   * 挂号费用
   */
  @Excel(name = "挂号费用")
  private BigDecimal cost;

  /**
   * 状态
   */
  @Excel(name = "状态")
  private String status;

  /**
   * 挂号人数
   */
  @Excel(name = "挂号人数")
  private Long regNumber;

  /**
   * 每小时挂号人数
   */
  @Excel(name = "每小时挂号人数")
  private Long regPerHour;

  /**
   * 医生简介
   */
  @Excel(name = "医生简介")
  private String intro;

  /**
   * 擅长领域
   */
  @Excel(name = "擅长领域")
  private String areasOfExpertise;

  /**
   * 值班信息
   */
  @Excel(name = "值班信息")
  private String dutyInfo;

  public void setDoctorId(Long doctorId) {
    this.doctorId = doctorId;
  }

  public Long getDoctorId() {
    return doctorId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhone() {
    return phone;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getSex() {
    return sex;
  }

  public void setPosition(Long position) {
    this.position = position;
  }

  public Long getPosition() {
    return position;
  }

  public void setAge(Long age) {
    this.age = age;
  }

  public Long getAge() {
    return age;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setRegNumber(Long regNumber) {
    this.regNumber = regNumber;
  }

  public Long getRegNumber() {
    return regNumber;
  }

  public void setRegPerHour(Long regPerHour) {
    this.regPerHour = regPerHour;
  }

  public Long getRegPerHour() {
    return regPerHour;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public String getIntro() {
    return intro;
  }

  public void setAreasOfExpertise(String areasOfExpertise) {
    this.areasOfExpertise = areasOfExpertise;
  }

  public String getAreasOfExpertise() {
    return areasOfExpertise;
  }

  public void setDutyInfo(String dutyInfo) {
    this.dutyInfo = dutyInfo;
  }

  public String getDutyInfo() {
    return dutyInfo;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getPostName() {
    return postName;
  }

  public void setPostName(String postName) {
    this.postName = postName;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("doctorId", getDoctorId())
        .append("deptId", getDeptId())
        .append("name", getName())
        .append("phone", getPhone())
        .append("sex", getSex())
        .append("position", getPosition())
        .append("age", getAge())
        .append("cost", getCost())
        .append("status", getStatus())
        .append("regNumber", getRegNumber())
        .append("regPerHour", getRegPerHour())
        .append("intro", getIntro())
        .append("areasOfExpertise", getAreasOfExpertise())
        .append("dutyInfo", getDutyInfo())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("deptName", getDeptName())
        .append("postName", getPostName())
        .toString();
  }
}
