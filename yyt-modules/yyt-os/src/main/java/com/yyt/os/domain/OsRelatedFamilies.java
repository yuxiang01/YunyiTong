package com.yyt.os.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 患者家庭关系对象 os_related_families
 *
 * @author fishx
 * @date 2023-12-13
 */
public class OsRelatedFamilies extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 序号
   */
  private Long relatedId;

  /**
   * 家庭关系
   */
  private String related;

  /**
   * 病人id
   */
  private Long patientId;

  /**
   * 关联的病人id
   */
  private Long relatedPatientId;

  // 拓展字段：关联的病人信息
  private SysPatient patient;

  public void setRelatedId(Long relatedId) {
    this.relatedId = relatedId;
  }

  public Long getRelatedId() {
    return relatedId;
  }

  public void setRelated(String related) {
    this.related = related;
  }

  public String getRelated() {
    return related;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setRelatedPatientId(Long relatedPatientId) {
    this.relatedPatientId = relatedPatientId;
  }

  public Long getRelatedPatientId() {
    return relatedPatientId;
  }

  public SysPatient getPatient() {
    return patient;
  }

  public void setPatient(SysPatient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("relatedId", getRelatedId())
        .append("related", getRelated())
        .append("patientId", getPatientId())
        .append("relatedPatientId", getRelatedPatientId())
        .append("patient", getPatient())
        .append("createTime", getCreateTime())
        .append("updateTime", getUpdateTime())
        .toString();
  }
}
