package com.yyt.os.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 病历对象 os_case_history
 *
 * @author yyt
 * @date 2023-12-21
 */
public class OsCaseHistory extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 病历编号
   */
  private Long caseId;

  /**
   * 处方/门诊编号
   */
  @Excel(name = "处方/门诊编号")
  private String preCode;

  /**
   * 症状出现日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "症状出现日期", width = 30, dateFormat = "yyyy-MM-dd")
  @NotNull(message = "症状出现日期不能为空")
  private Date dateOfOnset;

  /**
   * 主诉
   */
  @Excel(name = "主诉")
  @NotBlank(message = "主诉内容不能为空")
  private String activeInChief;

  /**
   * 现病史
   */
  @Excel(name = "现病史")
  private String hpi;

  /**
   * 既往史
   */
  @Excel(name = "既往史")
  private String pastHistory;

  /**
   * 过敏史
   */
  @Excel(name = "过敏史")
  private String allergy;

  /**
   * 家族史
   */
  @Excel(name = "家族史")
  private String familyHistory;

  /**
   * 治疗意见
   */
  @Excel(name = "医嘱")
  private String idea;

  // 诊断
  @Excel(name = "诊断")
  @NotBlank(message = "诊断结果不能为空")
  private String diagnosis;

  public void setCaseId(Long caseId) {
    this.caseId = caseId;
  }

  public Long getCaseId() {
    return caseId;
  }

  public void setPreCode(String preCode) {
    this.preCode = preCode;
  }

  public String getPreCode() {
    return preCode;
  }

  public void setDateOfOnset(Date dateOfOnset) {
    this.dateOfOnset = dateOfOnset;
  }

  public Date getDateOfOnset() {
    return dateOfOnset;
  }

  public void setActiveInChief(String activeInChief) {
    this.activeInChief = activeInChief;
  }

  public String getActiveInChief() {
    return activeInChief;
  }

  public void setHpi(String hpi) {
    this.hpi = hpi;
  }

  public String getHpi() {
    return hpi;
  }

  public void setPastHistory(String pastHistory) {
    this.pastHistory = pastHistory;
  }

  public String getPastHistory() {
    return pastHistory;
  }

  public void setAllergy(String allergy) {
    this.allergy = allergy;
  }

  public String getAllergy() {
    return allergy;
  }

  public void setFamilyHistory(String familyHistory) {
    this.familyHistory = familyHistory;
  }

  public String getFamilyHistory() {
    return familyHistory;
  }

  public void setIdea(String idea) {
    this.idea = idea;
  }

  public String getIdea() {
    return idea;
  }

  public String getDiagnosis() {
    return diagnosis;
  }

  public void setDiagnosis(String diagnosis) {
    this.diagnosis = diagnosis;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("caseId", getCaseId())
        .append("preCode", getPreCode())
        .append("dateOfOnset", getDateOfOnset())
        .append("activeInChief", getActiveInChief())
        .append("hpi", getHpi())
        .append("pastHistory", getPastHistory())
        .append("allergy", getAllergy())
        .append("familyHistory", getFamilyHistory())
        .append("idea", getIdea())
        .append("remark", getRemark())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("diagnosis", getDiagnosis())
        .toString();
  }
}
