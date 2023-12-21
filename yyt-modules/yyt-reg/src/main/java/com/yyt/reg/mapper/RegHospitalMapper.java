package com.yyt.reg.mapper;

import java.util.HashMap;
import java.util.List;

import com.yyt.reg.domain.RegHospital;

/**
 * 医院Mapper接口
 *
 * @author yyt
 * @date 2023-11-22
 */
public interface RegHospitalMapper {
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
     * 删除医院
     *
     * @param hospitalId 医院主键
     * @return 结果
     */
    public int deleteRegHospitalByHospitalId(Long hospitalId);

    /**
     * 批量删除医院
     *
     * @param hospitalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegHospitalByHospitalIds(Long[] hospitalIds);

    /**
     * 查询首页展示医院信息
     *
     * @param keywords
     * @return 结果
     */
    public List<HashMap> selAll(String keywords);

    /**
     * 查询该省下所有医院
     *
     * @param param1    地区ID
     * @param param2 标签ID
     * @return 结果
     */
    public List<HashMap> selAllByProId(String param1, String param2);

    /**
     * 查询该市下所有医院
     *
     * @param param1    地区ID
     * @param param2 标签ID
     * @return 结果
     */
    public List<HashMap> selAllByCityId(String param1, String param2);

    /**
     * 查询该区县下所有医院
     *
     * @param param1 地区ID
     * @param param2 标签ID
     * @return 结果
     */
    public List<HashMap> selAllByAreaId(String param1, String param2);
}
