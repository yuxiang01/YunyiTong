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
import com.yyt.os.domain.OsCheckItem;
import com.yyt.os.service.IOsCheckItemService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 检查项目Controller
 *
 * @author yyt
 * @date 2023-12-19
 */
@RestController
@RequestMapping("/checkItem")
public class OsCheckItemController extends BaseController {
  @Autowired
  private IOsCheckItemService osCheckItemService;

  /**
   * 查询检查项目列表
   */
  @RequiresPermissions("os:checkItem:list")
  @GetMapping("/list")
  public TableDataInfo list(OsCheckItem osCheckItem) {
    startPage();
    List<OsCheckItem> list = osCheckItemService.selectOsCheckItemList(osCheckItem);
    return getDataTable(list);
  }

  /**
   * 导出检查项目列表
   */
  @RequiresPermissions("os:checkItem:export")
  @Log(title = "检查项目", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsCheckItem osCheckItem) {
    List<OsCheckItem> list = osCheckItemService.selectOsCheckItemList(osCheckItem);
    ExcelUtil<OsCheckItem> util = new ExcelUtil<OsCheckItem>(OsCheckItem.class);
    util.exportExcel(response, list, "检查项目数据");
  }

  /**
   * 获取检查项目详细信息
   */
  @RequiresPermissions("os:checkItem:query")
  @GetMapping(value = "/{checkId}")
  public AjaxResult getInfo(@PathVariable("checkId") Long checkId) {
    return success(osCheckItemService.selectOsCheckItemByCheckId(checkId));
  }

  /**
   * 新增检查项目
   */
  @RequiresPermissions("os:checkItem:add")
  @Log(title = "检查项目", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsCheckItem osCheckItem) {
    return toAjax(osCheckItemService.insertOsCheckItem(osCheckItem));
  }

  /**
   * 修改检查项目
   */
  @RequiresPermissions("os:checkItem:edit")
  @Log(title = "检查项目", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsCheckItem osCheckItem) {
    return toAjax(osCheckItemService.updateOsCheckItem(osCheckItem));
  }

  /**
   * 删除检查项目
   */
  @RequiresPermissions("os:checkItem:remove")
  @Log(title = "检查项目", businessType = BusinessType.DELETE)
  @DeleteMapping("/{checkIds}")
  public AjaxResult remove(@PathVariable Long[] checkIds) {
    return toAjax(osCheckItemService.deleteOsCheckItemByCheckIds(checkIds));
  }
}
