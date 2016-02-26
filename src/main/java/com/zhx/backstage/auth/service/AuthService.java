package com.zhx.backstage.auth.service;

import com.zhx.backstage.auth.mapper.AuthMapper;
import com.zhx.backstage.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 一个自定义的service用来和数据库进行操作. 即以后我们要通过数据库保存权限.则需要我们继承UserDetailsService
 *
 * @author liukai
 */
@Service("authService")
public class AuthService{

    //protected static Logger logger = Logger.getLogger("service");

    @Autowired
    AuthMapper authMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    public Map getUserByUserName(String username){
        Map queryUser = new HashMap();
        queryUser.put("userName",username);
        return userMapper.selectUser(queryUser);
    }

    /**
     * 查询所有权限信息
     * @return
     */
    public List<Map> getAllAuth(){
        return authMapper.selectAllAuth();
    }

    /**
     * 通过权限ID查询所有对应角色
     * @param authID
     * @return
     */
    public List<Map> getRoleIdentByAuthID(int authID){
        return authMapper.selectRoleIdentByAuthID(authID);
    }


    /**
     * 通过用户ID查询所有对应角色
     * @param userID
     * @return
     */
    public List<Map> getRoleIdentByUserID(int userID){
        return authMapper.selectRoleIdentByUserID(userID);
    }

    /**
     * 插入菜单信息
     * @param authName
     * @param authUrl
     * @param pID
     * @return
     */
    public Map setMenu(String authName,String authUrl,String pID){
        Map result = new HashMap();
        Map menuParam = new HashMap();
        menuParam.put("authName",authName);
        menuParam.put("authUrl",authUrl);
        menuParam.put("status","1");
        menuParam.put("createDate",new Date());
        menuParam.put("pID",pID);
        try{
            authMapper.insertMenu(menuParam);
        }
        catch (Exception e){
            result.put("code","999");
            result.put("msg",e.getMessage());
            return result;
        }
        result.put("code","000");
        return result;
    }


}