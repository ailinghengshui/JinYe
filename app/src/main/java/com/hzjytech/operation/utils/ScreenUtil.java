package com.hzjytech.operation.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Hades on 2016/3/16.
 */
public class ScreenUtil {

    private ScreenUtil(){
        throw new UnsupportedOperationException(ScreenUtil.class.getSimpleName()+"cannot be instantiated");
    }

    public static int getScreenWidth(Context context){
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context){
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * return statusbar height
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context){
        int statusHeight=-1;
        try {
            Class<?> clazz= Class.forName("com.android.internal.R$dimen");
            Object object=clazz.newInstance();
            int height= Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight=context.getResources().getDimensionPixelOffset(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static Bitmap snapShotWithStatusBar(Activity activity){
        View view=activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap=view.getDrawingCache();
        int width=getScreenWidth(activity);
        int height=getScreenHeight(activity);

        Bitmap bp;
        bp= Bitmap.createBitmap(bitmap,0,0,width,height);
        view.destroyDrawingCache();
        return bp;
    }

    public static Bitmap snapShotWithoutStatusBar(Activity activity){
        View view=activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp=view.getDrawingCache();
        Rect frame=new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight=frame.top;

        int width=getScreenWidth(activity);
        int height=getScreenHeight(activity);
        Bitmap bp=null;
        bp= Bitmap.createBitmap(bmp,0,statusBarHeight,width,height-statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
