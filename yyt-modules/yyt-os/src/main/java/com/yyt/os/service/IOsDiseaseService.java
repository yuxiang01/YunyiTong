package com.yyt.os.service;

import java.util.List;
import com.yyt.os.domain.OsDisease;

/**
 * 病症Service接口
 * 
 * @author yyt
 * @date 2023-12-13
 */
public interface IOsDiseaseService 
{
    /**
     * 查询病症
     * 
     * @param disId 病症主键
     * @return 病症
     */
    public OsDisease selectOsDiseaseByDisId(Long disId);

    /**
     * 查询病症列表
     * 
     * @param osDisease 病症
     * @return 病症集合
     */
    public List<OsDisease> selectOsDiseaseList(OsDisease osDisease);

    /**
     * 新增病症
     * 
     * @param osDisease 病症
     * @return 结果
     */
    public int insertOsDisease(OsDisease osDisease);

    /**
     * 修改病症
     * 
     * @param osDisease 病症
     * @return 结果
     */
    public int updateOsDisease(OsDisease osDisease);

    /**
     * 批量删除病症
     * 
     * @param disIds 需要删除的病症主键集合
     * @return 结果
     */
    public int deleteOsDiseaseByDisIds(Long[] disIds);

    /**
     * 删除病症信息
     * 
     * @param disId 病症主键
     * @return 结果
     */
    public int deleteOsDiseaseByDisId(Long disId);
}
