package com.yyt.os.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yyt.common.core.utils.DateUtils;
import com.yyt.os.domain.*;
import com.yyt.os.service.IOsCaseHistoryService;
import com.yyt.os.service.IOsPhysiqueService;
import com.yyt.os.service.IOsRegorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.yyt.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.yyt.os.mapper.OsPreOrderMapper;
import com.yyt.os.service.IOsPreOrderService;

/**
 * 处方订单Service业务层处理
 *
 * @author yyt
 * @date 2023-12-21
 */
@Service
public class OsPreOrderServiceImpl implements IOsPreOrderService {
  @Autowired
  private OsPreOrderMapper osPreOrderMapper;
  @Autowired
  private IOsCaseHistoryService caseHistoryService;
  @Autowired
  private IOsPhysiqueService physiqueService;
  @Autowired
  private IOsRegorderService regorderService;

  /**
   * 查询处方订单
   *
   * @param preCode 处方订单主键
   * @return 处方订单
   */
  @Override
  public OsPreOrder selectOsPreOrderByPreCode(String preCode) {
    return osPreOrderMapper.selectOsPreOrderByPreCode(preCode);
  }

  // 根据订单编号查询
  @Override
  public OsPreOrder selectOsPreOrderByRegId(String regId) {
    return osPreOrderMapper.selectOsPreOrderByRegId(regId);
  }

