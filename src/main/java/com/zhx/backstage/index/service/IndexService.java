package com.zhx.backstage.index.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.auth.mapper.AuthMapper;
import com.zhx.backstage.index.mapper.IndexMapper;
import com.zhx.backstage.test.mapper.TestMapper;
import com.zhx.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 15/6/16.
 */
@Service("indexService")
public class IndexService extends BaseService {

    @Autowired
    IndexMapper indexMapper;

    @Autowired
    AuthMapper authMapper;


    /**
     * 查询菜单项
     * @param pID
     * @return
     */
    public List<Map> getMenuTree(String pID){
        return authMapper.selectMenuByPid(pID);
    }

    public static  void main(String[] args){

    }


}
