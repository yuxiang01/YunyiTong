package com.yyt.os.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.yyt.common.core.annotation.Excel;
import com.yyt.common.core.web.domain.BaseEntity;

/**
 * 费用对象 os_expense
 * 
 * @author yyt
 * @date 2023-12-20
 */
public class OsExpense extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 费用编号 */
    private Long expenseId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal price;

    /** 费用状态 */
    @Excel(name = "费用状态")
    private Long status;

    public void setExpenseId(Long expenseId) 
    {
        this.expenseId = expenseId;
    }

    public Long getExpenseId() 
    {
        return expenseId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("expenseId", getExpenseId())
            .append("name", getName())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
