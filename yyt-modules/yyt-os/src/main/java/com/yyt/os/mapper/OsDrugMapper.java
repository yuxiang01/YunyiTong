package com.yyt.os.mapper;

import java.util.List;
import com.yyt.os.domain.OsDrug;

/**
 * 药品Mapper接口
 * 
 * @author yyt
 * @date 2023-12-05
 */
public interface OsDrugMapper 
{
    /**
     * 查询药品
     * 
     * @param drugId 药品主键
     * @return 药品
     */
    public OsDrug selectOsDrugByDrugId(Long drugId);

    /**
     * 查询药品列表
     * 
     * @param osDrug 药品
     * @return 药品集合
     */
    public List<OsDrug> selectOsDrugList(OsDrug osDrug);

    /**
     * 新增药品
     * 
     * @param osDrug 药品
     * @return 结果
     */
    public int insertOsDrug(OsDrug osDrug);

    /**
     * 修改药品
     * 
     * @param osDrug 药品
     * @return 结果
     */
    public int updateOsDrug(OsDrug osDrug);

    /**
     * 删除药品
     * 
     * @param drugId 药品主键
     * @return 结果
     */
    public int deleteOsDrugByDrugId(Long drugId);

    /**
     * 批量删除药品
     * 
     * @param drugIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsDrugByDrugIds(Long[] drugIds);
}
