package com.yyt.os.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 体格对象 os_physique
 *
 * @author yyt
 * @date 2023-12-21
 */
public class OsPhysique extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 体格编号
   */
  private Long physiqueId;

  /**
   * 处方/门诊编号
   */
  @Excel(name = "处方/门诊编号")
  private String preCode;

  /**
   * 体温
   */
  @Excel(name = "体温")
  private BigDecimal temp;

  /**
   * 呼吸频率
   */
  @Excel(name = "呼吸频率")
  private Long rate;

  /**
   * 脉搏
   */
  @Excel(name = "脉搏")
  private Long pulse;

  /**
   * 血压收缩压
   */
  @Excel(name = "血压收缩压")
  private Long systolicBloodPressure;

  /**
   * 血压舒张压
   */
  @Excel(name = "血压舒张压")
  private Long diastolicBloodPressure;

  /**
   * 身高
   */
  @Excel(name = "身高")
  private Long height;

  /**
   * 体重
   */
  @Excel(name = "体重")
  private Long weight;

  /**
   * 血糖
   */
  @Excel(name = "血糖")
  private Long bloodSugar;

  /**
   * 血脂
   */
  @Excel(name = "血脂")
  private Long bloodLipid;

  public void setPhysiqueId(Long physiqueId) {
    this.physiqueId = physiqueId;
  }

  public Long getPhysiqueId() {
    return physiqueId;
  }

  public void setPreCode(String preCode) {
    this.preCode = preCode;
  }

  public String getPreCode() {
    return preCode;
  }

  public void setTemp(BigDecimal temp) {
    this.temp = temp;
  }

  public BigDecimal getTemp() {
    return temp;
  }

  public void setRate(Long rate) {
    this.rate = rate;
  }

  public Long getRate() {
    return rate;
  }

  public void setPulse(Long pulse) {
    this.pulse = pulse;
  }

  public Long getPulse() {
    return pulse;
  }

  public void setSystolicBloodPressure(Long systolicBloodPressure) {
    this.systolicBloodPressure = systolicBloodPressure;
  }

  public Long getSystolicBloodPressure() {
    return systolicBloodPressure;
  }

  public void setDiastolicBloodPressure(Long diastolicBloodPressure) {
    this.diastolicBloodPressure = diastolicBloodPressure;
  }

  public Long getDiastolicBloodPressure() {
    return diastolicBloodPressure;
  }

  public void setHeight(Long height) {
    this.height = height;
  }

  public Long getHeight() {
    return height;
  }

  public void setWeight(Long weight) {
    this.weight = weight;
  }

  public Long getWeight() {
    return weight;
  }

  public void setBloodSugar(Long bloodSugar) {
    this.bloodSugar = bloodSugar;
  }

  public Long getBloodSugar() {
    return bloodSugar;
  }

  public void setBloodLipid(Long bloodLipid) {
    this.bloodLipid = bloodLipid;
  }

  public Long getBloodLipid() {
    return bloodLipid;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("physiqueId", getPhysiqueId())
        .append("preCode", getPreCode())
        .append("temp", getTemp())
        .append("rate", getRate())
        .append("pulse", getPulse())
        .append("systolicBloodPressure", getSystolicBloodPressure())
        .append("diastolicBloodPressure", getDiastolicBloodPressure())
        .append("height", getHeight())
        .append("weight", getWeight())
        .append("bloodSugar", getBloodSugar())
        .append("bloodLipid", getBloodLipid())
        .toString();
  }
}
