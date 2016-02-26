package com.zhx.base.util;


public class StringUtils {

    /**
     * 通过分隔符分割
     * @param objs
     * @param symbol
     * @return
     */
    public static String[] split(String objs, String symbol){
        String[] objArray = objs.split(symbol);
        return objArray;
    }


}

