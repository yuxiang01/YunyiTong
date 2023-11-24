package com.yyt.os.service;

import java.util.List;
import com.yyt.os.domain.OsRegorder;

/**
 * 挂号订单Service接口
 * 
 * @author yyt
 * @date 2023-11-24
 */
public interface IOsRegorderService 
{
    /**
     * 查询挂号订单
     * 
     * @param regId 挂号订单主键
     * @return 挂号订单
     */
    public OsRegorder selectOsRegorderByRegId(String regId);

    /**
     * 查询挂号订单列表
     * 
     * @param osRegorder 挂号订单
     * @return 挂号订单集合
     */
    public List<OsRegorder> selectOsRegorderList(OsRegorder osRegorder);

    /**
     * 新增挂号订单
     * 
     * @param osRegorder 挂号订单
     * @return 结果
     */
    public int insertOsRegorder(OsRegorder osRegorder);

    /**
     * 修改挂号订单
     * 
     * @param osRegorder 挂号订单
     * @return 结果
     */
    public int updateOsRegorder(OsRegorder osRegorder);

    /**
     * 批量删除挂号订单
     * 
     * @param regIds 需要删除的挂号订单主键集合
     * @return 结果
     */
    public int deleteOsRegorderByRegIds(String[] regIds);

    /**
     * 删除挂号订单信息
     * 
     * @param regId 挂号订单主键
     * @return 结果
     */
    public int deleteOsRegorderByRegId(String regId);
}
