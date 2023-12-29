package com.yyt.os.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsPhysiqueMapper;
import com.yyt.os.domain.OsPhysique;
import com.yyt.os.service.IOsPhysiqueService;

/**
 * 体格Service业务层处理
 *
 * @author yyt
 * @date 2023-12-21
 */
@Service
public class OsPhysiqueServiceImpl implements IOsPhysiqueService {
  @Autowired
  private OsPhysiqueMapper osPhysiqueMapper;

  /**
   * 查询体格
   *
   * @param physiqueId 体格主键
   * @return 体格
   */
  @Override
  public OsPhysique selectOsPhysiqueByPhysiqueId(Long physiqueId) {
    return osPhysiqueMapper.selectOsPhysiqueByPhysiqueId(physiqueId);
  }

  /**
   * 查询体格列表
   *
   * @param osPhysique 体格
   * @return 体格
   */
  @Override
  public List<OsPhysique> selectOsPhysiqueList(OsPhysique osPhysique) {
    return osPhysiqueMapper.selectOsPhysiqueList(osPhysique);
  }

  /**
   * 新增体格
   *
   * @param osPhysique 体格
   * @return 结果
   */
  @Override
  public int insertOsPhysique(OsPhysique osPhysique) {
    return osPhysiqueMapper.insertOsPhysique(osPhysique);
  }

  /**
   * 修改体格
   *
   * @param osPhysique 体格
   * @return 结果
   */
  @Override
  public int updateOsPhysique(OsPhysique osPhysique) {
    return osPhysiqueMapper.updateOsPhysique(osPhysique);
  }

  /**
   * 批量删除体格
   *
   * @param physiqueIds 需要删除的体格主键
   * @return 结果
   */
  @Override
  public int deleteOsPhysiqueByPhysiqueIds(Long[] physiqueIds) {
    return osPhysiqueMapper.deleteOsPhysiqueByPhysiqueIds(physiqueIds);
  }

  @Override
  public int deleteOsPhysiqueByPreCode(String preCode) {
    return osPhysiqueMapper.deleteOsPhysiqueByPreCode(preCode);
  }

  /**
   * 删除体格信息
   *
   * @param physiqueId 体格主键
   * @return 结果
   */
  @Override
  public int deleteOsPhysiqueByPhysiqueId(Long physiqueId) {
    return osPhysiqueMapper.deleteOsPhysiqueByPhysiqueId(physiqueId);
  }
}
