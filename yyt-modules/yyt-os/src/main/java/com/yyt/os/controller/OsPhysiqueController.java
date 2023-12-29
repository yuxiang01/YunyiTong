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
import com.yyt.os.domain.OsPhysique;
import com.yyt.os.service.IOsPhysiqueService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 体格Controller
 *
 * @author yyt
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/physique")
public class OsPhysiqueController extends BaseController {
  @Autowired
  private IOsPhysiqueService osPhysiqueService;

  /**
   * 查询体格列表
   */
  @RequiresPermissions("os:physique:list")
  @GetMapping("/list")
  public TableDataInfo list(OsPhysique osPhysique) {
    startPage();
    List<OsPhysique> list = osPhysiqueService.selectOsPhysiqueList(osPhysique);
    return getDataTable(list);
  }

  /**
   * 导出体格列表
   */
  @RequiresPermissions("os:physique:export")
  @Log(title = "体格", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsPhysique osPhysique) {
    List<OsPhysique> list = osPhysiqueService.selectOsPhysiqueList(osPhysique);
    ExcelUtil<OsPhysique> util = new ExcelUtil<OsPhysique>(OsPhysique.class);
    util.exportExcel(response, list, "体格数据");
  }

  /**
   * 获取体格详细信息
   */
  @RequiresPermissions("os:physique:query")
  @GetMapping(value = "/{physiqueId}")
  public AjaxResult getInfo(@PathVariable("physiqueId") Long physiqueId) {
    return success(osPhysiqueService.selectOsPhysiqueByPhysiqueId(physiqueId));
  }

  /**
   * 新增体格
   */
  @RequiresPermissions("os:physique:add")
  @Log(title = "体格", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsPhysique osPhysique) {
    return toAjax(osPhysiqueService.insertOsPhysique(osPhysique));
  }

  /**
   * 修改体格
   */
  @RequiresPermissions("os:physique:edit")
  @Log(title = "体格", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsPhysique osPhysique) {
    return toAjax(osPhysiqueService.updateOsPhysique(osPhysique));
  }

  /**
   * 删除体格
   */
  @RequiresPermissions("os:physique:remove")
  @Log(title = "体格", businessType = BusinessType.DELETE)
  @DeleteMapping("/{physiqueIds}")
  public AjaxResult remove(@PathVariable Long[] physiqueIds) {
    return toAjax(osPhysiqueService.deleteOsPhysiqueByPhysiqueIds(physiqueIds));
  }
}
