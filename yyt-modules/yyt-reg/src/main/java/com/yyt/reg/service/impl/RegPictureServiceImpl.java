package com.yyt.reg.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.reg.mapper.RegPictureMapper;
import com.yyt.reg.domain.RegPicture;
import com.yyt.reg.service.IRegPictureService;

/**
 * 图片Service业务层处理
 * 
 * @author yyt
 * @date 2023-11-22
 */
@Service
public class RegPictureServiceImpl implements IRegPictureService 
{
    @Autowired
    private RegPictureMapper regPictureMapper;

    /**
     * 查询图片
     * 
     * @param pictureId 图片主键
     * @return 图片
     */
    @Override
    public RegPicture selectRegPictureByPictureId(Long pictureId)
    {
        return regPictureMapper.selectRegPictureByPictureId(pictureId);
    }

    /**
     * 查询图片列表
     * 
     * @param regPicture 图片
     * @return 图片
     */
    @Override
    public List<RegPicture> selectRegPictureList(RegPicture regPicture)
    {
        return regPictureMapper.selectRegPictureList(regPicture);
    }

    /**
     * 新增图片
     * 
     * @param regPicture 图片
     * @return 结果
     */
    @Override
    public int insertRegPicture(RegPicture regPicture)
    {
        return regPictureMapper.insertRegPicture(regPicture);
    }

    /**
     * 修改图片
     * 
     * @param regPicture 图片
     * @return 结果
     */
    @Override
    public int updateRegPicture(RegPicture regPicture)
    {
        return regPictureMapper.updateRegPicture(regPicture);
    }

    /**
     * 批量删除图片
     * 
     * @param pictureIds 需要删除的图片主键
     * @return 结果
     */
    @Override
    public int deleteRegPictureByPictureIds(Long[] pictureIds)
    {
        return regPictureMapper.deleteRegPictureByPictureIds(pictureIds);
    }

    /**
     * 删除图片信息
     * 
     * @param pictureId 图片主键
     * @return 结果
     */
    @Override
    public int deleteRegPictureByPictureId(Long pictureId)
    {
        return regPictureMapper.deleteRegPictureByPictureId(pictureId);
    }
}
