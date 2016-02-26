package com.zhx.backstage.auth.filter;

import com.zhx.backstage.auth.mapper.AuthMapper;
import com.zhx.backstage.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 *
 * @author user
 */
@Component("myInvocationSecurityMetadataSource")
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

/*
    public MyInvocationSecurityMetadataSource() {
        loadResourceDefine();
    }
*/

    /**
     * 系统初始化时调用读取权限信息
     */
    public void loadResourceDefine() {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        //查询所有资源列表
        List<Map> authList = authService.getAllAuth();
        //遍历资源列表
        for(Map auth : authList){
            int authID = Integer.valueOf(auth.get("authid").toString());
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            //查询当前权限所有的角色信息
            List<Map> roleList = authService.getRoleIdentByAuthID(authID);
            for(Map role : roleList){
                ConfigAttribute ca = new SecurityConfig(role.get("roleident").toString());
                atts.add(ca);
            }
            //将当前权限所对应的所有角色加入配置
            resourceMap.put(auth.get("authurl").toString(), atts);
        }
        System.out.println(resourceMap);
    }


    /**
     * 每次访问一个URL此方法判断权限
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    //  According to a URL, Find out permission configuration of this URL.
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        //  guess object is a URL.
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(url, resURL)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}