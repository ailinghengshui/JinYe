package com.hzjytech.operation.module.frags.machine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.MoreMachineAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.module.menu.MoreMachineActivity;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/9/21.
 */

public class FirstMachineFragment  extends BaseFragment{
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_more_machine)
    RecyclerView rvMoreMachine;
    @BindView(R.id.pcfl_more_machine)
    PtrClassicFrameLayout pcflMoreMachine;
    private int groupId;
    private List<GroupInfo.SubMachinesBean> mSubMachines;
    private MoreMachineAdapter machineAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private PtrHandler ptrDefaultHandler=new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();
        }
    };
    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            initData();
        }
    };
    private int menuId;
    private int id;
    private FragmentActivity mContext;

    @Override
    protected int getResId() {
        return R.layout.activity_more_machine;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();
    }

    /**
     * 初始化下拉刷新、上拉加载更多
     */
    private void initPtrc() {
        pcflMoreMachine.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflMoreMachine.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflMoreMachine.setLoadMoreEnable(false);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMoreMachine.setLayoutManager(linearLayoutManager);
        machineAdapter = new MoreMachineAdapter(mContext, null);
        mAdapter = new RecyclerAdapterWithHF(machineAdapter);
        rvMoreMachine.setAdapter(mAdapter);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = getActivity();
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getActivity().getIntent();
        groupId = intent.getIntExtra("groupId", -1);
        menuId=intent.getIntExtra("menuId",-1);
        if(groupId!=-1){
            Observable<GroupInfo> observable = MachinesApi.getGroupInfo(userInfo.getToken(), groupId);
            JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mContext).setOnNextListener(new SubscriberOnNextListener<GroupInfo>() {
                @Override
                public void onNext(GroupInfo groupInfo) {
                    mSubMachines = groupInfo.getSubMachines();
                    machineAdapter.setMachinesData(mSubMachines);
                }
            }).setProgressDialog(pcflMoreMachine.isRefreshing() || pcflMoreMachine.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflMoreMachine).build();
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        }else{
            Observable<MenuInfo> observable = MachinesApi.getMenuInfo(userInfo.getToken(), menuId);
            JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mContext).setOnNextListener(new SubscriberOnNextListener<MenuInfo>() {
                @Override
                public void onNext(MenuInfo menuInfo) {
                    ArrayList<GroupInfo.SubMachinesBean> subMachinesBeen = new ArrayList<>();
                    List<MenuInfo.SubMachinesBean> subMachines = menuInfo.getSubMachines();
                    for (MenuInfo.SubMachinesBean subMachine : subMachines) {
                        subMachinesBeen.add(new GroupInfo.SubMachinesBean(subMachine.getId(),subMachine.getName(),subMachine.getAddress()));
                    }
                    mSubMachines=subMachinesBeen;
                    machineAdapter.setMachinesData(mSubMachines);
                }
            }).setProgressDialog(pcflMoreMachine.isRefreshing() || pcflMoreMachine.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflMoreMachine).build();
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

        }

    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setTitle("包含咖啡机");

    }
}
