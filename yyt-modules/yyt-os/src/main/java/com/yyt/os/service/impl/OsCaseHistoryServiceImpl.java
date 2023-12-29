package com.yyt.os.service.impl;

import java.util.List;

import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsCaseHistoryMapper;
import com.yyt.os.domain.OsCaseHistory;
import com.yyt.os.service.IOsCaseHistoryService;

/**
 * 病历Service业务层处理
 *
 * @author yyt
 * @date 2023-12-21
 */
@Service
public class OsCaseHistoryServiceImpl implements IOsCaseHistoryService {
  @Autowired
  private OsCaseHistoryMapper osCaseHistoryMapper;

  /**
   * 查询病历
   *
   * @param caseId 病历主键
   * @return 病历
   */
  @Override
  public OsCaseHistory selectOsCaseHistoryByCaseId(Long caseId) {
    return osCaseHistoryMapper.selectOsCaseHistoryByCaseId(caseId);
  }

  /**
   * 查询病历列表
   *
   * @param osCaseHistory 病历
   * @return 病历
   */
  @Override
  public List<OsCaseHistory> selectOsCaseHistoryList(OsCaseHistory osCaseHistory) {
    return osCaseHistoryMapper.selectOsCaseHistoryList(osCaseHistory);
  }

  /**
   * 新增病历
   *
   * @param osCaseHistory 病历
   * @return 结果
   */
  @Override
  public int insertOsCaseHistory(OsCaseHistory osCaseHistory) {
    osCaseHistory.setCreateTime(DateUtils.getNowDate());
    return osCaseHistoryMapper.insertOsCaseHistory(osCaseHistory);
  }

  /**
   * 修改病历
   *
   * @param osCaseHistory 病历
   * @return 结果
   */
  @Override
  public int updateOsCaseHistory(OsCaseHistory osCaseHistory) {
    osCaseHistory.setUpdateTime(DateUtils.getNowDate());
    return osCaseHistoryMapper.updateOsCaseHistory(osCaseHistory);
  }

  /**
   * 批量删除病历
   *
   * @param caseIds 需要删除的病历主键
   * @return 结果
   */
  @Override
  public int deleteOsCaseHistoryByCaseIds(Long[] caseIds) {
    return osCaseHistoryMapper.deleteOsCaseHistoryByCaseIds(caseIds);
  }

  /**
   * 删除病历信息
   *
   * @param caseId 病历主键
   * @return 结果
   */
  @Override
  public int deleteOsCaseHistoryByCaseId(Long caseId) {
    return osCaseHistoryMapper.deleteOsCaseHistoryByCaseId(caseId);
  }

  @Override
  public int deleteOsCaseHistoryByPreCode(String preCode) {
    return osCaseHistoryMapper.deleteOsCaseHistoryByPreCode(preCode);
  }
}
