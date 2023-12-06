package com.yyt.os.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 患者对象 sys_patient
 * 
 * @author yyt
 * @date 2023-11-30
 */
public class SysPatient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 患者编号 */
    @Excel(name = "患者编号")
    private Long patientId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String name;

    /** 患者性别 */
    @Excel(name = "患者性别")
    private String sex;

    /** 患者年龄 */
    @Excel(name = "患者年龄")
    private Long age;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 证件号码 */
    private String card;

    /** 地址 */
    private String address;

    /** 详细地址 */
    private String detailsAddress;

    /** 属于用户 */
    private Long belongToUser;

    public void setPatientId(Long patientId) 
    {
        this.patientId = patientId;
    }

    public Long getPatientId() 
    {
        return patientId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setCard(String card) 
    {
        this.card = card;
    }

    public String getCard() 
    {
        return card;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setDetailsAddress(String detailsAddress) 
    {
        this.detailsAddress = detailsAddress;
    }

    public String getDetailsAddress() 
    {
        return detailsAddress;
    }
    public void setBelongToUser(Long belongToUser) 
    {
        this.belongToUser = belongToUser;
    }

    public Long getBelongToUser() 
    {
        return belongToUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("patientId", getPatientId())
            .append("name", getName())
            .append("sex", getSex())
            .append("age", getAge())
            .append("phone", getPhone())
            .append("card", getCard())
            .append("address", getAddress())
            .append("detailsAddress", getDetailsAddress())
            .append("remark", getRemark())
            .append("belongToUser", getBelongToUser())
            .toString();
    }
}
