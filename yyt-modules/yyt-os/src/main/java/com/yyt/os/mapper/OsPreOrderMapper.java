package com.yyt.os.mapper;

import java.util.List;
import com.yyt.os.domain.OsPreOrder;
import com.yyt.os.domain.OsPrescription;

/**
 * 处方订单Mapper接口
 * 
 * @author yyt
 * @date 2023-12-18
 */
public interface OsPreOrderMapper 
{
    /**
     * 查询处方订单
     * 
     * @param preCode 处方订单主键
     * @return 处方订单
     */
    public OsPreOrder selectOsPreOrderByPreCode(String preCode);

    /**
     * 查询处方订单列表
     * 
     * @param osPreOrder 处方订单
     * @return 处方订单集合
     */
    public List<OsPreOrder> selectOsPreOrderList(OsPreOrder osPreOrder);

    /**
     * 新增处方订单
     * 
     * @param osPreOrder 处方订单
     * @return 结果
     */
    public int insertOsPreOrder(OsPreOrder osPreOrder);

    /**
     * 修改处方订单
     * 
     * @param osPreOrder 处方订单
     * @return 结果
     */
    public int updateOsPreOrder(OsPreOrder osPreOrder);

    /**
     * 删除处方订单
     * 
     * @param preCode 处方订单主键
     * @return 结果
     */
    public int deleteOsPreOrderByPreCode(String preCode);

    /**
     * 批量删除处方订单
     * 
     * @param preCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsPreOrderByPreCodes(String[] preCodes);

    /**
     * 批量删除处方
     * 
     * @param preCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOsPrescriptionByPreCodes(String[] preCodes);
    
    /**
     * 批量新增处方
     * 
     * @param osPrescriptionList 处方列表
     * @return 结果
     */
    public int batchOsPrescription(List<OsPrescription> osPrescriptionList);
    

    /**
     * 通过处方订单主键删除处方信息
     * 
     * @param preCode 处方订单ID
     * @return 结果
     */
    public int deleteOsPrescriptionByPreCode(String preCode);
}
