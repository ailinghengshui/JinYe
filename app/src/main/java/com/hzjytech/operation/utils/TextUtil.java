package com.hzjytech.operation.utils;

import android.text.TextUtils;


public class TextUtil {

    /**
     * check params
     * if all params are not null return true
     * else return false
     * @param params
     * @return
     */
    public static boolean checkParams(String... params) {
        for(String param:params){
            if(TextUtils.isEmpty(param)){
                return false;
            }
        }
        return true;
    }
}
