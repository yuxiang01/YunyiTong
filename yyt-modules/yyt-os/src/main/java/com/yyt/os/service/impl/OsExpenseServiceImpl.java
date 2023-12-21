package com.yyt.os.service.impl;

import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsExpenseMapper;
import com.yyt.os.domain.OsExpense;
import com.yyt.os.service.IOsExpenseService;

/**
 * 费用Service业务层处理
 * 
 * @author yyt
 * @date 2023-12-20
 */
@Service
public class OsExpenseServiceImpl implements IOsExpenseService 
{
    @Autowired
    private OsExpenseMapper osExpenseMapper;

    /**
     * 查询费用
     * 
     * @param expenseId 费用主键
     * @return 费用
     */
    @Override
    public OsExpense selectOsExpenseByExpenseId(Long expenseId)
    {
        return osExpenseMapper.selectOsExpenseByExpenseId(expenseId);
    }

    /**
     * 查询费用列表
     * 
     * @param osExpense 费用
     * @return 费用
     */
    @Override
    public List<OsExpense> selectOsExpenseList(OsExpense osExpense)
    {
        return osExpenseMapper.selectOsExpenseList(osExpense);
    }

    /**
     * 新增费用
     * 
     * @param osExpense 费用
     * @return 结果
     */
    @Override
    public int insertOsExpense(OsExpense osExpense)
    {
        osExpense.setCreateTime(DateUtils.getNowDate());
        return osExpenseMapper.insertOsExpense(osExpense);
    }

    /**
     * 修改费用
     * 
     * @param osExpense 费用
     * @return 结果
     */
    @Override
    public int updateOsExpense(OsExpense osExpense)
    {
        osExpense.setUpdateTime(DateUtils.getNowDate());
        return osExpenseMapper.updateOsExpense(osExpense);
    }

    /**
     * 批量删除费用
     * 
     * @param expenseIds 需要删除的费用主键
     * @return 结果
     */
    @Override
    public int deleteOsExpenseByExpenseIds(Long[] expenseIds)
    {
        return osExpenseMapper.deleteOsExpenseByExpenseIds(expenseIds);
    }

    /**
     * 删除费用信息
     * 
     * @param expenseId 费用主键
     * @return 结果
     */
    @Override
    public int deleteOsExpenseByExpenseId(Long expenseId)
    {
        return osExpenseMapper.deleteOsExpenseByExpenseId(expenseId);
    }
}
