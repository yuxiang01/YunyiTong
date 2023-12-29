package com.yyt.os.service;

import java.util.List;

import com.yyt.os.domain.OsCaseHistory;

/**
 * 病历Service接口
 *
 * @author yyt
 * @date 2023-12-21
 */
public interface IOsCaseHistoryService {
  /**
   * 查询病历
   *
   * @param caseId 病历主键
   * @return 病历
   */
  public OsCaseHistory selectOsCaseHistoryByCaseId(Long caseId);

  /**
   * 查询病历列表
   *
   * @param osCaseHistory 病历
   * @return 病历集合
   */
  public List<OsCaseHistory> selectOsCaseHistoryList(OsCaseHistory osCaseHistory);

  /**
   * 新增病历
   *
   * @param osCaseHistory 病历
   * @return 结果
   */
  public int insertOsCaseHistory(OsCaseHistory osCaseHistory);

  /**
   * 修改病历
   *
   * @param osCaseHistory 病历
   * @return 结果
   */
  public int updateOsCaseHistory(OsCaseHistory osCaseHistory);

  /**
   * 批量删除病历
   *
   * @param caseIds 需要删除的病历主键集合
   * @return 结果
   */
  public int deleteOsCaseHistoryByCaseIds(Long[] caseIds);

  /**
   * 删除病历信息
   *
   * @param caseId 病历主键
   * @return 结果
   */
  public int deleteOsCaseHistoryByCaseId(Long caseId);

  int deleteOsCaseHistoryByPreCode(String preCode);
}
