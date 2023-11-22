package com.yyt.reg.mapper;

import java.util.List;
import com.yyt.reg.domain.RegHospital;

/**
 * 医院Mapper接口
 * 
 * @author yyt
 * @date 2023-11-22
 */
public interface RegHospitalMapper 
{
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
}
