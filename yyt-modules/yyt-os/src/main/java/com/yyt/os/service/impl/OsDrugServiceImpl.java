package com.yyt.os.service.impl;

import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsDrugMapper;
import com.yyt.os.domain.OsDrug;
import com.yyt.os.service.IOsDrugService;

/**
 * 药品Service业务层处理
 * 
 * @author yyt
 * @date 2023-12-05
 */
@Service
public class OsDrugServiceImpl implements IOsDrugService 
{
    @Autowired
    private OsDrugMapper osDrugMapper;

    /**
     * 查询药品
     * 
     * @param drugId 药品主键
     * @return 药品
     */
    @Override
    public OsDrug selectOsDrugByDrugId(Long drugId)
    {
        return osDrugMapper.selectOsDrugByDrugId(drugId);
    }

    /**
     * 查询药品列表
     * 
     * @param osDrug 药品
     * @return 药品
     */
    @Override
    public List<OsDrug> selectOsDrugList(OsDrug osDrug)
    {
        return osDrugMapper.selectOsDrugList(osDrug);
    }

    /**
     * 新增药品
     * 
     * @param osDrug 药品
     * @return 结果
     */
    @Override
    public int insertOsDrug(OsDrug osDrug)
    {
        osDrug.setCreateTime(DateUtils.getNowDate());
        return osDrugMapper.insertOsDrug(osDrug);
    }

    /**
     * 修改药品
     * 
     * @param osDrug 药品
     * @return 结果
     */
    @Override
    public int updateOsDrug(OsDrug osDrug)
    {
        osDrug.setUpdateTime(DateUtils.getNowDate());
        return osDrugMapper.updateOsDrug(osDrug);
    }

    /**
     * 批量删除药品
     * 
     * @param drugIds 需要删除的药品主键
     * @return 结果
     */
    @Override
    public int deleteOsDrugByDrugIds(Long[] drugIds)
    {
        return osDrugMapper.deleteOsDrugByDrugIds(drugIds);
    }

    /**
     * 删除药品信息
     * 
     * @param drugId 药品主键
     * @return 结果
     */
    @Override
    public int deleteOsDrugByDrugId(Long drugId)
    {
        return osDrugMapper.deleteOsDrugByDrugId(drugId);
    }
}
