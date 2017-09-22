package com.hzjytech.operation.module.common;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.hzjytech.operation.utils.SharedPrefUtil;
import com.hzjytech.operation.utils.UserUtils;

import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Hades on 2016/1/28.
 */
public class MyApplication extends Application {

    public static final boolean IS_DEBUG =false ;
    private static MyApplication mInstance;

    // public BaiduLocationService baiduLocationService;
    //用于控制dialog弹出顺序
    protected boolean hasShowDialog=false;
    public static MyApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {

        super.onCreate();
        mInstance=this;
        SharedPrefUtil.load(this);
        UserUtils.init(this);
        x.Ext.init(this);
       // initImageLoader(getApplicationContext());

       // baiduLocationService=new BaiduLocationService(getApplicationContext());
       // SDKInitializer.initialize(getApplicationContext());


        initJPush();
    }


    @Override
        protected void attachBaseContext(Context context) {
            super.attachBaseContext(context);
            //Avoiding the 64K Limit
            MultiDex.install(this);
        }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return super.getCacheDir();
    }
}
