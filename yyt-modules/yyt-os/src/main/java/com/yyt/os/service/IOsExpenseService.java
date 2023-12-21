package com.yyt.os.service;

import java.util.List;
import com.yyt.os.domain.OsExpense;

/**
 * 费用Service接口
 * 
 * @author yyt
 * @date 2023-12-20
 */
public interface IOsExpenseService 
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
     * 批量删除费用
     * 
     * @param expenseIds 需要删除的费用主键集合
     * @return 结果
     */
    public int deleteOsExpenseByExpenseIds(Long[] expenseIds);

    /**
     * 删除费用信息
     * 
     * @param expenseId 费用主键
     * @return 结果
     */
    public int deleteOsExpenseByExpenseId(Long expenseId);
}
