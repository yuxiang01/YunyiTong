package com.yyt.reg.service;

import java.util.HashMap;
import java.util.List;

import com.yyt.reg.domain.RegHospital;

/**
 * 医院Service接口
 *
 * @author yyt
 * @date 2023-11-22
 */
public interface IRegHospitalService {
    /**
     * 查询医院
     *
     * @param hospitalId 医院主键
     * @return 医院
     */
    public RegHospital selectRegHospitalByHospitalId(Long hospitalId);

    /**
     * 查询医院列表
     *
     * @param regHospital 医院
     * @return 医院集合
     */
    public List<RegHospital> selectRegHospitalList(RegHospital regHospital);

    /**
     * 新增医院
     *
     * @param regHospital 医院
     * @return 结果
     */
    public int insertRegHospital(RegHospital regHospital);

    /**
     * 修改医院
     *
     * @param regHospital 医院
     * @return 结果
     */
    public int updateRegHospital(RegHospital regHospital);

    /**
     * 批量删除医院
     *
     * @param hospitalIds 需要删除的医院主键集合
     * @return 结果
     */
    public int deleteRegHospitalByHospitalIds(Long[] hospitalIds);

    /**
     * 删除医院信息
     *
     * @param hospitalId 医院主键
     * @return 结果
     */
    public int deleteRegHospitalByHospitalId(Long hospitalId);

    /**
     * 查询首页展示医院信息
     *
     * @return 结果
     */
    public List<HashMap> selAll(String longitude, String latitude,String keywords);

    /**
     * 查询该省下所有医院
     *
     * @param id 地区ID
     * @return 结果
     */
    public List<HashMap> selAllByProId(String id, String longitude, String latitude, String tagId);

    /**
     * 查询该市下所有医院
     *
     * @param id 地区ID
     * @return 结果
     */
    public List<HashMap> selAllByCityId(String id, String longitude, String latitude, String tagId);

    /**
     * 查询该区县下所有医院
     *
     * @param id 地区ID
     * @param tagId 标签ID
     * @return 结果
     */
    public List<HashMap> selAllByAreaId(String id, String longitude, String latitude, String tagId);
}
