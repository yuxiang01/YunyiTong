package com.yyt.reg.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.reg.mapper.RegHospitalMapper;
import com.yyt.reg.domain.RegHospital;
import com.yyt.reg.service.IRegHospitalService;

/**
 * 医院Service业务层处理
 * 
 * @author yyt
 * @date 2023-11-22
 */
@Service
public class RegHospitalServiceImpl implements IRegHospitalService 
{
    @Autowired
    private RegHospitalMapper regHospitalMapper;

    /**
     * 查询医院
     * 
     * @param hospitalId 医院主键
     * @return 医院
     */
    @Override
    public RegHospital selectRegHospitalByHospitalId(Long hospitalId)
    {
        return regHospitalMapper.selectRegHospitalByHospitalId(hospitalId);
    }

    /**
     * 查询医院列表
     * 
     * @param regHospital 医院
     * @return 医院
     */
    @Override
    public List<RegHospital> selectRegHospitalList(RegHospital regHospital)
    {
        return regHospitalMapper.selectRegHospitalList(regHospital);
    }

    /**
     * 新增医院
     * 
     * @param regHospital 医院
     * @return 结果
     */
    @Override
    public int insertRegHospital(RegHospital regHospital)
    {
        return regHospitalMapper.insertRegHospital(regHospital);
    }

    /**
     * 修改医院
     * 
     * @param regHospital 医院
     * @return 结果
     */
    @Override
    public int updateRegHospital(RegHospital regHospital)
    {
        return regHospitalMapper.updateRegHospital(regHospital);
    }

    /**
     * 批量删除医院
     * 
     * @param hospitalIds 需要删除的医院主键
     * @return 结果
     */
    @Override
    public int deleteRegHospitalByHospitalIds(Long[] hospitalIds)
    {
        return regHospitalMapper.deleteRegHospitalByHospitalIds(hospitalIds);
    }

    /**
     * 删除医院信息
     * 
     * @param hospitalId 医院主键
     * @return 结果
     */
    @Override
    public int deleteRegHospitalByHospitalId(Long hospitalId)
    {
        return regHospitalMapper.deleteRegHospitalByHospitalId(hospitalId);
    }
}