  // 新增门诊（病历信息）
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveCaseHistoryInfo(OsPreOrder info) {
    // 判断是否存在门诊订单
    if (StrUtil.isBlank(info.getPreCode())) {
      setPreOrderCode(info);
      info.setCreateTime(DateUtils.getNowDate());
      if (osPreOrderMapper.insertOsPreOrder(info) < 0) {
        throw new RuntimeException("保存信息失败");
      }
    }
    // 赋值
    updateCaseInfo(info);
    return 1;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int finishSaveInfo(OsPreOrder osPreOrder) {
    // 判断是否存在门诊订单
    if (StrUtil.isBlank(osPreOrder.getPreCode())) {
      // 规则生成 pre_code
      setPreOrderCode(osPreOrder);
      if (insertOsPreOrder(osPreOrder) < 0) throw new RuntimeException("信息保存失败");
      // 保存病历填写
      updateCaseInfo(osPreOrder);
    } else {
      // 更新门诊订单：处方详情
      if (updateOsPreOrder(osPreOrder) < 0) throw new RuntimeException("信息保存失败");
      // 保存病历填写
      updateCaseInfo(osPreOrder);
    }
    return 1;
  }

  @Override
  public int payPre(OsPreOrder osPreOrder) {
    osPreOrder.setPayTime(DateUtils.getNowDate());
    osPreOrder.setUpdateTime(DateUtils.getNowDate());
    return osPreOrderMapper.updateOsPreOrder(osPreOrder);
  }

  /**
   * 查询处方订单列表
   *
   * @param osPreOrder 处方订单
   * @return 处方订单
   */
  @Override
  public List<OsPreOrder> selectOsPreOrderList(OsPreOrder osPreOrder) {
    List<OsPreOrder> orderList = osPreOrderMapper.selectOsPreOrderList(osPreOrder);
    orderList.forEach(item -> {
      item.setRegorder(regorderService.selectOsRegorderByRegId(item.getRegisterId()));
    });
    return orderList;
  }

  /**
   * 新增处方订单
   *
   * @param osPreOrder 处方订单
   * @return 结果
   */
  @Transactional
  @Override
  public int insertOsPreOrder(OsPreOrder osPreOrder) {
    osPreOrder.setCreateTime(DateUtils.getNowDate());
    if (StrUtil.isBlank(osPreOrder.getPreCode())) setPreOrderCode(osPreOrder);
    osPreOrder.setMoney(calculate(osPreOrder.getOsPrescriptionList()));
    int rows = osPreOrderMapper.insertOsPreOrder(osPreOrder);
    insertOsPrescription(osPreOrder);
    return rows;
  }

  /**
   * 修改处方订单
   *
   * @param osPreOrder 处方订单
   * @return 结果
   */
  @Transactional
  @Override
  public int updateOsPreOrder(OsPreOrder osPreOrder) {
    osPreOrder.setUpdateTime(DateUtils.getNowDate());
    osPreOrder.setMoney(calculate(osPreOrder.getOsPrescriptionList()));
    osPreOrderMapper.deleteOsPrescriptionByPreCode(osPreOrder.getPreCode());
    insertOsPrescription(osPreOrder);
    return osPreOrderMapper.updateOsPreOrder(osPreOrder);
  }

  /**
   * 批量删除处方订单
   *
   * @param preCodes 需要删除的处方订单主键
   * @return 结果
   */
  @Transactional
  @Override
  public int deleteOsPreOrderByPreCodes(String[] preCodes) {
    osPreOrderMapper.deleteOsPrescriptionByPreCodes(preCodes);
    return osPreOrderMapper.deleteOsPreOrderByPreCodes(preCodes);
  }

  /**
   * 删除处方订单信息
   *
   * @param preCode 处方订单主键
   * @return 结果
   */
  @Transactional
  @Override
  public int deleteOsPreOrderByPreCode(String preCode) {
    osPreOrderMapper.deleteOsPrescriptionByPreCode(preCode);
    return osPreOrderMapper.deleteOsPreOrderByPreCode(preCode);
  }


  /**
   * 新增处方信息
   *
   * @param osPreOrder 处方订单对象
   */
  public void insertOsPrescription(OsPreOrder osPreOrder) {
    List<OsPrescription> osPrescriptionList = osPreOrder.getOsPrescriptionList();
    String preCode = osPreOrder.getPreCode();
    if (StringUtils.isNotNull(osPrescriptionList)) {
      List<OsPrescription> list = new ArrayList<OsPrescription>();
      for (OsPrescription osPrescription : osPrescriptionList) {
        osPrescription.setPreCode(preCode);
        list.add(osPrescription);
      }
      if (list.size() > 0) {
        osPreOrderMapper.batchOsPrescription(list);
      }
    }
  }

  // 规则生成 pre_code
  private synchronized void setPreOrderCode(OsPreOrder osPreOrder) {
    String orderId = osPreOrderMapper.lastRowPreOrderId();
    String prefix = "cs" + DateUtil.format(new Date(), "yyyyMMddHHmm");
    if (StrUtil.isNotEmpty(orderId)) {
      int sub = Integer.parseInt(StrUtil.sub(orderId, 14, 18));
      DecimalFormat format = new DecimalFormat("0000");
      String string = format.format(sub + 1);
      osPreOrder.setPreCode(prefix + string);
    } else {
      osPreOrder.setPreCode(prefix + "0001");
    }
  }

  // 重复代码：新增病历填写
  private void updateCaseInfo(OsPreOrder osPreOrder) {
    physiqueService.deleteOsPhysiqueByPreCode(osPreOrder.getPreCode());
    caseHistoryService.deleteOsCaseHistoryByPreCode(osPreOrder.getPreCode());
    OsPhysique physique = osPreOrder.getPhysique();
    physique.setPreCode(osPreOrder.getPreCode());
    OsCaseHistory caseHistory = osPreOrder.getCaseHistory();
    caseHistory.setPreCode(osPreOrder.getPreCode());
    if (physiqueService.insertOsPhysique(physique) < 0) throw new RuntimeException("保存体格信息失败");
    if (caseHistoryService.insertOsCaseHistory(caseHistory) < 0) throw new RuntimeException("保存病历信息失败");
  }

  // 计算处方详情总价格
  private BigDecimal calculate(List<OsPrescription> prescriptionList) {
    BigDecimal total = BigDecimal.ZERO;
    for (OsPrescription item : prescriptionList) {
      if (item.getType() == 0 || item.getType() == 1) {
        total = total.add(new BigDecimal(item.getTotal()).multiply(item.getUnitPrice()));
      } else {
        total = total.add(new BigDecimal(item.getDosage()).multiply(item.getUnitPrice()));
      }
    }
    return total;
  }
}
