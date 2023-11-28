package com.yyt.reg.controller;

import java.util.HashMap;
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
import com.yyt.reg.domain.SysAddress;
import com.yyt.reg.service.ISysAddressService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 地点Controller
 *
 * @author yyt
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/address")
public class SysAddressController extends BaseController {
    @Autowired
    private ISysAddressService sysAddressService;

    /**
     * 查询地点列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysAddress sysAddress) {
        startPage();
        List<SysAddress> list = sysAddressService.selectSysAddressList(sysAddress);
        return getDataTable(list);
    }

    /**
     * 导出地点列表
     */
    @RequiresPermissions("reg:address:export")
    @Log(title = "地点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAddress sysAddress) {
        List<SysAddress> list = sysAddressService.selectSysAddressList(sysAddress);
        ExcelUtil<SysAddress> util = new ExcelUtil<SysAddress>(SysAddress.class);
        util.exportExcel(response, list, "地点数据");
    }

    /**
     * 获取地点详细信息
     */
    @RequiresPermissions("reg:address:query")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId) {
        return success(sysAddressService.selectSysAddressByAddressId(addressId));
    }

    /**
     * 新增地点
     */
    @RequiresPermissions("reg:address:add")
    @Log(title = "地点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAddress sysAddress) {
        return toAjax(sysAddressService.insertSysAddress(sysAddress));
    }

    /**
     * 修改地点
     */
    @RequiresPermissions("reg:address:edit")
    @Log(title = "地点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAddress sysAddress) {
        return toAjax(sysAddressService.updateSysAddress(sysAddress));
    }

    /**
     * 删除地点
     */
    @RequiresPermissions("reg:address:remove")
    @Log(title = "地点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds) {
        return toAjax(sysAddressService.deleteSysAddressByAddressIds(addressIds));
    }

    /**
     * 查询所有省份
     *
     * @return 结果
     */
    @GetMapping("/selProvince")
    public TableDataInfo selProvince() {
        List<HashMap> list = sysAddressService.selProvince();
        return getDataTable(list);
    }

    @GetMapping("/selCityByProId")
    public TableDataInfo selCityByProId(String id) {
        List<HashMap> list = sysAddressService.selCityByProId(id);
        return getDataTable(list);
    }
}
