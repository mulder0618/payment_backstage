package com.zhx.backstage.merchant.mapper;

import java.util.List;
import java.util.Map;

public interface MerchantMapper {

    /**
     * 查询订单列表
     * @param param
     * @return
     */
    List<Map> selectMerchant(Map param);

    /**
     * 更新信息
     * @param paramMap
     * @return
     */
    int updateMerchant(Map paramMap);

    /**
     * 新增
     * @param paramMap
     * @return
     */
    int insertMerchant(Map paramMap);



}