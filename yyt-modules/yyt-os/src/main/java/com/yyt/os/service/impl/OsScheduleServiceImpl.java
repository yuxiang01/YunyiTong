package com.yyt.os.service.impl;

import java.util.HashMap;
import java.util.List;
import com.yyt.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyt.os.mapper.OsScheduleMapper;
import com.yyt.os.domain.OsSchedule;
import com.yyt.os.service.IOsScheduleService;

/**
 * 排班Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class OsScheduleServiceImpl implements IOsScheduleService 
{
    @Autowired
    private OsScheduleMapper osScheduleMapper;

    /**
     * 查询排班
     * 
     * @param schedId 排班主键
     * @return 排班
     */
    @Override
    public OsSchedule selectOsScheduleBySchedId(Long schedId)
    {
        return osScheduleMapper.selectOsScheduleBySchedId(schedId);
    }

    /**
     * 查询排班列表
     * 
     * @param osSchedule 排班
     * @return 排班
     */
    @Override
    public List<OsSchedule> selectOsScheduleList(OsSchedule osSchedule)
    {
        return osScheduleMapper.selectOsScheduleList(osSchedule);
    }

    /**
     * 新增排班
     * 
     * @param osSchedule 排班
     * @return 结果
     */
    @Override
    public int insertOsSchedule(OsSchedule osSchedule)
    {
        osSchedule.setCreateTime(DateUtils.getNowDate());
        return osScheduleMapper.insertOsSchedule(osSchedule);
    }

    /**
     * 修改排班
     * 
     * @param osSchedule 排班
     * @return 结果
     */
    @Override
    public int updateOsSchedule(OsSchedule osSchedule)
    {
        osSchedule.setUpdateTime(DateUtils.getNowDate());
        return osScheduleMapper.updateOsSchedule(osSchedule);
    }

    /**
     * 批量删除排班
     * 
     * @param schedIds 需要删除的排班主键
     * @return 结果
     */
    @Override
    public int deleteOsScheduleBySchedIds(Long[] schedIds)
    {
        return osScheduleMapper.deleteOsScheduleBySchedIds(schedIds);
    }

    /**
     * 删除排班信息
     * 
     * @param schedId 排班主键
     * @return 结果
     */
    @Override
    public int deleteOsScheduleBySchedId(Long schedId)
    {
        return osScheduleMapper.deleteOsScheduleBySchedId(schedId);
    }

    /**
     * 根绝医生ID查询该医生的排班信息
     *
     * @param doctorId
     * @return 结果
     */
    @Override
    public List<HashMap> findScheduleInfoByDoctorId(String doctorId) {
        return osScheduleMapper.findScheduleInfoByDoctorId(doctorId);
    }
}
