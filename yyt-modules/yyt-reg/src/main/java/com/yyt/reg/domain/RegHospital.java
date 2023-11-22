package com.yyt.reg.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 医院对象 reg_hospital
 * 
 * @author yyt
 * @date 2023-11-22
 */
public class RegHospital extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 医院ID */
    private Long hospitalId;

    /** 地点ID */
    @Excel(name = "地点ID")
    private Long addressId;

    /** 医院名称 */
    @Excel(name = "医院名称")
    private String name;

    /** 医院标签 */
    @Excel(name = "医院标签")
    private String tagId;

    /** 医院位置 */
    @Excel(name = "医院位置")
    private String position;

    /** 医院电话 */
    @Excel(name = "医院电话")
    private String telephone;

    public void setHospitalId(Long hospitalId) 
    {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() 
    {
        return hospitalId;
    }
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
    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hospitalId", getHospitalId())
            .append("addressId", getAddressId())
            .append("name", getName())
            .append("tagId", getTagId())
            .append("position", getPosition())
            .append("createBy", getCreateBy())
            .append("telephone", getTelephone())
            .append("remark", getRemark())
            .toString();
    }
}
