package com.yyt.os.service;

import java.util.List;

import com.yyt.os.domain.OsPreOrder;

/**
 * 处方订单Service接口
 *
 * @author yyt
 * @date 2023-12-21
 */
public interface IOsPreOrderService {
  /**
   * 查询处方订单
   *
   * @param preCode 处方订单主键
   * @return 处方订单
   */
  public OsPreOrder selectOsPreOrderByPreCode(String preCode);

  /**
   * 查询处方订单列表
   *
   * @param osPreOrder 处方订单
   * @return 处方订单集合
   */
  public List<OsPreOrder> selectOsPreOrderList(OsPreOrder osPreOrder);

  /**
   * 新增处方订单
   *
   * @param osPreOrder 处方订单
   * @return 结果
   */
  public int insertOsPreOrder(OsPreOrder osPreOrder);

  /**
   * 修改处方订单
   *
   * @param osPreOrder 处方订单
   * @return 结果
   */
  public int updateOsPreOrder(OsPreOrder osPreOrder);

  /**
   * 批量删除处方订单
   *
   * @param preCodes 需要删除的处方订单主键集合
   * @return 结果
   */
  public int deleteOsPreOrderByPreCodes(String[] preCodes);

  /**
   * 删除处方订单信息
   *
   * @param preCode 处方订单主键
   * @return 结果
   */
  public int deleteOsPreOrderByPreCode(String preCode);

  OsPreOrder selectOsPreOrderByRegId(String regId);

  int saveCaseHistoryInfo(OsPreOrder info);

  int finishSaveInfo(OsPreOrder osPreOrder);

  int payPre(OsPreOrder osPreOrder);
}
