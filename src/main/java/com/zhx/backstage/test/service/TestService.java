package com.zhx.backstage.test.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.test.mapper.TestMapper;
import com.zhx.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mulder on 15/6/16.
 */
@Service("testService")
public class TestService extends BaseService {

    @Autowired
    TestMapper testMapper;



    public int testCount(){
        return  testMapper.testCount();
    }


    public void testInsert(
            String title
    ){
        Map paramMap = new HashMap();
        paramMap.put("title",title);
        testMapper.testInsert(paramMap);
    }


    public PageList<Map> getTestList(
            int pageIndex,
            int pageSize
    ) throws Exception {
        Map paramMap = new HashMap();
        paramMap.put("title", "123");
        PageList<Map> orderPagerList = (PageList<Map>) getPageList(TestMapper.class, "getTestList", paramMap, pageIndex, pageSize);
        return orderPagerList;
    }



    public static  void main(String[] args){

    }


}
