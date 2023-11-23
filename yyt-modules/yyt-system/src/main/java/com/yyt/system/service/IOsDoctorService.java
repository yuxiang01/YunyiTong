package com.yyt.system.service;

import java.util.List;
import com.yyt.system.domain.OsDoctor;

/**
 * 医生Service接口
 * 
 * @author yyt
 * @date 2023-11-23
 */
public interface IOsDoctorService 
{
    /**
     * 查询医生
     * 
     * @param doctorId 医生主键
     * @return 医生
     */
    public OsDoctor selectOsDoctorByDoctorId(Long doctorId);

    /**
     * 查询医生列表
     * 
     * @param osDoctor 医生
     * @return 医生集合
     */
    public List<OsDoctor> selectOsDoctorList(OsDoctor osDoctor);

    /**
     * 新增医生
     * 
     * @param osDoctor 医生
     * @return 结果
     */
    public int insertOsDoctor(OsDoctor osDoctor);

    /**
     * 修改医生
     * 
     * @param osDoctor 医生
     * @return 结果
     */
    public int updateOsDoctor(OsDoctor osDoctor);

    /**
     * 批量删除医生
     * 
     * @param doctorIds 需要删除的医生主键集合
     * @return 结果
     */
    public int deleteOsDoctorByDoctorIds(Long[] doctorIds);

    /**
     * 删除医生信息
     * 
     * @param doctorId 医生主键
     * @return 结果
     */
    public int deleteOsDoctorByDoctorId(Long doctorId);
}
