package com.yyt.os.mapper;

import java.util.List;

import com.yyt.os.domain.OsCaseHistory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 病历Mapper接口
 *
 * @author yyt
 * @date 2023-12-21
 */
public interface OsCaseHistoryMapper {
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
   * 删除病历
   *
   * @param caseId 病历主键
   * @return 结果
   */
  public int deleteOsCaseHistoryByCaseId(Long caseId);

  /**
   * 批量删除病历
   *
   * @param caseIds 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteOsCaseHistoryByCaseIds(Long[] caseIds);

  @Delete("DELETE FROM os_case_history WHERE pre_code = #{preCode}")
  int deleteOsCaseHistoryByPreCode(String preCode);

  OsCaseHistory selectOsCaseHistoryByPreCode(String preCode);
}
