package com.hzjytech.qiniutoken.utils;

import java.nio.charset.Charset;

/**
 * Created by Hades on 2016/2/24.
 */
public class StringUtils {
    public static byte[] utf8Bytes(String data) {
        return data.getBytes(Charset.forName("UTF-8"));

    }

    public static boolean inStringArray(String s, String[] array) {
        for(String x:array){
            if(x.equals(s)){
                return true;
            }
        }
        return false;
    }
}
