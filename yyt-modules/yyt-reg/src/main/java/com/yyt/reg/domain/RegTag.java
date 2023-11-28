package com.yyt.reg.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 医院标签对象 reg_tag
 * 
 * @author yyt
 * @date 2023-11-22
 */
public class RegTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签ID */
    private Long tagId;

    /** 标签标题 */
    @Excel(name = "标签标题")
    private String title;

    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagId", getTagId())
            .append("title", getTitle())
            .toString();
    }
}
