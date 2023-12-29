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
import com.yyt.os.domain.OsCaseHistory;
import com.yyt.os.service.IOsCaseHistoryService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 病历Controller
 *
 * @author yyt
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/caseHistory")
public class OsCaseHistoryController extends BaseController {
  @Autowired
  private IOsCaseHistoryService osCaseHistoryService;

  /**
   * 查询病历列表
   */
  @RequiresPermissions("os:caseHistory:list")
  @GetMapping("/list")
  public TableDataInfo list(OsCaseHistory osCaseHistory) {
    startPage();
    List<OsCaseHistory> list = osCaseHistoryService.selectOsCaseHistoryList(osCaseHistory);
    return getDataTable(list);
  }

  /**
   * 导出病历列表
   */
  @RequiresPermissions("os:caseHistory:export")
  @Log(title = "病历", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsCaseHistory osCaseHistory) {
    List<OsCaseHistory> list = osCaseHistoryService.selectOsCaseHistoryList(osCaseHistory);
    ExcelUtil<OsCaseHistory> util = new ExcelUtil<OsCaseHistory>(OsCaseHistory.class);
    util.exportExcel(response, list, "病历数据");
  }

  /**
   * 获取病历详细信息
   */
  @RequiresPermissions("os:caseHistory:query")
  @GetMapping(value = "/{caseId}")
  public AjaxResult getInfo(@PathVariable("caseId") Long caseId) {
    return success(osCaseHistoryService.selectOsCaseHistoryByCaseId(caseId));
  }

  /**
   * 新增病历
   */
  @RequiresPermissions("os:caseHistory:add")
  @Log(title = "病历", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsCaseHistory osCaseHistory) {
    return toAjax(osCaseHistoryService.insertOsCaseHistory(osCaseHistory));
  }

  /**
   * 修改病历
   */
  @RequiresPermissions("os:caseHistory:edit")
  @Log(title = "病历", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsCaseHistory osCaseHistory) {
    return toAjax(osCaseHistoryService.updateOsCaseHistory(osCaseHistory));
  }

  /**
   * 删除病历
   */
  @RequiresPermissions("os:caseHistory:remove")
  @Log(title = "病历", businessType = BusinessType.DELETE)
  @DeleteMapping("/{caseIds}")
  public AjaxResult remove(@PathVariable Long[] caseIds) {
    return toAjax(osCaseHistoryService.deleteOsCaseHistoryByCaseIds(caseIds));
  }
}
