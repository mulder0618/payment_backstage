package com.zhx.backstage.index.controller;

import com.zhx.backstage.index.service.IndexService;
import org.directwebremoting.annotations.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/index")
public class IndexController {


    @Autowired
    IndexService indexService;

    /**
     * 显示框架主页
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showindex")
    public ModelAndView showindex(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("index/index");
        return modelAndView;
    }


    /**
     * 显示左侧目录树
     * @return
     */
    @RequestMapping("/loadmanagertree")
    @ResponseBody
    public List loadmanagertree(
            String id
    ){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List treeList = new ArrayList();
        if(id==null){               //顶级菜单
            treeList =  indexService.getMenuTree("0");
        }
        else{                           //子菜单
            treeList = indexService.getMenuTree(id);
        }

        return treeList;
    }





}
