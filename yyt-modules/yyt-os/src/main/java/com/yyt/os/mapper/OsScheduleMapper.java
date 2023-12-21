package com.yyt.os.mapper;

import java.util.HashMap;
import java.util.List;
import com.yyt.os.domain.OsSchedule;

/**
 * 排班Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface OsScheduleMapper 
{
    /**
     * 查询排班
     * 
     * @param schedId 排班主键
     * @return 排班
     */
    public OsSchedule selectOsScheduleBySchedId(Long schedId);

    /**
     * 查询排班列表
     * 
     * @param osSchedule 排班
     * @return 排班集合
     */
    public List<OsSchedule> selectOsScheduleList(OsSchedule osSchedule);

    /**
     * 新增排班
     * 
     * @param osSchedule 排班
     * @return 结果
     */
    public int insertOsSchedule(OsSchedule osSchedule);

    /**
     * 修改排班
     * 
     * @param osSchedule 排班
     * @return 结果
     */
    public int updateOsSchedule(OsSchedule osSchedule);

    /**
     * 删除排班
     * 
     * @param schedId 排班主键
     * @return 结果
     */
    public int deleteOsScheduleBySchedId(Long schedId);

    /**
     * 批量删除排班
     * 
     * @param schedIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsScheduleBySchedIds(Long[] schedIds);

    /**
     * 根据医生ID查询该医生排班信息
     *
     * @param doctorId
     * @return 结果
     */
    public List<HashMap> findScheduleInfoByDoctorId(String doctorId);
}
