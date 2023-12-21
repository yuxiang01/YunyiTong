package com.yyt.reg.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.yyt.reg.mapper.RegPictureMapper;
import com.yyt.reg.mapper.RegTagMapper;
import com.yyt.reg.utils.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.yyt.reg.mapper.RegHospitalMapper;
import com.yyt.reg.domain.RegHospital;
import com.yyt.reg.service.IRegHospitalService;

import javax.annotation.Resource;

/**
 * 医院Service业务层处理
 *
 * @author yyt
 * @date 2023-11-22
 */
@Service
public class RegHospitalServiceImpl implements IRegHospitalService {
    @Autowired
    private RegHospitalMapper regHospitalMapper;

    @Resource
    private RegPictureMapper regPictureMapper;

    @Resource
    private RegTagMapper regTagMapper;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 查询医院
     *
     * @param hospitalId 医院主键
     * @return 医院
     */
    @Override
    public RegHospital selectRegHospitalByHospitalId(Long hospitalId) {
        return regHospitalMapper.selectRegHospitalByHospitalId(hospitalId);
    }

    /**
     * 查询医院列表
     *
     * @param regHospital 医院
     * @return 医院
     */
    @Override
    public List<RegHospital> selectRegHospitalList(RegHospital regHospital) {
        return regHospitalMapper.selectRegHospitalList(regHospital);
    }

    /**
     * 新增医院
     *
     * @param regHospital 医院
     * @return 结果
     */
    @Override
    public int insertRegHospital(RegHospital regHospital) {
        return regHospitalMapper.insertRegHospital(regHospital);
    }

    /**
     * 修改医院
     *
     * @param regHospital 医院
     * @return 结果
     */
    @Override
    public int updateRegHospital(RegHospital regHospital) {
        return regHospitalMapper.updateRegHospital(regHospital);
    }

    /**
     * 批量删除医院
     *
     * @param hospitalIds 需要删除的医院主键
     * @return 结果
     */
    @Override
    public int deleteRegHospitalByHospitalIds(Long[] hospitalIds) {
        return regHospitalMapper.deleteRegHospitalByHospitalIds(hospitalIds);
    }

    /**
     * 删除医院信息
     *
     * @param hospitalId 医院主键
     * @return 结果
     */
    @Override
    public int deleteRegHospitalByHospitalId(Long hospitalId) {
        return regHospitalMapper.deleteRegHospitalByHospitalId(hospitalId);
    }

    /**
     * 查询首页展示医院信息
     *
     * @return 结果
     */
    @Override
    public List<HashMap> selAll(String longitude, String latitude,String keywords) {
        GeoUtil geoUtil = applicationContext.getBean(GeoUtil.class);
        if (geoUtil.get("hospital") != null) {
            return (List<HashMap>) geoUtil.get("hospital");
        } else {
            List<HashMap> list = sort(regHospitalMapper.selAll(keywords), longitude, latitude);
            //添加到redis中
            geoUtil.set("hospital", list);
            return list;
        }
    }

    @Override
    public List<HashMap> selAllByProId(String id, String longitude, String latitude, String tagId) {
        return sort(regHospitalMapper.selAllByProId(id, tagId), longitude, latitude);
    }

    @Override
    public List<HashMap> selAllByCityId(String id, String longitude, String latitude, String tagId) {
        return sort(regHospitalMapper.selAllByCityId(id, tagId), longitude, latitude);
    }

    @Override
    public List<HashMap> selAllByAreaId(String id, String longitude, String latitude, String tagId) {
        return sort(regHospitalMapper.selAllByAreaId(id, tagId), longitude, latitude);
    }


    //医院信息列表排序
    private List<HashMap> sort(List<HashMap> list, String longitude, String latitude) {
        GeoUtil geoUtil = applicationContext.getBean(GeoUtil.class);
        List<HashMap> hashMapList = new ArrayList<>();
        list.forEach(e -> {
            //添加当前位置到redis中
            geoUtil.geoAdd("当前位置", Double.parseDouble(longitude), Double.parseDouble(latitude));
            //添加医院位置到redis中
            geoUtil.geoAdd(e.get("name").toString(), Double.parseDouble(e.get("position").toString().split(",")[0]), Double.parseDouble(e.get("position").toString().split(",")[1]));
            //添加医院标签，根据标标签ID获取标签集
            String tag = "";
            for (String str : e.get("tag_id").toString().split(",")) {
                tag += regTagMapper.selAll(str) + "/";
            }
            tag = tag.substring(0, tag.length() - 1);
            e.put("tag", tag);
            //添加医院图片，根据医院ID获取图片路径
            e.put("image", regPictureMapper.selAll("医院-" + e.get("hospital_id").toString()));
            //判断距离
            e.put("distance", (int) geoUtil.distanceBetween("当前位置", e.get("name").toString()));
            hashMapList.add(e);
        });
        //按照距离排序
        hashMapList.sort(Comparator.comparingInt(o -> Integer.parseInt(o.get("distance").toString())));
        return hashMapList;
    }
}
