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
import com.yyt.os.domain.OsRegorder;
import com.yyt.os.service.IOsRegorderService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 挂号订单Controller
 *
 * @author yyt
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/regorder")
public class OsRegorderController extends BaseController {
  @Autowired
  private IOsRegorderService osRegorderService;

  /**
   * 查询挂号订单列表
   */
  @RequiresPermissions("os:regorder:list")
  @GetMapping("/list")
  public TableDataInfo list(OsRegorder osRegorder) {
    startPage();
    List<OsRegorder> list = osRegorderService.selectOsRegorderList(osRegorder);
    return getDataTable(list);
  }

  /**
   * 导出挂号订单列表
   */
  @RequiresPermissions("os:regorder:export")
  @Log(title = "挂号订单", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, OsRegorder osRegorder) {
    List<OsRegorder> list = osRegorderService.selectOsRegorderList(osRegorder);
    ExcelUtil<OsRegorder> util = new ExcelUtil<OsRegorder>(OsRegorder.class);
    util.exportExcel(response, list, "挂号订单数据");
  }

  /**
   * 获取挂号订单详细信息
   */
  @RequiresPermissions("os:regorder:query")
  @GetMapping(value = "/{regId}")
  public AjaxResult getInfo(@PathVariable("regId") String regId) {
    return success(osRegorderService.selectOsRegorderByRegId(regId));
  }

  /**
   * 新增挂号订单
   */
  @RequiresPermissions("os:regorder:add")
  @Log(title = "挂号订单", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody OsRegorder osRegorder) {
    return toAjax(osRegorderService.insertOsRegorder(osRegorder));
  }

  /**
   * 修改挂号订单
   */
  @RequiresPermissions("os:regorder:edit")
  @Log(title = "挂号订单", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody OsRegorder osRegorder) {
    return toAjax(osRegorderService.updateOsRegorder(osRegorder));
  }

  /**
   * 删除挂号订单
   */
  @RequiresPermissions("os:regorder:remove")
  @Log(title = "挂号订单", businessType = BusinessType.DELETE)
  @DeleteMapping("/{regIds}")
  public AjaxResult remove(@PathVariable String[] regIds) {
    return toAjax(osRegorderService.deleteOsRegorderByRegIds(regIds));
  }

  /**
   * 根据医生ID查询该医生预约数
   *
   * @param doctorId 医生ID
   * @return
   */
  @GetMapping("/findDoctorRegCountByDoctorId")
  public AjaxResult findDoctorRegCountByDoctorId(String doctorId) {
    return success(osRegorderService.findDoctorRegCountByDoctorId(doctorId));
  }
}
