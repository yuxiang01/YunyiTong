package com.yyt.os.service.impl;

import java.util.List;
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
public class OsRegorderServiceImpl implements IOsRegorderService 
{
    @Autowired
    private OsRegorderMapper osRegorderMapper;

    /**
     * 查询挂号订单
     * 
     * @param regId 挂号订单主键
     * @return 挂号订单
     */
    @Override
    public OsRegorder selectOsRegorderByRegId(String regId)
    {
        return osRegorderMapper.selectOsRegorderByRegId(regId);
    }

    /**
     * 查询挂号订单列表
     * 
     * @param osRegorder 挂号订单
     * @return 挂号订单
     */
    @Override
    public List<OsRegorder> selectOsRegorderList(OsRegorder osRegorder)
    {
        return osRegorderMapper.selectOsRegorderList(osRegorder);
    }

    /**
     * 新增挂号订单
     * 
     * @param osRegorder 挂号订单
     * @return 结果
     */
    @Override
    public int insertOsRegorder(OsRegorder osRegorder)
    {
        return osRegorderMapper.insertOsRegorder(osRegorder);
    }

    /**
     * 修改挂号订单
     * 
     * @param osRegorder 挂号订单
     * @return 结果
     */
    @Override
    public int updateOsRegorder(OsRegorder osRegorder)
    {
        return osRegorderMapper.updateOsRegorder(osRegorder);
    }

    /**
     * 批量删除挂号订单
     * 
     * @param regIds 需要删除的挂号订单主键
     * @return 结果
     */
    @Override
    public int deleteOsRegorderByRegIds(String[] regIds)
    {
        return osRegorderMapper.deleteOsRegorderByRegIds(regIds);
    }

    /**
     * 删除挂号订单信息
     * 
     * @param regId 挂号订单主键
     * @return 结果
     */
    @Override
    public int deleteOsRegorderByRegId(String regId)
    {
        return osRegorderMapper.deleteOsRegorderByRegId(regId);
    }
}
