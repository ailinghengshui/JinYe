package com.hzjytech.operation.utils;

import android.util.Log;


/**
 * Created by Hades on 2016/1/26.
 */
public class LogUtil {

    public static boolean isDebug=true;
    private LogUtil(){
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
    public static void i(String tag, String msg){
        if(isDebug){
            Log.i(tag,msg);
        }
    }

    public static void d(String tag, String msg) {

        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(isDebug){
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(isDebug){
            Log.v(tag,msg);
        }
    }

    public static void w(String tag, String s) {
        if(isDebug){
            Log.w(tag,s);
        }
    }
}
