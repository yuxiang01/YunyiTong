package com.yyt.os.mapper;

import java.util.List;
import com.yyt.os.domain.OsCheckItem;

/**
 * 检查项目Mapper接口
 * 
 * @author yyt
 * @date 2023-12-19
 */
public interface OsCheckItemMapper 
{
    /**
     * 查询检查项目
     * 
     * @param checkId 检查项目主键
     * @return 检查项目
     */
    public OsCheckItem selectOsCheckItemByCheckId(Long checkId);

    /**
     * 查询检查项目列表
     * 
     * @param osCheckItem 检查项目
     * @return 检查项目集合
     */
    public List<OsCheckItem> selectOsCheckItemList(OsCheckItem osCheckItem);

    /**
     * 新增检查项目
     * 
     * @param osCheckItem 检查项目
     * @return 结果
     */
    public int insertOsCheckItem(OsCheckItem osCheckItem);

    /**
     * 修改检查项目
     * 
     * @param osCheckItem 检查项目
     * @return 结果
     */
    public int updateOsCheckItem(OsCheckItem osCheckItem);

    /**
     * 删除检查项目
     * 
     * @param checkId 检查项目主键
     * @return 结果
     */
    public int deleteOsCheckItemByCheckId(Long checkId);

    /**
     * 批量删除检查项目
     * 
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsCheckItemByCheckIds(Long[] checkIds);
}
