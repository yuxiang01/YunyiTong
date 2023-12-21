package com.yyt.os.service;

import java.util.List;

import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.os.domain.OsRelatedFamilies;

/**
 * 患者家庭关系Service接口
 *
 * @author fishx
 * @date 2023-12-13
 */
public interface IOsRelatedFamiliesService {
  /**
   * 查询患者家庭关系
   *
   * @param relatedId 患者家庭关系主键
   * @return 患者家庭关系
   */
  OsRelatedFamilies selectOsRelatedFamiliesByRelatedId(Long relatedId);

  /**
   * 查询患者家庭关系列表
   *
   * @param patientId 患者id
   * @return 患者家庭关系集合
   */
  List<OsRelatedFamilies> selectOsRelatedFamiliesList(Long patientId);

  /**
   * 新增患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  AjaxResult insertOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies);

  /**
   * 修改患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  int updateOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies);

  /**
   * 批量删除患者家庭关系
   *
   * @param relatedIds 需要删除的患者家庭关系主键集合
   * @return 结果
   */
  int deleteOsRelatedFamiliesByRelatedIds(Long[] relatedIds);

  /**
   * 删除患者家庭关系信息
   *
   * @param relatedId 患者家庭关系主键
   * @return 结果
   */
  int deleteOsRelatedFamiliesByRelatedId(Long relatedId);
}
