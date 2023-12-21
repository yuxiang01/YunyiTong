package com.yyt.os.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yyt.common.log.annotation.Log;
import com.yyt.common.log.enums.BusinessType;
import com.yyt.common.security.annotation.RequiresPermissions;
import com.yyt.os.domain.OsExpense;
import com.yyt.os.service.IOsExpenseService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 费用Controller
 *
 * @author yyt
 * @date 2023-12-20
 */
@RestController
@RequestMapping("/expense")
public class OsExpenseController extends BaseController {
  @Autowired
  private IOsExpenseService osExpenseService;

  /**
   * 查询费用列表
   */
  @RequiresPermissions("os:expense:list")
  @GetMapping("/list")
  public TableDataInfo list(OsExpense osExpense) {
    startPage();
    List<OsExpense> list = osExpenseService.selectOsExpenseList(osExpense);
    return getDataTable(list);
  }

  /**
   * 导出费用列表
   */
  @RequiresPermissions("os:expense:export")
  @Log(title = "费用", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsExpense osExpense) {
    List<OsExpense> list = osExpenseService.selectOsExpenseList(osExpense);
    ExcelUtil<OsExpense> util = new ExcelUtil<OsExpense>(OsExpense.class);
    util.exportExcel(response, list, "费用数据");
  }

  /**
   * 获取费用详细信息
   */
  @RequiresPermissions("os:expense:query")
  @GetMapping(value = "/{expenseId}")
  public AjaxResult getInfo(@PathVariable("expenseId") Long expenseId) {
    return success(osExpenseService.selectOsExpenseByExpenseId(expenseId));
  }

  /**
   * 新增费用
   */
  @RequiresPermissions("os:expense:add")
  @Log(title = "费用", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsExpense osExpense) {
    return toAjax(osExpenseService.insertOsExpense(osExpense));
  }

  /**
   * 修改费用
   */
  @RequiresPermissions("os:expense:edit")
  @Log(title = "费用", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsExpense osExpense) {
    return toAjax(osExpenseService.updateOsExpense(osExpense));
  }

  /**
   * 删除费用
   */
  @RequiresPermissions("os:expense:remove")
  @Log(title = "费用", businessType = BusinessType.DELETE)
  @DeleteMapping("/{expenseIds}")
  public AjaxResult remove(@PathVariable Long[] expenseIds) {
    return toAjax(osExpenseService.deleteOsExpenseByExpenseIds(expenseIds));
  }
}
