package com.zhx.backstage.auth.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuthMapper {



    /**
     * 查询用户列表
     * @param param
     * @return
     */
    List<Map> selectAllUser(Map param);


    /**
     * 查询权限列表
     * @return
     */
    List<Map> selectAllAuth();


    /**
     * 通过权限ID查询角色
     * @return
     */
    List<Map> selectRoleIdentByAuthID(int authID);


    /**
     * 通过用户ID查询角色
     * @param userID
     * @return
     */
    List<Map> selectRoleIdentByUserID(int userID);


    /**
     * 通过pid查询菜单项
     * @param pID
     * @return
     */
    List<Map> selectMenuByPid(String pID);


    /**
     * 插入菜单(权限)信息
     * @param param
     * @return
     */
    int insertMenu(Map param);


}