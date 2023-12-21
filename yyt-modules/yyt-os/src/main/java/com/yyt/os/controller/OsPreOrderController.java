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
import com.yyt.os.domain.OsPreOrder;
import com.yyt.os.service.IOsPreOrderService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 处方订单Controller
 *
 * @author yyt
 * @date 2023-12-18
 */
@RestController
@RequestMapping("/recipe")
public class OsPreOrderController extends BaseController {
  @Autowired
  private IOsPreOrderService osPreOrderService;

  /**
   * 查询处方订单列表
   */
  @RequiresPermissions("recipe:recipe:list")
  @GetMapping("/list")
  public TableDataInfo list(OsPreOrder osPreOrder) {
    startPage();
    List<OsPreOrder> list = osPreOrderService.selectOsPreOrderList(osPreOrder);
    return getDataTable(list);
  }

  /**
   * 导出处方订单列表
   */
  @RequiresPermissions("recipe:recipe:export")
  @Log(title = "处方订单", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsPreOrder osPreOrder) {
    List<OsPreOrder> list = osPreOrderService.selectOsPreOrderList(osPreOrder);
    ExcelUtil<OsPreOrder> util = new ExcelUtil<OsPreOrder>(OsPreOrder.class);
    util.exportExcel(response, list, "处方订单数据");
  }

  /**
   * 获取处方订单详细信息
   */
  @RequiresPermissions("recipe:recipe:query")
  @GetMapping(value = "/{preCode}")
  public AjaxResult getInfo(@PathVariable("preCode") String preCode) {
    return success(osPreOrderService.selectOsPreOrderByPreCode(preCode));
  }

  /**
   * 新增处方订单
   */
  @RequiresPermissions("recipe:recipe:add")
  @Log(title = "处方订单", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsPreOrder osPreOrder) {
    return toAjax(osPreOrderService.insertOsPreOrder(osPreOrder));
  }

  /**
   * 修改处方订单
   */
  @RequiresPermissions("recipe:recipe:edit")
  @Log(title = "处方订单", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsPreOrder osPreOrder) {
    return toAjax(osPreOrderService.updateOsPreOrder(osPreOrder));
  }

  /**
   * 删除处方订单
   */
  @RequiresPermissions("recipe:recipe:remove")
  @Log(title = "处方订单", businessType = BusinessType.DELETE)
  @DeleteMapping("/{preCodes}")
  public AjaxResult remove(@PathVariable String[] preCodes) {
    return toAjax(osPreOrderService.deleteOsPreOrderByPreCodes(preCodes));
  }
}
