package com.yyt.reg.service;

import java.util.List;
import com.yyt.reg.domain.RegTag;

/**
 * 医院标签Service接口
 * 
 * @author yyt
 * @date 2023-11-22
 */
public interface IRegTagService 
{
    /**
     * 查询医院标签
     * 
     * @param tagId 医院标签主键
     * @return 医院标签
     */
    public RegTag selectRegTagByTagId(Long tagId);

    /**
     * 查询医院标签列表
     * 
     * @param regTag 医院标签
     * @return 医院标签集合
     */
    public List<RegTag> selectRegTagList(RegTag regTag);

    /**
     * 新增医院标签
     * 
     * @param regTag 医院标签
     * @return 结果
     */
    public int insertRegTag(RegTag regTag);

    /**
     * 修改医院标签
     * 
     * @param regTag 医院标签
     * @return 结果
     */
    public int updateRegTag(RegTag regTag);

    /**
     * 批量删除医院标签
     * 
     * @param tagIds 需要删除的医院标签主键集合
     * @return 结果
     */
    public int deleteRegTagByTagIds(Long[] tagIds);

    /**
     * 删除医院标签信息
     * 
     * @param tagId 医院标签主键
     * @return 结果
     */
    public int deleteRegTagByTagId(Long tagId);
}
