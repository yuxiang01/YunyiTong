package com.yyt.os.controller;

import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.web.page.TableDataInfo;
import com.yyt.common.log.annotation.Log;
import com.yyt.common.log.enums.BusinessType;
import com.yyt.common.security.annotation.RequiresPermissions;
import com.yyt.os.domain.OsDrug;
import com.yyt.os.service.IOsDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 药品Controller
 *
 * @author yyt
 * @date 2023-12-05
 */
@RestController
@RequestMapping("/drug")
public class OsDrugController extends BaseController {
  @Autowired
  private IOsDrugService osDrugService;

  /**
   * 查询药品列表
   */
  @RequiresPermissions("os:drug:list")
  @GetMapping("/list")
  public TableDataInfo list(OsDrug osDrug) {
    startPage();
    List<OsDrug> list = osDrugService.selectOsDrugList(osDrug);
    return getDataTable(list);
  }

  /**
   * 导出药品列表
   */
  @RequiresPermissions("os:drug:export")
  @Log(title = "药品", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsDrug osDrug) {
    List<OsDrug> list = osDrugService.selectOsDrugList(osDrug);
    ExcelUtil<OsDrug> util = new ExcelUtil<OsDrug>(OsDrug.class);
    util.exportExcel(response, list, "药品数据");
  }

  /**
   * 获取药品详细信息
   */
  @RequiresPermissions("os:drug:query")
  @GetMapping(value = "/{drugId}")
  public AjaxResult getInfo(@PathVariable("drugId") Long drugId) {
    return success(osDrugService.selectOsDrugByDrugId(drugId));
  }

  /**
   * 新增药品
   */
  @RequiresPermissions("os:drug:add")
  @Log(title = "药品", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsDrug osDrug) {
    return toAjax(osDrugService.insertOsDrug(osDrug));
  }

  /**
   * 修改药品
   */
  @RequiresPermissions("os:drug:edit")
  @Log(title = "药品", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsDrug osDrug) {
    return toAjax(osDrugService.updateOsDrug(osDrug));
  }

  /**
   * 删除药品
   */
  @RequiresPermissions("os:drug:remove")
  @Log(title = "药品", businessType = BusinessType.DELETE)
  @DeleteMapping("/{drugIds}")
  public AjaxResult remove(@PathVariable Long[] drugIds) {
    return toAjax(osDrugService.deleteOsDrugByDrugIds(drugIds));
  }
}
