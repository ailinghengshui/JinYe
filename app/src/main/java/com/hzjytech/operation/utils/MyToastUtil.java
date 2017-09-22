package com.hzjytech.operation.utils;

import android.content.Context;

/**
 * Created by hehongcan on 2017/5/8.
 */
public class MyToastUtil {
    private MyToastUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static MiuiToast toast;

    public static void show(Context context, String msg) {
        if(msg==null||msg==""){
            return;
        }
        toast = MiuiToast.makeText(context,msg,false);
        toast.show();
    }
    public static void show(Context context, int res) {

        toast = MiuiToast.makeText(context,context.getString(res),false);
        toast.show();
    }
}
