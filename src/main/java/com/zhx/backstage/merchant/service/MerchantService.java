package com.zhx.backstage.merchant.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.merchant.mapper.MerchantMapper;
import com.zhx.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 15/6/16.
 */
@Service("MerchantService")
public class MerchantService extends BaseService {

    @Autowired
    MerchantMapper merchantMapper;


    /**
     * 查询列表
     * @param pageIndex  当前页码
     * @param pageSize     分页条数
     * @return
     * @throws Exception
     */
    public Map getMerchantList( Map query, int pageIndex,int pageSize) throws Exception {

        PageList<Map> userPagerList = (PageList<Map>) getPageList(MerchantMapper.class, "selectMerchant", query, pageIndex, pageSize);
        return packageEasyuiResultMap(userPagerList);
    }

    /**
     * 更新状态
     * @param param
     * @return
     */
    public int updateMerchant(Map param){
        return merchantMapper.updateMerchant(param);
    }

    /**
     * 新增信息
     * @param param
     * @return
     */
    public int insertMerchant(Map param){
        return merchantMapper.insertMerchant(param);
    }
}
