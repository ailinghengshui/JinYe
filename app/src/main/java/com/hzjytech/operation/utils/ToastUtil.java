package com.hzjytech.operation.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by Hades on 2016/1/26.
 */
public class ToastUtil {

    private ToastUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static Toast toast;

    public static void showShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    public static void showShort(Context context, int res) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getString(res), Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getString(res));
        }
        toast.show();
    }

    public static void showLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    public static void showLong(Context context,int res) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getString(res), Toast.LENGTH_LONG);
        } else {
            toast.setText( context.getString(res));
        }
        toast.show();
    }

}
