package com.zhx.backstage.order.mapper;

import com.googlecode.ehcache.annotations.Cacheable;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    /**
     * 查询订单列表
     * @param param
     * @return
     */
    List<Map> selectOrderList(Map param);

    /**
     * 更新订单信息
     * @param paramMap
     * @return
     */
    int updatePaymentOrder(Map paramMap);

    /**
     * 查询网关订单
     * @param paramMap
     * @return
     */
    Map selectPaymentOrder(Map paramMap);

    /**
     * 查询商户信息表
     * @param paramMap
     * @return
     */
    Map selectPaymentMerchant(Map paramMap);

}