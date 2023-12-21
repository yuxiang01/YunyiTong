package com.yyt.os.service.impl;

import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsDiseaseMapper;
import com.yyt.os.domain.OsDisease;
import com.yyt.os.service.IOsDiseaseService;

/**
 * 病症Service业务层处理
 * 
 * @author yyt
 * @date 2023-12-13
 */
@Service
public class OsDiseaseServiceImpl implements IOsDiseaseService 
{
    @Autowired
    private OsDiseaseMapper osDiseaseMapper;

    /**
     * 查询病症
     * 
     * @param disId 病症主键
     * @return 病症
     */
    @Override
    public OsDisease selectOsDiseaseByDisId(Long disId)
    {
        return osDiseaseMapper.selectOsDiseaseByDisId(disId);
    }

    /**
     * 查询病症列表
     * 
     * @param osDisease 病症
     * @return 病症
     */
    @Override
    public List<OsDisease> selectOsDiseaseList(OsDisease osDisease)
    {
        return osDiseaseMapper.selectOsDiseaseList(osDisease);
    }

    /**
     * 新增病症
     * 
     * @param osDisease 病症
     * @return 结果
     */
    @Override
    public int insertOsDisease(OsDisease osDisease)
    {
        osDisease.setCreateTime(DateUtils.getNowDate());
        return osDiseaseMapper.insertOsDisease(osDisease);
    }

    /**
     * 修改病症
     * 
     * @param osDisease 病症
     * @return 结果
     */
    @Override
    public int updateOsDisease(OsDisease osDisease)
    {
        osDisease.setUpdateTime(DateUtils.getNowDate());
        return osDiseaseMapper.updateOsDisease(osDisease);
    }

    /**
     * 批量删除病症
     * 
     * @param disIds 需要删除的病症主键
     * @return 结果
     */
    @Override
    public int deleteOsDiseaseByDisIds(Long[] disIds)
    {
        return osDiseaseMapper.deleteOsDiseaseByDisIds(disIds);
    }

    /**
     * 删除病症信息
     * 
     * @param disId 病症主键
     * @return 结果
     */
    @Override
    public int deleteOsDiseaseByDisId(Long disId)
    {
        return osDiseaseMapper.deleteOsDiseaseByDisId(disId);
    }
}
