package com.yyt.system.service.impl;

import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.system.mapper.OsDoctorMapper;
import com.yyt.system.domain.OsDoctor;
import com.yyt.system.service.IOsDoctorService;

/**
 * 医生Service业务层处理
 * 
 * @author yyt
 * @date 2023-11-23
 */
@Service
public class OsDoctorServiceImpl implements IOsDoctorService 
{
    @Autowired
    private OsDoctorMapper osDoctorMapper;

    /**
     * 查询医生
     * 
     * @param doctorId 医生主键
     * @return 医生
     */
    @Override
    public OsDoctor selectOsDoctorByDoctorId(Long doctorId)
    {
        return osDoctorMapper.selectOsDoctorByDoctorId(doctorId);
    }

    /**
     * 查询医生列表
     * 
     * @param osDoctor 医生
     * @return 医生
     */
    @Override
    public List<OsDoctor> selectOsDoctorList(OsDoctor osDoctor)
    {
        return osDoctorMapper.selectOsDoctorList(osDoctor);
    }

    /**
     * 新增医生
     * 
     * @param osDoctor 医生
     * @return 结果
     */
    @Override
    public int insertOsDoctor(OsDoctor osDoctor)
    {
        osDoctor.setCreateTime(DateUtils.getNowDate());
        return osDoctorMapper.insertOsDoctor(osDoctor);
    }

    /**
     * 修改医生
     * 
     * @param osDoctor 医生
     * @return 结果
     */
    @Override
    public int updateOsDoctor(OsDoctor osDoctor)
    {
        osDoctor.setUpdateTime(DateUtils.getNowDate());
        return osDoctorMapper.updateOsDoctor(osDoctor);
    }

    /**
     * 批量删除医生
     * 
     * @param doctorIds 需要删除的医生主键
     * @return 结果
     */
    @Override
    public int deleteOsDoctorByDoctorIds(Long[] doctorIds)
    {
        return osDoctorMapper.deleteOsDoctorByDoctorIds(doctorIds);
    }

    /**
     * 删除医生信息
     * 
     * @param doctorId 医生主键
     * @return 结果
     */
    @Override
    public int deleteOsDoctorByDoctorId(Long doctorId)
    {
        return osDoctorMapper.deleteOsDoctorByDoctorId(doctorId);
    }
}
