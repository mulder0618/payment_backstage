package com.zhx.base.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by mulder on 2016/2/25.
 */
public class EncodingTool {

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
