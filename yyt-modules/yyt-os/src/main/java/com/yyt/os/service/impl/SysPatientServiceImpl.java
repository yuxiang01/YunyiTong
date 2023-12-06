package com.yyt.os.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.SysPatientMapper;
import com.yyt.os.domain.SysPatient;
import com.yyt.os.service.ISysPatientService;

/**
 * 患者Service业务层处理
 * 
 * @author yyt
 * @date 2023-11-30
 */
@Service
public class SysPatientServiceImpl implements ISysPatientService 
{
    @Autowired
    private SysPatientMapper sysPatientMapper;

    /**
     * 查询患者
     * 
     * @param patientId 患者主键
     * @return 患者
     */
    @Override
    public SysPatient selectSysPatientByPatientId(Long patientId)
    {
        return sysPatientMapper.selectSysPatientByPatientId(patientId);
    }

    /**
     * 查询患者列表
     * 
     * @param sysPatient 患者
     * @return 患者
     */
    @Override
    public List<SysPatient> selectSysPatientList(SysPatient sysPatient)
    {
        return sysPatientMapper.selectSysPatientList(sysPatient);
    }

    /**
     * 新增患者
     * 
     * @param sysPatient 患者
     * @return 结果
     */
    @Override
    public int insertSysPatient(SysPatient sysPatient)
    {
        return sysPatientMapper.insertSysPatient(sysPatient);
    }

    /**
     * 修改患者
     * 
     * @param sysPatient 患者
     * @return 结果
     */
    @Override
    public int updateSysPatient(SysPatient sysPatient)
    {
        return sysPatientMapper.updateSysPatient(sysPatient);
    }

    /**
     * 批量删除患者
     * 
     * @param patientIds 需要删除的患者主键
     * @return 结果
     */
    @Override
    public int deleteSysPatientByPatientIds(Long[] patientIds)
    {
        return sysPatientMapper.deleteSysPatientByPatientIds(patientIds);
    }

    /**
     * 删除患者信息
     * 
     * @param patientId 患者主键
     * @return 结果
     */
    @Override
    public int deleteSysPatientByPatientId(Long patientId)
    {
        return sysPatientMapper.deleteSysPatientByPatientId(patientId);
    }
}
