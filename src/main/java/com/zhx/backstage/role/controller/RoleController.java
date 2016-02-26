package com.zhx.backstage.role.controller;

import com.zhx.backstage.auth.filter.MyInvocationSecurityMetadataSource;
import com.zhx.backstage.auth.service.AuthService;
import com.zhx.backstage.role.service.RoleService;
import com.zhx.base.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 2015/6/16.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;


    @Autowired
    AuthService authService;

    @Autowired
    MyInvocationSecurityMetadataSource myInvocationSecurityMetadataSource;


    /**
     * 显示角色列表
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showrolelist")
    public ModelAndView showroleslist(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("role/rolelist");
        return modelAndView;
    }

    /**
     * 显示添加角色
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showroleadd")
    public ModelAndView showroleadd(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("role/roleadd");
        return modelAndView;
    }


    /**
     * 查询角色列表
     * @param roleStatus
     * @param roleCreateDate
     * @param roleName
     * @param sort
     * @param order
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadrolelist")
    @ResponseBody
    public Map loadrolelist(
            @RequestParam(value = "roleStatus", required = false) String roleStatus,
            @RequestParam(value = "roleCreateDate", required = false) String roleCreateDate,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            Page page
    ) throws Exception {
        return roleService.getRoleList(page.getPage(), page.getRows(), roleStatus, roleCreateDate, roleName, sort, order);
    }


    /**
     * 添加角色
     * @param roleName
     * @param roleIdent
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(
            @RequestParam(value = "roleName", required = true) String roleName,
            @RequestParam(value = "roleIdent", required = true) String roleIdent,
            @RequestParam(value = "selectedAuths", required = true) String selectedAuths
    ) throws Exception {
        Map result =  roleService.addRole(roleName, roleIdent,selectedAuths);
        if("success".equals(result.get("result"))){
            //刷新权限角色关系
            myInvocationSecurityMetadataSource.setAuthService(authService);
            myInvocationSecurityMetadataSource.loadResourceDefine();
        }
        return  result;
    }



    /**
     * 查询所有角色
     * @return
     * @throws Exception
     */
    @RequestMapping("/getallrole")
    @ResponseBody
    public List<Map> getallrole(
    ) throws Exception {
        return roleService.getRoleList();
    }

    /**
     * 修改配置
     * @param
     * @return
     */
    @RequestMapping("/updateStat")
    @ResponseBody
    public int updateStat(
            @RequestParam(value = "status",required = true)String status,
            @RequestParam(value = "id",required = true)String id
    )throws Exception {
        Map param=new HashMap();
        param.put("id",id);
        param.put("status","1".equals(status)?"0":"1");
        return roleService.updateRole(param);
    }




}
