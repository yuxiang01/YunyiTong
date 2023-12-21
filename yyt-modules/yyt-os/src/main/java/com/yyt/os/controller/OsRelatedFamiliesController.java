package com.yyt.os.controller;

import java.util.List;
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
import com.yyt.os.domain.OsRelatedFamilies;
import com.yyt.os.service.IOsRelatedFamiliesService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 患者家庭关系Controller
 *
 * @author fishx
 * @date 2023-12-13
 */
@RestController
@RequestMapping("/families")
public class OsRelatedFamiliesController extends BaseController {
  @Autowired
  private IOsRelatedFamiliesService osRelatedFamiliesService;

  /**
   * 查询患者家庭关系列表
   */
  @RequiresPermissions("os:families:list")
  @GetMapping("/list")
  public TableDataInfo list(Long patientId) {
    List<OsRelatedFamilies> list = osRelatedFamiliesService.selectOsRelatedFamiliesList(patientId);
    return getDataTable(list);
  }

  /**
   * 获取患者家庭关系详细信息
   */
  @RequiresPermissions("os:families:query")
  @GetMapping(value = "/{relatedId}")
  public AjaxResult getInfo(@PathVariable("relatedId") Long relatedId) {
    return success(osRelatedFamiliesService.selectOsRelatedFamiliesByRelatedId(relatedId));
  }

  /**
   * 新增患者家庭关系
   */
  @RequiresPermissions("os:families:add")
  @Log(title = "患者家庭关系", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsRelatedFamilies osRelatedFamilies) {
    return osRelatedFamiliesService.insertOsRelatedFamilies(osRelatedFamilies);
  }

  /**
   * 修改患者家庭关系
   */
  @RequiresPermissions("os:families:edit")
  @Log(title = "患者家庭关系", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsRelatedFamilies osRelatedFamilies) {
    return toAjax(osRelatedFamiliesService.updateOsRelatedFamilies(osRelatedFamilies));
  }

  /**
   * 删除患者家庭关系
   */
  @RequiresPermissions("os:families:remove")
  @Log(title = "患者家庭关系", businessType = BusinessType.DELETE)
  @DeleteMapping("/{relatedIds}")
  public AjaxResult remove(@PathVariable Long[] relatedIds) {
    return toAjax(osRelatedFamiliesService.deleteOsRelatedFamiliesByRelatedIds(relatedIds));
  }
}
