package com.zhx.backstage.auth.filter;

import com.zhx.backstage.auth.mapper.AuthMapper;
import com.zhx.backstage.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * 一个自定义的service用来和数据库进行操作. 即以后我们要通过数据库保存权限.则需要我们继承UserDetailsService
 *
 * @author liukai
 */
public class AuthenticationManager implements UserDetailsService {

    //protected static Logger logger = Logger.getLogger("service");

    @Autowired
    AuthService authService;

    /**
     * 登录认证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        UserDetails user = null;
        try {
            Map adminUser = authService.getUserByUserName(username);
            int userID = Integer.valueOf(adminUser.get("userid").toString());
            user = new User(adminUser.get("username").toString(), adminUser.get("password").toString(),
                    true, true, true, true,
                    getAuthorities(userID)
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error in retrieving user");
        }

        return user;
    }

    /**
     * 获得访问角色权限
     *
     * @param userID
     * @return
     */
    public Collection<GrantedAuthority> getAuthorities(Integer userID) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //通过用户id查询用户所有角色
        List<Map> userRoles = authService.getRoleIdentByUserID(userID);
        //用此用户添加相应角色
        for(Map userRole:userRoles){
            authList.add(new GrantedAuthorityImpl(userRole.get("roleident").toString()));
        }
        return authList;
    }
}