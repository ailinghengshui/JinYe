package com.hzjytech.operation.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Hades on 2016/2/23.
 */
public class URLUtil {

    public static String encodeURLUTF8(String str){
        try {
            return URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
