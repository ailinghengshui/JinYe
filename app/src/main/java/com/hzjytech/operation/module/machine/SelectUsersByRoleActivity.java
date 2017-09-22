package com.hzjytech.operation.module.machine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.SelectUserAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.AdminInfo;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnCompletedListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.utils.CommonUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by hehongcan on 2017/9/6.
 */

public class SelectUsersByRoleActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_select_users)
    RecyclerView mRvSelectUsers;
    private SelectUserAdapter selectUserAdapter;
    private int mRoleId;
    private ArrayList<Integer> mSelectUserIds;
    private int mMachineId;
    private JijiaHttpSubscriber mSubscriber;
    private String mRoleName;
    private boolean mStaffAuth;
    private TitleBar.TextAction mTextAction;
    private ArrayList<AdminInfo> mAdmins;
    //已经在其他角色中被选择过，就不要出现在选择picker中了
    private ArrayList<AdminInfo> selectAdmins;

    @Override
    protected int getResId() {
        return R.layout.activity_select_users;
    }

    @Override
    protected void initView() {
        initTilte();
        initRecycler();
        if(mStaffAuth){
            initData();
        }


    }

    private void initData() {
        Observable<AdminInfo> observable = AuthorizationApi.getAdminList(UserUtils
                .getUserInfo()
                .getToken(), mMachineId, mRoleId)
                .flatMap(new Func1<List<AdminInfo>, Observable<AdminInfo>>() {
                    @Override
                    public Observable<AdminInfo> call(List<AdminInfo> adminInfos) {
                        return Observable.from(adminInfos);
                    }
                }).filter(new Func1<AdminInfo, Boolean>() {
                    @Override
                    public Boolean call(AdminInfo adminInfo) {
                        for (AdminInfo selectAdmin : selectAdmins) {
                            if(selectAdmin.getAdminId()==adminInfo.getAdminId()){
                                return false;
                            }
                        }
                        return true;
                    }
                });
        final ArrayList<AdminInfo> list = new ArrayList<>();
        JijiaHttpSubscriber mSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<AdminInfo>() {
                    @Override
                    public void onNext(AdminInfo adminInfo) {
                        list.add(adminInfo);
                    }
                })
                .setOnCompletedListener(new SubscriberOnCompletedListener() {
                    @Override
                    public void onCompleted() {
                        selectUserAdapter.setData(list);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(mSubscriber);

    }

    private void initRecycler() {
        Intent intent = getIntent();
        mRoleId = intent.getIntExtra("roleId", -1);
        mMachineId = intent.getIntExtra("machineId", -1);
        mRoleName = intent.getStringExtra("roleName");
        mStaffAuth = intent.getBooleanExtra("staffAuth", false);
        mTitleBar.setTitle(mRoleName);
        if(!mStaffAuth){
            mTitleBar.removeAction(mTextAction);
        }
        mAdmins = intent.getParcelableArrayListExtra("admins");
        selectAdmins = intent.getParcelableArrayListExtra("selectAdmins");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSelectUsers.setLayoutManager(linearLayoutManager);
        selectUserAdapter = new SelectUserAdapter(this, mAdmins,mStaffAuth);
        mRvSelectUsers.setAdapter(selectUserAdapter);
    }

    private void initTilte() {
        final Intent intent = new Intent();
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTextAction = new TitleBar.TextAction("保存") {
            @Override
            public void performAction(View view) {
                List<AdminInfo> selectAdminResults = selectUserAdapter.getSelectAdmins();
                ArrayList<MachineInfo.StaffInfoBean> staffInfoBeen = new ArrayList<>();
                if(selectAdminResults==null){
                    finish();
                }
                for (AdminInfo selectAdmin : selectAdminResults) {
                    staffInfoBeen.add(new MachineInfo.StaffInfoBean(selectAdmin.getAdminId(),
                            mRoleId,
                            selectAdmin.getAdminName(),
                            mRoleName));
                }
                intent.putParcelableArrayListExtra("staff", staffInfoBeen);
                intent.putExtra("roleId", mRoleId);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        mTitleBar.addAction(mTextAction);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(mSubscriber);
    }
}
