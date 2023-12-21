package com.yyt.os.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 病症对象 os_disease
 * 
 * @author yyt
 * @date 2023-12-13
 */
public class OsDisease extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 病症编号 */
    private Long disId;

    /** 病症名称 */
    @Excel(name = "病症名称")
    private String name;

    /** 上级病症 */
    @Excel(name = "上级病症")
    private Long superDisId;

    public void setDisId(Long disId) 
    {
        this.disId = disId;
    }

    public Long getDisId() 
    {
        return disId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSuperDisId(Long superDisId) 
    {
        this.superDisId = superDisId;
    }

    public Long getSuperDisId() 
    {
        return superDisId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("disId", getDisId())
            .append("name", getName())
            .append("superDisId", getSuperDisId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
