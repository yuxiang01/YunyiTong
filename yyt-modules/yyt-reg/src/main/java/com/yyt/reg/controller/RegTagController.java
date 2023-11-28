package com.yyt.reg.controller;

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
import com.yyt.reg.domain.RegTag;
import com.yyt.reg.service.IRegTagService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 医院标签Controller
 *
 * @author yyt
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/tag")
public class RegTagController extends BaseController {
    @Autowired
    private IRegTagService regTagService;

    /**
     * 查询医院标签列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RegTag regTag) {
        startPage();
        List<RegTag> list = regTagService.selectRegTagList(regTag);
        return getDataTable(list);
    }

    /**
     * 导出医院标签列表
     */
    @RequiresPermissions("reg:tag:export")
    @Log(title = "医院标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegTag regTag) {
        List<RegTag> list = regTagService.selectRegTagList(regTag);
        ExcelUtil<RegTag> util = new ExcelUtil<RegTag>(RegTag.class);
        util.exportExcel(response, list, "医院标签数据");
    }

    /**
     * 获取医院标签详细信息
     */
    @RequiresPermissions("reg:tag:query")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") Long tagId) {
        return success(regTagService.selectRegTagByTagId(tagId));
    }

    /**
     * 新增医院标签
     */
    @RequiresPermissions("reg:tag:add")
    @Log(title = "医院标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegTag regTag) {
        return toAjax(regTagService.insertRegTag(regTag));
    }

    /**
     * 修改医院标签
     */
    @RequiresPermissions("reg:tag:edit")
    @Log(title = "医院标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegTag regTag) {
        return toAjax(regTagService.updateRegTag(regTag));
    }

    /**
     * 删除医院标签
     */
    @RequiresPermissions("reg:tag:remove")
    @Log(title = "医院标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds) {
        return toAjax(regTagService.deleteRegTagByTagIds(tagIds));
    }
}
