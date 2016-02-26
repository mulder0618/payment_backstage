package com.zhx.backstage.user.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 查询用户
     * @param param
     * @return
     */
    Map selectUser(Map param);


    /**
     * 添加用户
     * @param param
     * @return
     */
    int insertUser(Map param);


    /**
     * 添加用户角色关系
     * @param param
     * @return
     */
    int insertUserRole(List param);


}