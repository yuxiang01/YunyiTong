package com.yyt.reg.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 地点对象 sys_address
 * 
 * @author yyt
 * @date 2023-11-22
 */
public class SysAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地点表 */
    private Long addressId;

    /** 地点ID */
    @Excel(name = "地点ID")
    private String name;

    /** 地点所属 */
    @Excel(name = "地点所属")
    private Long superId;

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSuperId(Long superId) 
    {
        this.superId = superId;
    }

    public Long getSuperId() 
    {
        return superId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("addressId", getAddressId())
            .append("name", getName())
            .append("superId", getSuperId())
            .toString();
    }
}
