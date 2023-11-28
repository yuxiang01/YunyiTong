package com.yyt.reg.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 图片对象 reg_picture
 * 
 * @author yyt
 * @date 2023-11-22
 */
public class RegPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片编号 */
    private Long pictureId;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String path;

    /** 图片所属 */
    private String id;

    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pictureId", getPictureId())
            .append("path", getPath())
            .append("id", getId())
            .toString();
    }
}
