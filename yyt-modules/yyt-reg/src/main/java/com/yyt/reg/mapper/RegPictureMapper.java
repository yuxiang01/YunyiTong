package com.yyt.reg.mapper;

import java.util.List;
import com.yyt.reg.domain.RegPicture;

/**
 * 图片Mapper接口
 * 
 * @author yyt
 * @date 2023-11-22
 */
public interface RegPictureMapper 
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
     * 删除图片
     * 
     * @param pictureId 图片主键
     * @return 结果
     */
    public int deleteRegPictureByPictureId(Long pictureId);

    /**
     * 批量删除图片
     * 
     * @param pictureIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegPictureByPictureIds(Long[] pictureIds);

    /**
     * 查询图片路径
     *
     * @param id
     * @return 图片
     */
    public String selAll(String id);
}
