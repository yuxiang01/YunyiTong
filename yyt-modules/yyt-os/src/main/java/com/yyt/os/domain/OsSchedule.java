package com.yyt.os.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 排班对象 os_schedule
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class OsSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排班编号 */
    private Long schedId;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 上午 */
    @Excel(name = "上午")
    private Long morning;

    /** 下午 */
    @Excel(name = "下午")
    private Long afternoon;

    /** 挂号数 */
    @Excel(name = "挂号数")
    private Long regNumber;

    /** 医生编号 */
    @Excel(name = "医生编号")
    private Long doctorId;

    /** 已挂号数 */
    @Excel(name = "已挂号数")
    private Long nowNumber;

    public void setSchedId(Long schedId) 
    {
        this.schedId = schedId;
    }

    public Long getSchedId() 
    {
        return schedId;
    }
    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setMorning(Long morning) 
    {
        this.morning = morning;
    }

    public Long getMorning() 
    {
        return morning;
    }
    public void setAfternoon(Long afternoon) 
    {
        this.afternoon = afternoon;
    }

    public Long getAfternoon() 
    {
        return afternoon;
    }
    public void setRegNumber(Long regNumber) 
    {
        this.regNumber = regNumber;
    }

    public Long getRegNumber() 
    {
        return regNumber;
    }
    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId() 
    {
        return doctorId;
    }
    public void setNowNumber(Long nowNumber) 
    {
        this.nowNumber = nowNumber;
    }

    public Long getNowNumber() 
    {
        return nowNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("schedId", getSchedId())
            .append("date", getDate())
            .append("morning", getMorning())
            .append("afternoon", getAfternoon())
            .append("regNumber", getRegNumber())
            .append("doctorId", getDoctorId())
            .append("nowNumber", getNowNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
