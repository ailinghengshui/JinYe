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
import com.hzjytech.operation.adapters.menu.PackItemAdapter;
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
public class MorePackItemActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_pack_item)
    RecyclerView rvPackItem;
    @BindView(R.id.pcfl_pack_item)
    PtrClassicFrameLayout pcflPackItem;
    private List<MenuInfo.PacksBean> packs;
    private PackItemAdapter packAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_more_pack;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();
    }
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
    private void initData() {
        Intent intent = getIntent();
        menuId = intent.getIntExtra("menuId", -1);
        User userInfo = UserUtils.getUserInfo();
        Observable<MenuInfo> observable = MachinesApi.getMenuInfo(userInfo.getToken(), menuId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<MenuInfo>() {
            @Override
            public void onNext(MenuInfo menuInfo) {
                packs = menuInfo.getPacks();
                packAdapter.setPackData(packs);
            }
        }).setProgressDialog(pcflPackItem.isRefreshing() ||pcflPackItem.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflPackItem).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void initPtrc() {
        pcflPackItem.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflPackItem.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflPackItem.setLoadMoreEnable(false);//设置可以加载更多
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPackItem.setLayoutManager(linearLayoutManager);
        packAdapter = new PackItemAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(packAdapter);
        rvPackItem.setAdapter(mAdapter);
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
        titleBar.setTitle("套餐属性");
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
