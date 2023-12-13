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
import com.yyt.reg.domain.RegHospital;
import com.yyt.reg.service.IRegHospitalService;
import com.yyt.common.core.web.controller.BaseController;
import com.yyt.common.core.web.domain.AjaxResult;
import com.yyt.common.core.utils.poi.ExcelUtil;
import com.yyt.common.core.web.page.TableDataInfo;

/**
 * 医院Controller
 *
 * @author yyt
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/hospital")
public class RegHospitalController extends BaseController {
    @Autowired
    private IRegHospitalService regHospitalService;

    /**
     * 查询医院列表
     */
    @RequiresPermissions("reg:hospital:list")
    @GetMapping("/list")
    public TableDataInfo list(RegHospital regHospital) {
        startPage();
        List<RegHospital> list = regHospitalService.selectRegHospitalList(regHospital);
        return getDataTable(list);
    }

    /**
     * 导出医院列表
     */
    @RequiresPermissions("reg:hospital:export")
    @Log(title = "医院", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegHospital regHospital) {
        List<RegHospital> list = regHospitalService.selectRegHospitalList(regHospital);
        ExcelUtil<RegHospital> util = new ExcelUtil<RegHospital>(RegHospital.class);
        util.exportExcel(response, list, "医院数据");
    }

    /**
     * 获取医院详细信息
     */
    @RequiresPermissions("reg:hospital:query")
    @GetMapping(value = "/{hospitalId}")
    public AjaxResult getInfo(@PathVariable("hospitalId") Long hospitalId) {
        return success(regHospitalService.selectRegHospitalByHospitalId(hospitalId));
    }

    /**
     * 新增医院
     */
    @RequiresPermissions("reg:hospital:add")
    @Log(title = "医院", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegHospital regHospital) {
        return toAjax(regHospitalService.insertRegHospital(regHospital));
    }

    /**
     * 修改医院
     */
    @RequiresPermissions("reg:hospital:edit")
    @Log(title = "医院", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegHospital regHospital) {
        return toAjax(regHospitalService.updateRegHospital(regHospital));
    }

    /**
     * 删除医院
     */
    @RequiresPermissions("reg:hospital:remove")
    @Log(title = "医院", businessType = BusinessType.DELETE)
    @DeleteMapping("/{hospitalIds}")
    public AjaxResult remove(@PathVariable Long[] hospitalIds) {
        return toAjax(regHospitalService.deleteRegHospitalByHospitalIds(hospitalIds));
    }

    /**
     * 查询所有医院
     */
    @GetMapping("/selAll")
    public TableDataInfo list(String longitude, String latitude, String keywords) {
        startPage();
        List<HashMap> list = regHospitalService.selAll(longitude, latitude, keywords);
        return getDataTable(list);
    }

    /**
     * 查询省份下所有的医院
     */
    @GetMapping("/selAllByProId")
    public TableDataInfo selAllByProId(String id, String longitude, String latitude, String tagId) {
        List<HashMap> list = regHospitalService.selAllByProId(id, longitude, latitude, tagId);
        return getDataTable(list);
    }

    /**
     * 查询市区下所有的医院
     */
    @GetMapping("/selAllByCityId")
    public TableDataInfo selAllByCityId(String id, String longitude, String latitude, String tagId) {
        List<HashMap> list = regHospitalService.selAllByCityId(id, longitude, latitude, tagId);
        return getDataTable(list);
    }

    /**
     * 查询区县下所有的医院
     */
    @GetMapping("/selAllByAreaId")
    public TableDataInfo selAllByAreaId(String id, String longitude, String latitude, String tagId) {
        System.out.println(id + "--------" + longitude + "----------" + latitude + "---------" + tagId);
        List<HashMap> list = regHospitalService.selAllByAreaId(id, longitude, latitude, tagId);
        return getDataTable(list);
    }
}
