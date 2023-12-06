package com.yyt.os.mapper;

import java.util.List;
import com.yyt.os.domain.SysPatient;

/**
 * 患者Mapper接口
 * 
 * @author yyt
 * @date 2023-11-30
 */
public interface SysPatientMapper 
{
    /**
     * 查询患者
     * 
     * @param patientId 患者主键
     * @return 患者
     */
    public SysPatient selectSysPatientByPatientId(Long patientId);

    /**
     * 查询患者列表
     * 
     * @param sysPatient 患者
     * @return 患者集合
     */
    public List<SysPatient> selectSysPatientList(SysPatient sysPatient);

    /**
     * 新增患者
     * 
     * @param sysPatient 患者
     * @return 结果
     */
    public int insertSysPatient(SysPatient sysPatient);

    /**
     * 修改患者
     * 
     * @param sysPatient 患者
     * @return 结果
     */
    public int updateSysPatient(SysPatient sysPatient);

    /**
     * 删除患者
     * 
     * @param patientId 患者主键
     * @return 结果
     */
    public int deleteSysPatientByPatientId(Long patientId);

    /**
     * 批量删除患者
     * 
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPatientByPatientIds(Long[] patientIds);
}
