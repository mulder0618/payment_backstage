package com.zhx.backstage.user.controller;

import com.zhx.backstage.user.service.UserService;
import com.zhx.base.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mulder on 2015/6/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 显示用户列表
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showuserlist")
    public ModelAndView showuser(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("user/userlist");
        return modelAndView;
    }


    /**
     * 显示添加用户页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showuseradd")
    public ModelAndView showuseradd(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("user/useradd");
        return modelAndView;
    }


    /**
     * 显示用户添加角色页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showbindroles")
    public ModelAndView showbindroles(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("user/userbindroles");
        return modelAndView;
    }


    /**
     * 添加用户
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map add(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "role", required = true) String role
    ) throws Exception {
        Map result = userService.addUser(userName,password,role);
        if("success".equals(result.get("result"))){
            //刷新权限信息

        }
        return result;
    }


    /**
     * 查询用户列表
     * @param userStatus
     * @param userCreateDate
     * @param userName
     * @param sort
     * @param order
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/loaduserlist")
    @ResponseBody
    public Map loaduserlist(
            @RequestParam(value = "userStatus", required = false) String userStatus,
            @RequestParam(value = "userCreateDate", required = false) String userCreateDate,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            Page page
    ) throws Exception {
        return userService.getUserList(page.getPage(), page.getRows(), userStatus, userCreateDate, userName, sort, order);
    }



}
