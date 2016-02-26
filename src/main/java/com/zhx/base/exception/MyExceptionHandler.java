package com.zhx.base.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class MyExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex)
    {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        if(ex instanceof SignException) {
            return new ModelAndView("sign-error", model);
        }
        return new ModelAndView("error", model);
    }


}