package com.yyt.reg.service;

import java.util.List;
import com.yyt.reg.domain.RegPicture;

/**
 * 图片Service接口
 * 
 * @author yyt
 * @date 2023-11-22
 */
public interface IRegPictureService 
{
    /**
     * 查询图片
     * 
     * @param pictureId 图片主键
     * @return 图片
     */
    public RegPicture selectRegPictureByPictureId(Long pictureId);

    /**
     * 查询图片列表
     * 
     * @param regPicture 图片
     * @return 图片集合
     */
    public List<RegPicture> selectRegPictureList(RegPicture regPicture);

    /**
     * 新增图片
     * 
     * @param regPicture 图片
     * @return 结果
     */
    public int insertRegPicture(RegPicture regPicture);

    /**
     * 修改图片
     * 
     * @param regPicture 图片
     * @return 结果
     */
    public int updateRegPicture(RegPicture regPicture);

    /**
     * 批量删除图片
     * 
     * @param pictureIds 需要删除的图片主键集合
     * @return 结果
     */
    public int deleteRegPictureByPictureIds(Long[] pictureIds);

    /**
     * 删除图片信息
     * 
     * @param pictureId 图片主键
     * @return 结果
     */
    public int deleteRegPictureByPictureId(Long pictureId);
}
