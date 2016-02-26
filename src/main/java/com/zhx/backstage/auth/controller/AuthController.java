package com.zhx.backstage.auth.controller;

import com.zhx.backstage.auth.service.AuthService;
import com.zhx.base.util.EncodingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by mulder on 15/12/1.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthService authService;

    /**
     * 显示登录页
     * @param modelAndView
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }


    /**
     * 显示菜单管理界面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/showmenulist")
    public ModelAndView showauthlist(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("menu/menulist");
        return modelAndView;
    }

    /**
     * 添加菜单
     * @param menuPID
     * @param menuNameInput
     * @param menuUrlInput
     * @return
     * @throws Exception
     */
    @RequestMapping("/addmenu")
    @ResponseBody
    public Map addMenu(
            @RequestParam(value = "menuPID", required = true) String menuPID,
            @RequestParam(value = "menuNameInput", required = true) String menuNameInput,
            @RequestParam(value = "menuUrlInput", required = true) String menuUrlInput
    ) throws Exception {
        return authService.setMenu(EncodingTool.encodeStr(menuNameInput),menuUrlInput,menuPID);
    }




}
