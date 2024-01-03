package com.yyt.os.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/order")
public class OsPreOrderController extends BaseController {
  @Autowired
  private IOsPreOrderService osPreOrderService;

  /**
   * 查询处方订单列表
   */
  @RequiresPermissions("os:order:list")
  @GetMapping("/list")
  public TableDataInfo list(OsPreOrder osPreOrder) {
    startPage();
    return getDataTable(osPreOrderService.selectOsPreOrderList(osPreOrder));
  }

  @RequiresPermissions("os:order:list")
  @GetMapping("/case/list")
  public TableDataInfo caseList(OsPreOrder osPreOrder) {
    return getDataTable(osPreOrderService.selectOsPreOrderList(osPreOrder));
  }

  /**
   * 导出处方订单列表
   */
  @RequiresPermissions("os:order:export")
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
  @RequiresPermissions("os:order:query")
  @GetMapping(value = "/{preCode}")
  public AjaxResult getInfo(@PathVariable("preCode") String preCode) {
    return success(osPreOrderService.selectOsPreOrderByPreCode(preCode));
  }

  /**
   * 获取处方订单详细信息 by regId
   */
  @RequiresPermissions("os:order:query")
  @GetMapping(value = "/reg/{regId}")
  public AjaxResult getInfoByRegId(@PathVariable("regId") String regId) {
    return success(osPreOrderService.selectOsPreOrderByRegId(regId));
  }

  /**
   * 新增处方订单
   */
  @RequiresPermissions("os:order:add")
  @Log(title = "处方订单", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@Validated @RequestBody OsPreOrder osPreOrder) {
    return toAjax(osPreOrderService.insertOsPreOrder(osPreOrder));
  }

  // 保存病历信息
  @RequiresPermissions("os:order:add")
  @Log(title = "处方订单", businessType = BusinessType.INSERT)
  @PostMapping("/case")
  public AjaxResult saveCaseHistoryInfo(@Validated @RequestBody OsPreOrder preOrder) {
    return toAjax(osPreOrderService.saveCaseHistoryInfo(preOrder));
  }

  // 保存病历信息
  @RequiresPermissions("os:order:add")
  @Log(title = "处方订单", businessType = BusinessType.INSERT)
  @PostMapping("/finish")
  public AjaxResult finishSaveInfo(@Validated @RequestBody OsPreOrder preOrder) {
    return toAjax(osPreOrderService.finishSaveInfo(preOrder));
  }

  // 支付处方单
  @RequiresPermissions("os:order:edit")
  @Log(title = "支付订单", businessType = BusinessType.UPDATE)
  @PostMapping("/pay")
  public AjaxResult payPer(@RequestBody OsPreOrder preOrder) {
    return toAjax(osPreOrderService.payPre(preOrder));
  }

  /**
   * 修改处方订单
   */
  @RequiresPermissions("os:order:edit")
  @Log(title = "处方订单", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsPreOrder osPreOrder) {
    return toAjax(osPreOrderService.updateOsPreOrder(osPreOrder));
  }

  /**
   * 删除处方订单
   */
  @RequiresPermissions("os:order:remove")
  @Log(title = "处方订单", businessType = BusinessType.DELETE)
  @DeleteMapping("/{preCodes}")
  public AjaxResult remove(@PathVariable String[] preCodes) {
    return toAjax(osPreOrderService.deleteOsPreOrderByPreCodes(preCodes));
  }
}
