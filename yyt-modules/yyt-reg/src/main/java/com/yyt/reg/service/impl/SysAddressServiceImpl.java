package com.yyt.reg.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.yyt.reg.mapper.SysAddressMapper;
import com.yyt.reg.domain.SysAddress;
import com.yyt.reg.service.ISysAddressService;

import javax.annotation.Resource;

/**
 * 地点Service业务层处理
 *
 * @author yyt
 * @date 2023-11-22
 */
@Service
public class SysAddressServiceImpl implements ISysAddressService {
    @Autowired
    private SysAddressMapper sysAddressMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询地点
     *
     * @param addressId 地点主键
     * @return 地点
     */
    @Override
    public SysAddress selectSysAddressByAddressId(Long addressId) {
        return sysAddressMapper.selectSysAddressByAddressId(addressId);
    }

    /**
     * 查询地点列表
     *
     * @param sysAddress 地点
     * @return 地点
     */
    @Override
    public List<SysAddress> selectSysAddressList(SysAddress sysAddress) {
        return sysAddressMapper.selectSysAddressList(sysAddress);
    }

    /**
     * 新增地点
     *
     * @param sysAddress 地点
     * @return 结果
     */
    @Override
    public int insertSysAddress(SysAddress sysAddress) {
        return sysAddressMapper.insertSysAddress(sysAddress);
    }

    /**
     * 修改地点
     *
     * @param sysAddress 地点
     * @return 结果
     */
    @Override
    public int updateSysAddress(SysAddress sysAddress) {
        return sysAddressMapper.updateSysAddress(sysAddress);
    }

    /**
     * 批量删除地点
     *
     * @param addressIds 需要删除的地点主键
     * @return 结果
     */
    @Override
    public int deleteSysAddressByAddressIds(Long[] addressIds) {
        return sysAddressMapper.deleteSysAddressByAddressIds(addressIds);
    }

    /**
     * 删除地点信息
     *
     * @param addressId 地点主键
     * @return 结果
     */
    @Override
    public int deleteSysAddressByAddressId(Long addressId) {
        return sysAddressMapper.deleteSysAddressByAddressId(addressId);
    }

    /**
     * 查询所有省份
     *
     * @return 结果
     */
    @Override
    public List<HashMap> selProvince() {
        if (redisTemplate.opsForValue().get("province") != null) {
            return (List<HashMap>) redisTemplate.opsForValue().get("province");
        } else {
            List<HashMap> list = sysAddressMapper.selProvince();
            redisTemplate.opsForValue().set("province", list);
            return list;
        }
    }

    /**
     * 查询该省份下所有城市
     *
     * @param id 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public List<HashMap> selCityByProId(String id) {
        if (redisTemplate.opsForValue().get("city" + id) != null) {
            return (List<HashMap>) redisTemplate.opsForValue().get("city" + id);
        } else {
            List<HashMap> list = sysAddressMapper.selCityByProId(id);
            redisTemplate.opsForValue().set("city" + id, list);
            return list;
        }
    }
}
