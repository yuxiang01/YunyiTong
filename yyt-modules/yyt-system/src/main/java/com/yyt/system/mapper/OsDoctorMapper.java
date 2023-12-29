package com.yyt.system.mapper;

import java.util.HashMap;
import java.util.List;

import com.yyt.system.domain.OsDoctor;

/**
 * 医生Mapper接口
 *
 * @author yyt
 * @date 2023-11-23
 */
public interface OsDoctorMapper {
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
   * 删除医生
   *
   * @param doctorId 医生主键
   * @return 结果
   */
  public int deleteOsDoctorByDoctorId(Long doctorId);

  /**
   * 批量删除医生
   *
   * @param doctorIds 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteOsDoctorByDoctorIds(Long[] doctorIds);

  /**
   * 根据部门ID查询医生
   *
   * @param deptId 部门ID
   * @return 结果
   */
  public List<HashMap> findDoctorByDeptId(String deptId);

  /**
   * 根据医生ID查询医生
   *
   * @param doctorId 部门ID
   * @return 结果
   */
  public HashMap findDoctorByDoctorId(String doctorId);
}
