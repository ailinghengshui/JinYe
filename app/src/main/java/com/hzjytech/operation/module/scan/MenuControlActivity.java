package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.scan.DividerGridItemDecoration;
import com.hzjytech.operation.adapters.scan.MenuControlAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.IConsBean;
import com.hzjytech.operation.entity.ScanAuthInfo;
import com.hzjytech.operation.entity.ScanInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.OnItemClickListener;
import com.hzjytech.operation.module.data.ErrorListActivity;
import com.hzjytech.operation.module.machine.DetailMachineActivity;
import com.hzjytech.operation.scan.activity.CaptureActivity;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.dialogs.IThreeButtonClick;
import com.hzjytech.operation.widgets.dialogs.ThreeChooseDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/6/5.
 */
public class MenuControlActivity extends BaseActivity {
    private static final int REQUEST_SCAN = 111;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_menu_control)
    RecyclerView mRv_menu_control;
    private String mResultString;
    private Integer mMachineId;
    private boolean isLogin;
    private ScanInfo info;
    private String token;
    private ArrayList<StickyHeadEntity<IConsBean>> mIConsBeen;
    private static final String TAG = "MenuControlActivity";
    private MenuControlAdapter mAdapter;
    private String machineType=null;
    //0:咖啡机登录 1：历史故障 2：咖啡机信息 3：巡检 4：浓度记录 5：签到 6：加料

    @Override
    protected int getResId() {
        return R.layout.activity_menu_control;
    }

    @Override
    protected void initView() {
        initTitle();
        initIcons();
        initClickListener();
        token = UserUtils.getUserInfo()
                .getToken();
        // TODO: 2017/8/22  测试用直接返回machineId
       /* mMachineId = 1;
        ScanAuthInfo scanAuthInfo=new ScanAuthInfo();
        scanAuthInfo.setConcentrationRole(true);
        scanAuthInfo.setInspectionRole(true);
        scanAuthInfo.setSignInRole(true);
        initAuth(scanAuthInfo);*/
        if (mResultString == null) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_SCAN);
        }
    }

    /**
     * 初始化icons点击事件
     */
    private void initClickListener() {
        mAdapter.setItemClickListener(new OnItemClickListener<IConsBean>() {
            @Override
            public void onItemClick(View view, IConsBean data, int position) {
                switch (data.getIcon()) {
                    case R.drawable.icon_login_in:
                        if (!isLogin) {
                            loginIn();
                        }
                        break;
                    case R.drawable.icon_error_history:
                        openErrorAcitivty();
                        break;
                    case R.drawable.icon_machine_info:
                        openMachineInfo();
                        break;
                    case R.drawable.icon_polling:
                        openPollingActivity();
                        break;
                    case R.drawable.icon_menu_density:
                        openDensityActivity();
                        break;
                    case R.drawable.icon_menu_sign_in:
                        openSignActivity();
                        break;
                    case R.drawable.icon_menu_dosing:
                        openDosingActivity();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 打开配料界面
     */
    private void openDosingActivity() {
        Intent intent = new Intent(this, DosingActivity.class);
        intent.putExtra("machineId", mMachineId);
        startActivity(intent);
    }

    /**
     * 根据弹窗选择签到类型
     */
    private void chooseMachineTypeDialog() {
        final Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("machineId", mMachineId);
        Resources r = getResources();
        final ThreeChooseDialog dialog = ThreeChooseDialog.newInstance(r.getString(R.string
                        .please_choose_machie_type),
                r.getString(R.string.cancel),   r.getString(R.string.normal),
                r.getString(R.string.water)
             );
        dialog.setOnThreeClcikListener(new IThreeButtonClick() {
            @Override
            public void leftClick() {
                dialog.dismiss();
            }

            @Override
            public void middleClick() {
                machineType=Constants.MachieType.SERIFLUX;
                intent.putExtra("machineType",machineType);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                dialog.dismiss();
            }

            @Override
            public void rightClick() {
                machineType= Constants.MachieType.NORMAL;
                intent.putExtra("machineType",machineType);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(),"machineType");
    }

    /**
     * 打开签到界面
     */
    private void openSignActivity() {
        if(machineType!=null){
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }else{
            chooseMachineTypeDialog();
        }
    }

    /**
     * 打开咖啡浓度界面
     */
    private void openDensityActivity() {
        Intent intent = new Intent(this, DensityActivity.class);
        intent.putExtra("machineId", mMachineId);
        startActivity(intent);
    }

    /**
     * 打开巡检界面
     */
    private void openPollingActivity() {
        Intent pollingIntent = new Intent(this, PollingActivity.class);
        pollingIntent.putExtra("machineId", mMachineId);
        startActivity(pollingIntent);
    }

    /**
     * 打开咖啡机详情
     */
    private void openMachineInfo() {
        Intent machineIntent = new Intent(this, DetailMachineActivity.class);
        machineIntent.putExtra("machineId", mMachineId);
        startActivity(machineIntent);
    }

    /**
     * 打开错误历史
     */
    private void openErrorAcitivty() {
        Intent intent = new Intent(this, ErrorListActivity.class);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(mMachineId);
        long startTime = 0;
        long endTime = System.currentTimeMillis();
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        intent.putIntegerArrayListExtra("machinesId", ids);
        startActivity(intent);
    }

    /**
     * 初始化界面
     */
    private void initIcons() {
        mIConsBeen = new ArrayList<>();
        List<String> nameList = Arrays.asList(getResources().getStringArray(R.array.menu_control));
        int[] imgs = {R.drawable.icon_login_in, R.drawable.icon_error_history, R.drawable
                .icon_machine_info, R.drawable.icon_polling, R.drawable.icon_menu_density, R
                .drawable.icon_menu_sign_in, R.drawable.icon_menu_dosing};
        Log.e(TAG, imgs.toString());
        for (int i = 0; i < nameList.size(); i++) {
            IConsBean bean = new IConsBean(nameList.get(i), imgs[i]);
            mIConsBeen.add(new StickyHeadEntity<IConsBean>(bean, 0, null));
        }
        mAdapter = new MenuControlAdapter(this);
        mAdapter.setData(mIConsBeen);
        mRv_menu_control.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                3,
                LinearLayoutManager.VERTICAL,
                false);
        mRv_menu_control.setLayoutManager(gridLayoutManager);
        mRv_menu_control.addItemDecoration(new DividerGridItemDecoration(this));
    }
    /**
     * 根据权限重新定义界面
     * @param scanAuthInfo
     */
    private void initAuth(ScanAuthInfo scanAuthInfo) {
        if(mIConsBeen==null){
            return;
        }
        //物料权限,经过讨论，先砍掉这个功能
        boolean recipeRole = scanAuthInfo.isRecipeRole();
        mIConsBeen.remove(6);
        //签到权限
        boolean signInRole = scanAuthInfo.isSignInRole();
        if(!signInRole){
            mIConsBeen.remove(5);
        }
        //浓度记录权限
        boolean concentrationRole = scanAuthInfo.isConcentrationRole();
        if(!concentrationRole){
            mIConsBeen.remove(4);
        }
        //巡检权限
        boolean inspectionRole = scanAuthInfo.isInspectionRole();
        if(!inspectionRole){
                mIConsBeen.remove(3);
        }

        mAdapter.setData(mIConsBeen);

    }
    /**
     * 设置标题栏
     */
    private void initTitle() {
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitle(R.string.menu_control);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            mResultString = bundle.getString(CaptureActivity.SCAN_RESULT_KEY);
            getMachineIdForEncodeString(mResultString);
        } else {
            finish();
        }
    }

    /**
     * 网络获取解码后的machineId
     *
     * @param resultString
     * @return
     */
    private void getMachineIdForEncodeString(String resultString) {
        Observable<ScanAuthInfo> observable = MachinesApi.getMachineIdByQRCode(token, resultString);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<ScanAuthInfo>() {
                    @Override
                    public void onNext(ScanAuthInfo scanAuthInfo) {
                        mMachineId = Integer.valueOf(scanAuthInfo.getMachineId());
                    /*    scanAuthInfo.setConcentrationRole(false);
                        scanAuthInfo.setInspectionRole(false);
                        scanAuthInfo.setSignInRole(false);*/
                        initAuth(scanAuthInfo);
                    }
                })
                .setOnErrorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {
                        Intent intent = new Intent(MenuControlActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_SCAN);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 登录
     */
    private void loginIn() {
        Observable<ScanInfo> observable = MachinesApi.loginMachine(token,
                mMachineId,
                true,
                mResultString,
                null);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<ScanInfo>() {
                    @Override
                    public void onNext(ScanInfo info) {
                        MenuControlActivity.this.info = info;
                        isLogin = true;
                        mIConsBeen.get(0)
                                .getData()
                                .setIcon(R.drawable.icon_login_out);
                        mIConsBeen.get(0)
                                .getData()
                                .setIconName(MenuControlActivity.this.getResources()
                                        .getString(R.string.login));
                        mAdapter.notifyItemChanged(0);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SignActivity.getInstanse().finish();
        super.onDestroy();
    }
}
