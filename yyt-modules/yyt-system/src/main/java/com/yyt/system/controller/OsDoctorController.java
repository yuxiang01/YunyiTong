package com.yyt.system.controller;

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
import com.yyt.system.domain.OsDoctor;
import com.yyt.system.service.IOsDoctorService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 医生Controller
 *
 * @author yyt
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/doctor")
public class OsDoctorController extends BaseController {
  @Autowired
  private IOsDoctorService osDoctorService;

  /**
   * 查询医生列表
   */
  @RequiresPermissions("system:doctor:list")
  @GetMapping("/list")
  public TableDataInfo list(OsDoctor osDoctor) {
    startPage();
    List<OsDoctor> list = osDoctorService.selectOsDoctorList(osDoctor);
    return getDataTable(list);
  }

  /**
   * 导出医生列表
   */
  @RequiresPermissions("system:doctor:export")
  @Log(title = "医生", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsDoctor osDoctor) {
    List<OsDoctor> list = osDoctorService.selectOsDoctorList(osDoctor);
    ExcelUtil<OsDoctor> util = new ExcelUtil<OsDoctor>(OsDoctor.class);
    util.exportExcel(response, list, "医生数据");
  }

  /**
   * 获取医生详细信息
   */
  @RequiresPermissions("system:doctor:query")
  @GetMapping(value = "/{doctorId}")
  public AjaxResult getInfo(@PathVariable("doctorId") Long doctorId) {
    return success(osDoctorService.selectOsDoctorByDoctorId(doctorId));
  }

  /**
   * 新增医生
   */
  @RequiresPermissions("system:doctor:add")
  @Log(title = "医生", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsDoctor osDoctor) {
    return toAjax(osDoctorService.insertOsDoctor(osDoctor));
  }

  /**
   * 修改医生
   */
  @RequiresPermissions("system:doctor:edit")
  @Log(title = "医生", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsDoctor osDoctor) {
    return toAjax(osDoctorService.updateOsDoctor(osDoctor));
  }

  /**
   * 删除医生
   */
  @RequiresPermissions("system:doctor:remove")
  @Log(title = "医生", businessType = BusinessType.DELETE)
  @DeleteMapping("/{doctorIds}")
  public AjaxResult remove(@PathVariable Long[] doctorIds) {
    return toAjax(osDoctorService.deleteOsDoctorByDoctorIds(doctorIds));
  }


  /**
   * 根据部门ID查询医生
   *
   * @param deptId 部门ID
   * @return
   */
  @GetMapping("/findDoctorByDeptId")
  public AjaxResult findDoctorByDeptId(String deptId){
    return success(osDoctorService.findDoctorByDeptId(deptId));
  }

  /**
   * 根据医生ID查询医生
   *
   * @param doctorId 医生ID
   * @return
   */
  @GetMapping("/findDoctorByDoctorId")
  public AjaxResult findDoctorByDoctorId(String doctorId){
    return success(osDoctorService.findDoctorByDoctorId(doctorId));
  }
}
