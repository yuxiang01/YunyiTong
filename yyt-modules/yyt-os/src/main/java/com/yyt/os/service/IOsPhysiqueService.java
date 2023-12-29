package com.yyt.os.service;

import java.util.List;

import com.yyt.os.domain.OsPhysique;

/**
 * 体格Service接口
 *
 * @author yyt
 * @date 2023-12-21
 */
public interface IOsPhysiqueService {
  /**
   * 查询体格
   *
   * @param physiqueId 体格主键
   * @return 体格
   */
  public OsPhysique selectOsPhysiqueByPhysiqueId(Long physiqueId);

  /**
   * 查询体格列表
   *
   * @param osPhysique 体格
   * @return 体格集合
   */
  public List<OsPhysique> selectOsPhysiqueList(OsPhysique osPhysique);

  /**
   * 新增体格
   *
   * @param osPhysique 体格
   * @return 结果
   */
  public int insertOsPhysique(OsPhysique osPhysique);

  /**
   * 修改体格
   *
   * @param osPhysique 体格
   * @return 结果
   */
  public int updateOsPhysique(OsPhysique osPhysique);

  /**
   * 批量删除体格
   *
   * @param physiqueIds 需要删除的体格主键集合
   * @return 结果
   */
  public int deleteOsPhysiqueByPhysiqueIds(Long[] physiqueIds);

  // 通过preCode删除
  int deleteOsPhysiqueByPreCode(String preCode);

  /**
   * 删除体格信息
   *
   * @param physiqueId 体格主键
   * @return 结果
   */
  public int deleteOsPhysiqueByPhysiqueId(Long physiqueId);
}
