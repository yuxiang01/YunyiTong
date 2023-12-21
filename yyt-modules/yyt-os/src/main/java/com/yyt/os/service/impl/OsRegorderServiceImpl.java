package com.yyt.os.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mysql.cj.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsRegorderMapper;
import com.yyt.os.domain.OsRegorder;
import com.yyt.os.service.IOsRegorderService;

/**
 * 挂号订单Service业务层处理
 *
 * @author yyt
 * @date 2023-11-24
 */
@Service
public class OsRegorderServiceImpl implements IOsRegorderService {
  @Autowired
  private OsRegorderMapper osRegorderMapper;

  /**
   * 查询挂号订单
   *
   * @param regId 挂号订单主键
   * @return 挂号订单
   */
  @Override
  public OsRegorder selectOsRegorderByRegId(String regId) {
    return osRegorderMapper.selectOsRegorderByRegId(regId);
  }

  /**
   * 查询挂号订单列表
   *
   * @param osRegorder 挂号订单
   * @return 挂号订单
   */
  @Override
  public List<OsRegorder> selectOsRegorderList(OsRegorder osRegorder) {
    Date[] queryTime = osRegorder.getQueryTime();
    if (queryTime != null && queryTime.length == 2) {
      osRegorder.setStartTime(queryTime[0]);
      osRegorder.setEndTime(queryTime[1]);
    }
    return osRegorderMapper.selectOsRegorderList(osRegorder);
  }

  /**
   * 新增挂号订单
   *
   * @param osRegorder 挂号订单
   * @return 结果
   */
  @Override
  public int insertOsRegorder(OsRegorder osRegorder) {
    String orderId = osRegorderMapper.lastRowOrderId();
    String prefix = "os" + DateUtil.format(new Date(), "yyyyMMddHHmm");
    if (StrUtil.isNotEmpty(orderId)) {
      int sub = Integer.parseInt(StrUtil.sub(orderId, 14, 18));
      DecimalFormat format = new DecimalFormat("0000");
      String string = format.format(sub + 1);
      osRegorder.setRegId(prefix + string);
    } else {
      osRegorder.setRegId(prefix + "0001");
    }
    osRegorder.setCreateTime(new Date());
    return osRegorderMapper.insertOsRegorder(osRegorder);
  }

  /**
   * 修改挂号订单
   *
   * @param osRegorder 挂号订单
   * @return 结果
   */
  @Override
  public int updateOsRegorder(OsRegorder osRegorder) {
    osRegorder.setUpdateTime(new Date());
    return osRegorderMapper.updateOsRegorder(osRegorder);
  }

  /**
   * 批量删除挂号订单
   *
   * @param regIds 需要删除的挂号订单主键
   * @return 结果
   */
  @Override
  public int deleteOsRegorderByRegIds(String[] regIds) {
    return osRegorderMapper.deleteOsRegorderByRegIds(regIds);
  }

  /**
   * 删除挂号订单信息
   *
   * @param regId 挂号订单主键
   * @return 结果
   */
  @Override
  public int deleteOsRegorderByRegId(String regId) {
    return osRegorderMapper.deleteOsRegorderByRegId(regId);
  }
}
