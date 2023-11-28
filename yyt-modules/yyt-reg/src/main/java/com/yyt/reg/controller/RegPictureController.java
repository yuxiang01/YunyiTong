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
import com.yyt.reg.domain.RegPicture;
import com.yyt.reg.service.IRegPictureService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 图片Controller
 * 
 * @author yyt
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/picture")
public class RegPictureController extends BaseController
{
    @Autowired
    private IRegPictureService regPictureService;

    /**
     * 查询图片列表
     */
    @RequiresPermissions("reg:picture:list")
    @GetMapping("/list")
    public TableDataInfo list(RegPicture regPicture)
    {
        startPage();
        List<RegPicture> list = regPictureService.selectRegPictureList(regPicture);
        return getDataTable(list);
    }

    /**
     * 导出图片列表
     */
    @RequiresPermissions("reg:picture:export")
    @Log(title = "图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegPicture regPicture)
    {
        List<RegPicture> list = regPictureService.selectRegPictureList(regPicture);
        ExcelUtil<RegPicture> util = new ExcelUtil<RegPicture>(RegPicture.class);
        util.exportExcel(response, list, "图片数据");
    }

    /**
     * 获取图片详细信息
     */
    @RequiresPermissions("reg:picture:query")
    @GetMapping(value = "/{pictureId}")
    public AjaxResult getInfo(@PathVariable("pictureId") Long pictureId)
    {
        return success(regPictureService.selectRegPictureByPictureId(pictureId));
    }

    /**
     * 新增图片
     */
    @RequiresPermissions("reg:picture:add")
    @Log(title = "图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegPicture regPicture)
    {
        return toAjax(regPictureService.insertRegPicture(regPicture));
    }

    /**
     * 修改图片
     */
    @RequiresPermissions("reg:picture:edit")
    @Log(title = "图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegPicture regPicture)
    {
        return toAjax(regPictureService.updateRegPicture(regPicture));
    }

    /**
     * 删除图片
     */
    @RequiresPermissions("reg:picture:remove")
    @Log(title = "图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pictureIds}")
    public AjaxResult remove(@PathVariable Long[] pictureIds)
    {
        return toAjax(regPictureService.deleteRegPictureByPictureIds(pictureIds));
    }
}
