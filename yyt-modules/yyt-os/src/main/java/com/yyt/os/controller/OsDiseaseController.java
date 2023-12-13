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
import com.yyt.os.domain.OsDisease;
import com.yyt.os.service.IOsDiseaseService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 病症Controller
 * 
 * @author yyt
 * @date 2023-12-13
 */
@RestController
@RequestMapping("/disease")
public class OsDiseaseController extends BaseController
{
    @Autowired
    private IOsDiseaseService osDiseaseService;

    /**
     * 查询病症列表
     */
    //@RequiresPermissions("system:disease:list")
    @GetMapping("/list")
    public TableDataInfo list(OsDisease osDisease)
    {
        startPage();
        List<OsDisease> list = osDiseaseService.selectOsDiseaseList(osDisease);
        return getDataTable(list);
    }

    /**
     * 导出病症列表
     */
    @RequiresPermissions("system:disease:export")
    @Log(title = "病症", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OsDisease osDisease)
    {
        List<OsDisease> list = osDiseaseService.selectOsDiseaseList(osDisease);
        ExcelUtil<OsDisease> util = new ExcelUtil<OsDisease>(OsDisease.class);
        util.exportExcel(response, list, "病症数据");
    }

    /**
     * 获取病症详细信息
     */
    @RequiresPermissions("system:disease:query")
    @GetMapping(value = "/{disId}")
    public AjaxResult getInfo(@PathVariable("disId") Long disId)
    {
        return success(osDiseaseService.selectOsDiseaseByDisId(disId));
    }

    /**
     * 新增病症
     */
    @RequiresPermissions("system:disease:add")
    @Log(title = "病症", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OsDisease osDisease)
    {
        return toAjax(osDiseaseService.insertOsDisease(osDisease));
    }

    /**
     * 修改病症
     */
    @RequiresPermissions("system:disease:edit")
    @Log(title = "病症", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OsDisease osDisease)
    {
        return toAjax(osDiseaseService.updateOsDisease(osDisease));
    }

    /**
     * 删除病症
     */
    @RequiresPermissions("system:disease:remove")
    @Log(title = "病症", businessType = BusinessType.DELETE)
	@DeleteMapping("/{disIds}")
    public AjaxResult remove(@PathVariable Long[] disIds)
    {
        return toAjax(osDiseaseService.deleteOsDiseaseByDisIds(disIds));
    }
}
