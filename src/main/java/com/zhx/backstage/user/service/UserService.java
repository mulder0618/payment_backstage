package com.zhx.backstage.user.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zhx.backstage.index.mapper.IndexMapper;
import com.zhx.backstage.order.mapper.OrderMapper;
import com.zhx.backstage.user.mapper.UserMapper;
import com.zhx.base.service.BaseService;
import com.zhx.base.util.MD5Util;
import com.zhx.base.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by mulder on 15/6/16.
 */
@Service("userService")
public class UserService extends BaseService {

    @Autowired
    UserMapper userMapper;


    /**
     * 查询用户列表
     * @param pageIndex
     * @param pageSize
     * @param userStatus
     * @param createTime
     * @param userName
     * @param sort
     * @param order
     * @return
     * @throws Exception
     */
    public Map getUserList(
            int pageIndex,
            int pageSize,
            String userStatus,
            String createTime,
            String userName,
            String sort,
            String order)
            throws Exception
    {
        Map queryUser = new HashMap();
        if(userStatus!=null&&!"".equals(userStatus)){
            queryUser.put("userStatus",userStatus);
        }
        if(createTime!=null&&!"".equals(createTime)){
            queryUser.put("createTime",createTime);
        }
        if(userName!=null&&!"".equals(userName)){
            queryUser.put("userName",userName);
        }
        if(sort!=null){
            queryUser.put("sort",sort);
        }
        if(order!=null){
            queryUser.put("order",order);
        }
        PageList<Map> userPagerList = (PageList<Map>) getPageList(UserMapper.class, "selectUser", queryUser, pageIndex, pageSize);
        return packageEasyuiResultMap(userPagerList);
    }

    /**
     * 添加用户
     * @param userName
     * @param password
     * @return
     */
    @Transactional
    public Map addUser(String userName,String password,String role){
        Map user = new HashMap();
        user.put("userName",userName);
        user.put("password", MD5Util.endcodeMd5(password));
        user.put("status",1);
        user.put("createDate",new Date());
        Map result = new HashMap();
        try {
            userMapper.insertUser(user);
            //添加用户角色关系
            String[] roles = StringUtils.split(role,",");
            List<Map> rolesList = new ArrayList();
            for(String roleId : roles){
                Map userRole = new HashMap();
                userRole.put("userId",user.get("userid"));
                userRole.put("roleId",roleId);
                rolesList.add(userRole);
            }
            userMapper.insertUserRole(rolesList);
            result.put("result","success");
        }
        catch (Exception e){
            result.put("result","failed");
            result.put("error",e.getMessage());
        }
        return result;
    }




}
