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
import com.yyt.os.domain.OsSchedule;
import com.yyt.os.service.IOsScheduleService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 排班Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/schedule")
public class OsScheduleController extends BaseController
{
    @Autowired
    private IOsScheduleService osScheduleService;

    /**
     * 查询排班列表
     */
    @RequiresPermissions("os:schedule:list")
    @GetMapping("/list")
    public TableDataInfo list(OsSchedule osSchedule)
    {
        startPage();
        List<OsSchedule> list = osScheduleService.selectOsScheduleList(osSchedule);
        return getDataTable(list);
    }

    /**
     * 导出排班列表
     */
    @RequiresPermissions("os:schedule:export")
    @Log(title = "排班", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OsSchedule osSchedule)
    {
        List<OsSchedule> list = osScheduleService.selectOsScheduleList(osSchedule);
        ExcelUtil<OsSchedule> util = new ExcelUtil<OsSchedule>(OsSchedule.class);
        util.exportExcel(response, list, "排班数据");
    }

    /**
     * 获取排班详细信息
     */
    @RequiresPermissions("os:schedule:query")
    @GetMapping(value = "/{schedId}")
    public AjaxResult getInfo(@PathVariable("schedId") Long schedId)
    {
        return success(osScheduleService.selectOsScheduleBySchedId(schedId));
    }

    /**
     * 新增排班
     */
    @RequiresPermissions("os:schedule:add")
    @Log(title = "排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OsSchedule osSchedule)
    {
        return toAjax(osScheduleService.insertOsSchedule(osSchedule));
    }

    /**
     * 修改排班
     */
    @RequiresPermissions("os:schedule:edit")
    @Log(title = "排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OsSchedule osSchedule)
    {
        return toAjax(osScheduleService.updateOsSchedule(osSchedule));
    }

    /**
     * 删除排班
     */
    @RequiresPermissions("os:schedule:remove")
    @Log(title = "排班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{schedIds}")
    public AjaxResult remove(@PathVariable Long[] schedIds)
    {
        return toAjax(osScheduleService.deleteOsScheduleBySchedIds(schedIds));
    }

    /**
     * 查询医生排班信息
     */
    @GetMapping("/findScheduleInfoByDoctorId")
    public AjaxResult findScheduleInfoByDoctorId(String doctorId)
    {
        return success(osScheduleService.findScheduleInfoByDoctorId(doctorId));
    }
}
