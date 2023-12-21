package com.yyt.os.service.impl;

import java.util.List;

import com.yyt.common.core.utils.DateUtils;
import com.yyt.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsRelatedFamiliesMapper;
import com.yyt.os.domain.OsRelatedFamilies;
import com.yyt.os.service.IOsRelatedFamiliesService;

/**
 * 患者家庭关系Service业务层处理
 *
 * @author fishx
 * @date 2023-12-13
 */
@Service
public class OsRelatedFamiliesServiceImpl implements IOsRelatedFamiliesService {
  @Autowired
  private OsRelatedFamiliesMapper osRelatedFamiliesMapper;

  /**
   * 查询患者家庭关系
   *
   * @param relatedId 患者家庭关系主键
   * @return 患者家庭关系
   */
  @Override
  public OsRelatedFamilies selectOsRelatedFamiliesByRelatedId(Long relatedId) {
    return osRelatedFamiliesMapper.selectOsRelatedFamiliesByRelatedId(relatedId);
  }

  /**
   * 查询患者家庭关系列表
   *
   * @param patientId 患者id
   * @return 患者家庭关系
   */
  @Override
  public List<OsRelatedFamilies> selectOsRelatedFamiliesList(Long patientId) {
    // 首先，正向查询
    List<OsRelatedFamilies> relatedFamilies = osRelatedFamiliesMapper.selectOsRelatedFamiliesList(patientId);
    // 接着，关联查询
    List<OsRelatedFamilies> osRelatedFamilies = osRelatedFamiliesMapper.selectRelatedListByPatientId(patientId);
    relatedFamilies.addAll(osRelatedFamilies);
    return relatedFamilies;
  }

  /**
   * 新增患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  @Override
  public AjaxResult insertOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies) {
    if (osRelatedFamiliesMapper.selectOsRelatedFamiliesByRelated(osRelatedFamilies) != null) {
      return AjaxResult.error("该关系已经绑定了，不用重复绑定");
    }
    osRelatedFamilies.setCreateTime(DateUtils.getNowDate());
    return osRelatedFamiliesMapper.insertOsRelatedFamilies(osRelatedFamilies) > 0
        ? AjaxResult.success() : AjaxResult.error();
  }

  /**
   * 修改患者家庭关系
   *
   * @param osRelatedFamilies 患者家庭关系
   * @return 结果
   */
  @Override
  public int updateOsRelatedFamilies(OsRelatedFamilies osRelatedFamilies) {
    osRelatedFamilies.setUpdateTime(DateUtils.getNowDate());
    return osRelatedFamiliesMapper.updateOsRelatedFamilies(osRelatedFamilies);
  }

  /**
   * 批量删除患者家庭关系
   *
   * @param relatedIds 需要删除的患者家庭关系主键
   * @return 结果
   */
  @Override
  public int deleteOsRelatedFamiliesByRelatedIds(Long[] relatedIds) {
    return osRelatedFamiliesMapper.deleteOsRelatedFamiliesByRelatedIds(relatedIds);
  }

  /**
   * 删除患者家庭关系信息
   *
   * @param relatedId 患者家庭关系主键
   * @return 结果
   */
  @Override
  public int deleteOsRelatedFamiliesByRelatedId(Long relatedId) {
    return osRelatedFamiliesMapper.deleteOsRelatedFamiliesByRelatedId(relatedId);
  }
}
