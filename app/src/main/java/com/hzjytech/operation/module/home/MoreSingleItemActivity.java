package com.hzjytech.operation.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.menu.SingleItemAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MoreSingleItemActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_single_item)
    RecyclerView rvSingleItem;
    @BindView(R.id.pcfl_single_item)
    PtrClassicFrameLayout pcflSingleItem;
    private RecyclerAdapterWithHF mAdapter;
    private PtrHandler ptrDefaultHandler=new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {

        }
    };
    private int menuId;
    private List<MenuInfo.ItemsBean> items;
    private SingleItemAdapter machineAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_single_item;
    }

    @Override
    protected void initView() {
           initTitle();
           initRecyclerView();
          initPtrc();
          initData();
    }

    private void initData() {
        Intent intent = getIntent();
        menuId = intent.getIntExtra("menuId", -1);
        User userInfo = UserUtils.getUserInfo();
        Observable<MenuInfo> observable = MachinesApi.getMenuInfo(userInfo.getToken(), menuId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<MenuInfo>() {
            @Override
            public void onNext(MenuInfo menuInfo) {
                items = menuInfo.getItems();
                 machineAdapter.setItemData(items);
            }
        }).setProgressDialog(pcflSingleItem.isRefreshing() || pcflSingleItem.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflSingleItem).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void initPtrc() {
        pcflSingleItem.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflSingleItem.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
       pcflSingleItem.setLoadMoreEnable(false);//设置可以加载更多
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSingleItem.setLayoutManager(linearLayoutManager);
        machineAdapter = new SingleItemAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(machineAdapter);
        rvSingleItem.setAdapter(mAdapter);
    }

    private void initTitle() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setTitle("单品属性");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
