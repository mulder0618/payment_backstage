package com.zhx.backstage.role.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.role.mapper.RoleMapper;
import com.zhx.backstage.user.mapper.UserMapper;
import com.zhx.base.service.BaseService;
import com.zhx.base.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 15/6/16.
 */
@Service("roleService")
public class RoleService extends BaseService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 查询角色列表
     * @param pageIndex
     * @param pageSize
     * @param roleStatus
     * @param createTime
     * @param roleName
     * @param sort
     * @param order
     * @return
     * @throws Exception
     */
    public Map getRoleList(
            int pageIndex,
            int pageSize,
            String roleStatus,
            String createTime,
            String roleName,
            String sort,
            String order)
            throws Exception
    {
        Map queryRole = new HashMap();
        if(roleStatus!=null&&!"".equals(roleStatus)){
            queryRole.put("roleStatus",roleStatus);
        }
        if(createTime!=null&&!"".equals(createTime)){
            queryRole.put("createTime",createTime);
        }
        if(roleName!=null&&!"".equals(roleName)){
            queryRole.put("roleName",roleName);
        }
        if(sort!=null){
            queryRole.put("sort",sort);
        }
        if(order!=null){
            queryRole.put("order",order);
        }
        PageList<Map> rolePagerList = (PageList<Map>) getPageList(RoleMapper.class, "selectRole", queryRole, pageIndex, pageSize);
        return packageEasyuiResultMap(rolePagerList);
    }


    /**
     * 添加角色
     * @param roleName
     * @param roleIdent
     * @return
     */
    public Map addRole(String roleName,String roleIdent,String selectedAuths){
        Map role = new HashMap();
        role.put("roleName",roleName);
        role.put("roleIdent",roleIdent);
        role.put("status",1);
        role.put("createDate",new Date());
        Map result = new HashMap();
        try {
            //插入角色表
            roleMapper.insertRole(role);

            //插入默认首页权限
            Map roleauthDefault = new HashMap();
            roleauthDefault.put("roleID",role.get("roleid"));
            roleauthDefault.put("authID","1");
            roleMapper.insertRoleAuth(roleauthDefault);
            //插入角色权限关系表
            String[] auths = selectedAuths.split(",");
            for(int i=0;i<auths.length;i++){
                Map roleauth = new HashMap();
                roleauth.put("roleID",role.get("roleid"));
                roleauth.put("authID",auths[i]);
                roleMapper.insertRoleAuth(roleauth);
            }
            result.put("result","success");
        }
        catch (Exception e){
            result.put("result","failed");
            result.put("error",e.getMessage());
        }
        return result;
    }




    /**
     * 查询所有角色信息
     * @return
     */
    public List<Map> getRoleList(){
        return roleMapper.selectAllRole();
    }
    /**
     * 修改配置
     * @param param
     * @return
     */
    public int updateRole(Map param){
        return roleMapper.updateRole(param);
    }



}
