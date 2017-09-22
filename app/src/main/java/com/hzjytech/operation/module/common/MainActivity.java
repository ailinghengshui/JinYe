package com.hzjytech.operation.module.common;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.MainPageAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.UpdateInfo;

import com.hzjytech.operation.http.DownloadManagerPro;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.module.scan.MenuControlActivity;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.utils.SharedPrefUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.BadgeView;
import com.hzjytech.operation.widgets.dialogs.ForceUpdateDialog;
import com.hzjytech.operation.widgets.dialogs.ITwoButtonClick;
import com.hzjytech.operation.widgets.dialogs.UpdateDialog;
import com.umeng.analytics.MobclickAgent;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import hehongcan.fitnlib.FileProvider7;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by hehongcan on 2017/4/6.
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 2;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;
    private MainPageAdapter adapter;
    private BadgeView badgeView;
    private long mClickExitTime = 0;
    private DownloadManager downloadManager;
    private DownloadManagerPro downloadManagerPro;
    private CompleteReceiver completeReceiver;
    public static final String KEY_IGNORE_VERSION = "key_ignore_version";
    private static final String DOWNLOAD_FOLDER_NAME = "Operation";
    private static final String DOWNLOAD_FILE_NAME = "Operation.apk";
    public static final String KEY_NAME_DOWNLOAD_ID = "downloadId";
    private long downloadId = 0;
    private Subscription mSubscribe;
    private Subscription mSubscribe1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mainTablayout.setTabMode(TabLayout.MODE_FIXED);
        adapter = new MainPageAdapter(getSupportFragmentManager());
        intTab();
        initTaskCount();
        initVersionUpdate();
        initIntent();
    }

    /**
     * 推送跳转
     */
    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle =intent.getBundleExtra("flag");
        if(bundle!=null){
            String flag = bundle.getString("flag");
            if(flag!=null&&flag.equals("task")){
                mainTablayout.getTabAt(1).select();
            }
        }



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initIntent();
    }

    /**
     * 检测升级
     */
    public void initVersionUpdate() {
        initDownloadConfig();
        checkAppUpdate();
    }

    /**
     * 初始化底部naviTab
     */
    private void intTab() {
        mainTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = (Fragment) adapter.instantiateItem(rlContainer, position);
                adapter.setPrimaryItem(rlContainer,position,fragment);
                adapter.finishUpdate(rlContainer);
                View view = tab.getCustomView();
                ImageView iv_check = (ImageView) view.findViewById(R.id.iv_check);
                TextView tv_check = (TextView) view.findViewById(R.id.tv_check);
            /*    iv_check.setSelected(true);
                tv_check.setSelected(true);*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i=0;i<5;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.tab_main_layout, null);
            ImageView iv_check = (ImageView) view.findViewById(R.id.iv_check);
            TextView tv_check = (TextView) view.findViewById(R.id.tv_check);
            if(i==0){
                iv_check.setImageResource(R.drawable.selector_main_home);
                tv_check.setText("首页");
            }
            if(i==1){
                iv_check.setImageResource(R.drawable.selector_main_task);
                tv_check.setText("任务");
                badgeView = new BadgeView(this, iv_check);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                badgeView.setText("9");
                //badgeView.show();
            }
            if(i==2){
                iv_check.setImageResource(R.drawable.selector_main_data);
                tv_check.setText("数据");
            }
            if(i==3){
                iv_check.setImageResource(R.drawable.selector_main_machine);
                tv_check.setText("咖啡机");
            }
            if(i==4){
                iv_check.setImageResource(R.drawable.selector_main_me);
                tv_check.setText("我");
            }
            TabLayout.Tab tab = mainTablayout.newTab();
            tab.setCustomView(view);
            //选择第一个
            mainTablayout.addTab(tab,i==0?true:false);
        }
    }

    /**
     * 初始化任务数量，设置badage
     */
    private void initTaskCount() {

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            showQuitAlert();
            return true;
        }
        return false;
    }


    public void showQuitAlert() {
        long clickTime = System.currentTimeMillis();
        if (mClickExitTime != 0 && clickTime - mClickExitTime < 2000) {
            finish();
        } else {
            mClickExitTime = clickTime;
            Toast.makeText(this, "再按一次退出锦业运维", Toast.LENGTH_SHORT).show();
        }
    }
    //更新app

    public  void checkAppUpdate() {
        Observable<UpdateInfo> observable = AuthorizationApi.getAppInfo(UserUtils.getUserInfo()
                .getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<UpdateInfo>() {
                    @Override
                    public void onNext(UpdateInfo updateInfo) {
                     isNeedUpdate(updateInfo);
                    }
                })
                .build();
        observable.subscribe(subscriber);
    }
    private void isNeedUpdate(final UpdateInfo updateInfo) {
        if ( Integer.valueOf(updateInfo.getVersionCode()) > AppUtil.getVersionCode(MainActivity.this)&&updateInfo.isForceUpgrade()) {
            final ForceUpdateDialog dialogFragment = ForceUpdateDialog.newInstance("新版本更新", updateInfo.getChangeLog(), "退出", "更新");
            dialogFragment.setOnTwoClickListener(new ITwoButtonClick() {
                @Override
                public void onLeftButtonClick() {
                    android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                    System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                    //finish();
                }
                @Override
                public void onRightButtonClick() {
                    MPermissions.requestPermissions(MainActivity.this,5, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
                    //downLoadApk(appVersion.getDownload_url());
                    mSubscribe1 = RxBus.getDefault()
                            .toObservable()
                            .subscribe(new Action1<Object>() {
                                @Override
                                public void call(Object o) {
                                    if (o instanceof Boolean) {
                                        if ((Boolean) o) {
                                            downLoadApkWithProgress(updateInfo.getApkUrl(),
                                                    dialogFragment);
                                        } else {
                                            System.exit(0);
                                        }
                                    }
                                }
                            });


                    //finish();
                }
            });
            dialogFragment.show(getSupportFragmentManager(), "forceUpdate");
        } else {
            if (Integer.valueOf(updateInfo.getVersionCode()) > SharedPrefUtil.getInt(KEY_IGNORE_VERSION) && Integer.valueOf(updateInfo.getVersionCode()) > AppUtil.getVersionCode(MainActivity.this)) {
                final UpdateDialog updateDialog = UpdateDialog.newInstance("新版本更新", updateInfo.getChangeLog());
                updateDialog.setOnTwoClickListener(new ITwoButtonClick() {
                    @Override
                    public void onLeftButtonClick() {
                        SharedPrefUtil.putInt(KEY_IGNORE_VERSION, Integer.valueOf(updateInfo.getVersionCode()));
                    }

                    @Override
                    public void onRightButtonClick() {
                        MPermissions.requestPermissions(MainActivity.this,5, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
                        mSubscribe = RxBus.getDefault()
                                .toObservable()
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        if (o instanceof Boolean) {
                                            if ((Boolean) o) {
                                                downLoadApk(updateInfo.getApkUrl(), updateDialog);
                                            } else {
                                                updateDialog.dismiss();
                                            }
                                        }
                                    }
                                });

                    }
                });
                updateDialog.show(getSupportFragmentManager(), "updateDialog");
            }
        }
    }


    public void downLoadApkWithProgress(final String downloadUrl, final ForceUpdateDialog dialogFragment){

        dialogFragment.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Xutils3联网获取数据
                RequestParams param  = new RequestParams(downloadUrl);
                param.setSaveFilePath(Environment.getExternalStorageDirectory()+ File.separator+DOWNLOAD_FOLDER_NAME+File.separator+DOWNLOAD_FILE_NAME);
                x.http().get(param, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File file) {

                        Log.d("TAG",file.toString());
                        //down();
                        String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append(DOWNLOAD_FOLDER_NAME).append(File.separator)
                                .append(DOWNLOAD_FILE_NAME).toString();
                        installApk(getApplicationContext(), apkFilePath);
                        dialogFragment.dismiss();
                       MainActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        dialogFragment.setProgress(Math.round(current*1000f/total)/10f);

                    }
                });




            }
        }).start();
    }
    public void downLoadApk(final String downloadUrl, final UpdateDialog updateDialog){
        updateDialog.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Xutils3联网获取数据
                RequestParams param  = new RequestParams(downloadUrl);
                param.setSaveFilePath(Environment.getExternalStorageDirectory()+File.separator+DOWNLOAD_FOLDER_NAME+File.separator+DOWNLOAD_FILE_NAME);
                x.http().get(param, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File file) {

                        Log.d("TAG",file.toString());
                        //down();
                        String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append(DOWNLOAD_FOLDER_NAME).append(File.separator)
                                .append(DOWNLOAD_FILE_NAME).toString();
                        installApk(getApplicationContext(), apkFilePath);
                        updateDialog.dismiss();
                        MainActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        updateDialog.setProgress(Math.round(current*1000f/total)/10f);

                    }
                });




            }
        }).start();
    }

    public void initDownloadConfig() {
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadManagerPro = new DownloadManagerPro(downloadManager);
        completeReceiver = new CompleteReceiver();
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(completeReceiver);
        if(mSubscribe!=null&&mSubscribe1!=null){
            mSubscribe.unsubscribe();
            mSubscribe1.unsubscribe();
        }


    }

    class CompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (completeDownloadId == downloadId) {
                if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
                    String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                            .append(File.separator).append(DOWNLOAD_FOLDER_NAME).append(File.separator)
                            .append(DOWNLOAD_FILE_NAME).toString();
                   installApk(context, apkFilePath);
                }
            }
        }
    }
    public boolean installApk(Context context,String filePath){
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(this,intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
        return true;
    }

    public boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            String packageName = getApplicationInfo().packageName;
            Uri apkUri = FileProvider.getUriForFile(context, packageName+".provider", file);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);
            return true;
        }else{
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                return true;
        }}

        return false;
    }
    @PermissionGrant(4)
    public void requestCameraSuccess()
    {
        Intent intent = new Intent(this, MenuControlActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(4)
    public void requestCameraFailed()
    {
        Toast.makeText(this, "您拒绝了此权限", Toast.LENGTH_SHORT).show();
    }

    @PermissionGrant(5)
    public void requestStorageSuccess()
    {
        RxBus.getDefault().send(true);
        //Toast.makeText(this, "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(5)
    public void requestStorageFailed()
    {
        Toast.makeText(this, "您拒绝了此权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
