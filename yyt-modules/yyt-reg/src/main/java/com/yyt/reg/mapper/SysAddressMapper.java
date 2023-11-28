package com.yyt.reg.mapper;

import java.util.HashMap;
import java.util.List;
import com.yyt.reg.domain.SysAddress;

/**
 * 地点Mapper接口
 * 
 * @author yyt
 * @date 2023-11-22
 */
public interface SysAddressMapper 
{
    /**
     * 查询地点
     * 
     * @param addressId 地点主键
     * @return 地点
     */
    public SysAddress selectSysAddressByAddressId(Long addressId);

    /**
     * 查询地点列表
     * 
     * @param sysAddress 地点
     * @return 地点集合
     */
    public List<SysAddress> selectSysAddressList(SysAddress sysAddress);

    /**
     * 新增地点
     * 
     * @param sysAddress 地点
     * @return 结果
     */
    public int insertSysAddress(SysAddress sysAddress);

    /**
     * 修改地点
     * 
     * @param sysAddress 地点
     * @return 结果
     */
    public int updateSysAddress(SysAddress sysAddress);

    /**
     * 删除地点
     * 
     * @param addressId 地点主键
     * @return 结果
     */
    public int deleteSysAddressByAddressId(Long addressId);

    /**
     * 批量删除地点
     * 
     * @param addressIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysAddressByAddressIds(Long[] addressIds);

    /**
     * 查询所有省份
     *
     * @return 结果
     */
    public List<HashMap> selProvince();

    /**
     * 查询该省份下所有城市
     *
     * @param id 需要删除的数据主键集合
     * @return 结果
     */
    public List<HashMap> selCityByProId(String id);
}
