package com.yyt.os.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.tree.TreeUtil;
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
import com.yyt.os.domain.SysPatient;
import com.yyt.os.service.ISysPatientService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 患者Controller
 *
 * @author yyt
 * @date 2023-11-30
 */
@RestController
@RequestMapping("/patient")
public class SysPatientController extends BaseController {
  @Autowired
  private ISysPatientService sysPatientService;

  /**
   * 查询患者列表
   */
  @RequiresPermissions("os:patient:list")
  @GetMapping("/list")
  public TableDataInfo list(SysPatient sysPatient) {
    startPage();
    List<SysPatient> list = sysPatientService.selectSysPatientList(sysPatient);
    return getDataTable(list);
  }

  /**
   * 导出患者列表
   */
  @RequiresPermissions("os:patient:export")
  @Log(title = "患者", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysPatient sysPatient) {
    List<SysPatient> list = sysPatientService.selectSysPatientList(sysPatient);
    ExcelUtil<SysPatient> util = new ExcelUtil<SysPatient>(SysPatient.class);
    util.exportExcel(response, list, "患者数据");
  }

  /**
   * 获取患者详细信息
   */
  @RequiresPermissions("os:patient:query")
  @GetMapping(value = "/{patientId}")
  public AjaxResult getInfo(@PathVariable("patientId") Long patientId) {
    return success(sysPatientService.selectSysPatientByPatientId(patientId));
  }

  /**
   * 新增患者
   */
  @RequiresPermissions("os:patient:add")
  @Log(title = "患者", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SysPatient sysPatient) {
    return toAjax(sysPatientService.insertSysPatient(sysPatient));
  }

  /**
   * 修改患者
   */
  @RequiresPermissions("os:patient:edit")
  @Log(title = "患者", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SysPatient sysPatient) {
    return toAjax(sysPatientService.updateSysPatient(sysPatient));
  }

  /**
   * 删除患者
   */
  @RequiresPermissions("os:patient:remove")
  @Log(title = "患者", businessType = BusinessType.DELETE)
  @DeleteMapping("/{patientIds}")
  public AjaxResult remove(@PathVariable Long[] patientIds) {
    return toAjax(sysPatientService.deleteSysPatientByPatientIds(patientIds));
  }
}
