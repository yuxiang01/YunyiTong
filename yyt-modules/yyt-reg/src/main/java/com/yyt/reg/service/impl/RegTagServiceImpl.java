package com.yyt.reg.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.reg.mapper.RegTagMapper;
import com.yyt.reg.domain.RegTag;
import com.yyt.reg.service.IRegTagService;

/**
 * 医院标签Service业务层处理
 * 
 * @author yyt
 * @date 2023-11-22
 */
@Service
public class RegTagServiceImpl implements IRegTagService 
{
    @Autowired
    private RegTagMapper regTagMapper;

    /**
     * 查询医院标签
     * 
     * @param tagId 医院标签主键
     * @return 医院标签
     */
    @Override
    public RegTag selectRegTagByTagId(Long tagId)
    {
        return regTagMapper.selectRegTagByTagId(tagId);
    }

    /**
     * 查询医院标签列表
     * 
     * @param regTag 医院标签
     * @return 医院标签
     */
    @Override
    public List<RegTag> selectRegTagList(RegTag regTag)
    {
        return regTagMapper.selectRegTagList(regTag);
    }

    /**
     * 新增医院标签
     * 
     * @param regTag 医院标签
     * @return 结果
     */
    @Override
    public int insertRegTag(RegTag regTag)
    {
        return regTagMapper.insertRegTag(regTag);
    }

    /**
     * 修改医院标签
     * 
     * @param regTag 医院标签
     * @return 结果
     */
    @Override
    public int updateRegTag(RegTag regTag)
    {
        return regTagMapper.updateRegTag(regTag);
    }

    /**
     * 批量删除医院标签
     * 
     * @param tagIds 需要删除的医院标签主键
     * @return 结果
     */
    @Override
    public int deleteRegTagByTagIds(Long[] tagIds)
    {
        return regTagMapper.deleteRegTagByTagIds(tagIds);
    }

    /**
     * 删除医院标签信息
     * 
     * @param tagId 医院标签主键
     * @return 结果
     */
    @Override
    public int deleteRegTagByTagId(Long tagId)
    {
        return regTagMapper.deleteRegTagByTagId(tagId);
    }
}
