package com.zhx.backstage.test.mapper;

import java.util.List;
import java.util.Map;

public interface TestMapper {

    int testCount();

    void testInsert(Map paramMap);

    List<Map> getTestList(Map paramMap);

}