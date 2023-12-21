package com.yyt.os.service.impl;

import java.util.List;

import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsCheckItemMapper;
import com.yyt.os.domain.OsCheckItem;
import com.yyt.os.service.IOsCheckItemService;

/**
 * 检查项目Service业务层处理
 *
 * @author yyt
 * @date 2023-12-19
 */
@Service
public class OsCheckItemServiceImpl implements IOsCheckItemService {
  @Autowired
  private OsCheckItemMapper osCheckItemMapper;

  /**
   * 查询检查项目
   *
   * @param checkId 检查项目主键
   * @return 检查项目
   */
  @Override
  public OsCheckItem selectOsCheckItemByCheckId(Long checkId) {
    return osCheckItemMapper.selectOsCheckItemByCheckId(checkId);
  }

  /**
   * 查询检查项目列表
   *
   * @param osCheckItem 检查项目
   * @return 检查项目
   */
  @Override
  public List<OsCheckItem> selectOsCheckItemList(OsCheckItem osCheckItem) {
    return osCheckItemMapper.selectOsCheckItemList(osCheckItem);
  }

  /**
   * 新增检查项目
   *
   * @param osCheckItem 检查项目
   * @return 结果
   */
  @Override
  public int insertOsCheckItem(OsCheckItem osCheckItem) {
    osCheckItem.setCreateTime(DateUtils.getNowDate());
    return osCheckItemMapper.insertOsCheckItem(osCheckItem);
  }

  /**
   * 修改检查项目
   *
   * @param osCheckItem 检查项目
   * @return 结果
   */
  @Override
  public int updateOsCheckItem(OsCheckItem osCheckItem) {
    osCheckItem.setUpdateTime(DateUtils.getNowDate());
    return osCheckItemMapper.updateOsCheckItem(osCheckItem);
  }

  /**
   * 批量删除检查项目
   *
   * @param checkIds 需要删除的检查项目主键
   * @return 结果
   */
  @Override
  public int deleteOsCheckItemByCheckIds(Long[] checkIds) {
    return osCheckItemMapper.deleteOsCheckItemByCheckIds(checkIds);
  }

  /**
   * 删除检查项目信息
   *
   * @param checkId 检查项目主键
   * @return 结果
   */
  @Override
  public int deleteOsCheckItemByCheckId(Long checkId) {
    return osCheckItemMapper.deleteOsCheckItemByCheckId(checkId);
  }
}
