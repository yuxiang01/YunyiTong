package com.yyt.os.service.impl;

import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.yyt.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.yyt.os.domain.OsPrescription;
import com.yyt.os.mapper.OsPreOrderMapper;
import com.yyt.os.domain.OsPreOrder;
import com.yyt.os.service.IOsPreOrderService;

/**
 * 处方订单Service业务层处理
 * 
 * @author yyt
 * @date 2023-12-18
 */
@Service
public class OsPreOrderServiceImpl implements IOsPreOrderService 
{
    @Autowired
    private OsPreOrderMapper osPreOrderMapper;

    /**
     * 查询处方订单
     * 
     * @param preCode 处方订单主键
     * @return 处方订单
     */
    @Override
    public OsPreOrder selectOsPreOrderByPreCode(String preCode)
    {
        return osPreOrderMapper.selectOsPreOrderByPreCode(preCode);
    }

    /**
     * 查询处方订单列表
     * 
     * @param osPreOrder 处方订单
     * @return 处方订单
     */
    @Override
    public List<OsPreOrder> selectOsPreOrderList(OsPreOrder osPreOrder)
    {
        return osPreOrderMapper.selectOsPreOrderList(osPreOrder);
    }

    /**
     * 新增处方订单
     * 
     * @param osPreOrder 处方订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOsPreOrder(OsPreOrder osPreOrder)
    {
        osPreOrder.setCreateTime(DateUtils.getNowDate());
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
    public int updateOsPreOrder(OsPreOrder osPreOrder)
    {
        osPreOrder.setUpdateTime(DateUtils.getNowDate());
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
    public int deleteOsPreOrderByPreCodes(String[] preCodes)
    {
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
    public int deleteOsPreOrderByPreCode(String preCode)
    {
        osPreOrderMapper.deleteOsPrescriptionByPreCode(preCode);
        return osPreOrderMapper.deleteOsPreOrderByPreCode(preCode);
    }

    /**
     * 新增处方信息
     * 
     * @param osPreOrder 处方订单对象
     */
    public void insertOsPrescription(OsPreOrder osPreOrder)
    {
        List<OsPrescription> osPrescriptionList = osPreOrder.getOsPrescriptionList();
        String preCode = osPreOrder.getPreCode();
        if (StringUtils.isNotNull(osPrescriptionList))
        {
            List<OsPrescription> list = new ArrayList<OsPrescription>();
            for (OsPrescription osPrescription : osPrescriptionList)
            {
                osPrescription.setPreCode(preCode);
                list.add(osPrescription);
            }
            if (list.size() > 0)
            {
                osPreOrderMapper.batchOsPrescription(list);
            }
        }
    }
}
