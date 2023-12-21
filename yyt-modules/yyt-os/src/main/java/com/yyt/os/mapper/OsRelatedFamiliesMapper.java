package com.yyt.os.mapper;

import java.util.List;

import com.yyt.os.domain.OsRelatedFamilies;

/**
 * 患者家庭关系Mapper接口
 *
 * @author fishx
 * @date 2023-12-13
 */
public interface OsRelatedFamiliesMapper {
  /**
   * 查询患者家庭关系
   *
   * @param relatedId 患者家庭关系主键
   * @return 患者家庭关系
   */
  OsRelatedFamilies selectOsRelatedFamiliesByRelatedId(Long relatedId);

  OsRelatedFamilies selectOsRelatedFamiliesByRelated(OsRelatedFamilies osRelatedFamilies);

  /**
   * 查询患者家庭关系列表(根据患者id查询关联关系)
   *
   * @param patientId 患者id
   * @return 患者家庭关系集合
   */
  List<OsRelatedFamilies> selectOsRelatedFamiliesList(Long patientId);

  /**
   * 查询患者家庭关系列表(被关联关系)
   *
   * @param patientId 患者related_patient_id
   * @return 患者家庭关系集合
   */
  List<OsRelatedFamilies> selectRelatedListByPatientId(Long patientId);

  /**
   * 新增患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  int insertOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies);

  /**
   * 修改患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  int updateOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies);

  /**
   * 删除患者家庭关系
   *
   * @param relatedId 患者家庭关系主键
   * @return 结果
   */
  int deleteOsRelatedFamiliesByRelatedId(Long relatedId);

  /**
   * 批量删除患者家庭关系
   *
   * @param relatedIds 需要删除的数据主键集合
   * @return 结果
   */
  int deleteOsRelatedFamiliesByRelatedIds(Long[] relatedIds);
}
