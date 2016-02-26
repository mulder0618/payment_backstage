package com.zhx.backstage.role.mapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    /**
     * 查询角色
     * @param param
     * @return
     */
    Map selectRole(Map param);

    /**
     * 查询所有角色信息
     * @return
     */
    List<Map> selectAllRole();

    /**
     * 插入角色
     * @param param
     * @return
     */
    int insertRole(Map param);

    /**
     * 修改配置
     * @param param
     * @return
     */
    int updateRole(Map param);

    /**
     * 插入角色权限关系
     * @param param
     * @return
     */
    int insertRoleAuth(Map param);
}