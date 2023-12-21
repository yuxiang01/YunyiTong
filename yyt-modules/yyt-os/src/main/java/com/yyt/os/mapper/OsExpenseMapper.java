package com.yyt.os.mapper;

import java.util.List;
import com.yyt.os.domain.OsExpense;

/**
 * 费用Mapper接口
 * 
 * @author yyt
 * @date 2023-12-20
 */
public interface OsExpenseMapper 
{
    /**
     * 查询费用
     * 
     * @param expenseId 费用主键
     * @return 费用
     */
    public OsExpense selectOsExpenseByExpenseId(Long expenseId);

    /**
     * 查询费用列表
     * 
     * @param osExpense 费用
     * @return 费用集合
     */
    public List<OsExpense> selectOsExpenseList(OsExpense osExpense);

    /**
     * 新增费用
     * 
     * @param osExpense 费用
     * @return 结果
     */
    public int insertOsExpense(OsExpense osExpense);

    /**
     * 修改费用
     * 
     * @param osExpense 费用
     * @return 结果
     */
    public int updateOsExpense(OsExpense osExpense);

    /**
     * 删除费用
     * 
     * @param expenseId 费用主键
     * @return 结果
     */
    public int deleteOsExpenseByExpenseId(Long expenseId);

    /**
     * 批量删除费用
     * 
     * @param expenseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsExpenseByExpenseIds(Long[] expenseIds);
}
