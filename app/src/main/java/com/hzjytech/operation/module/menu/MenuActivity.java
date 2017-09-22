package com.hzjytech.operation.module.menu;

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
import com.hzjytech.operation.adapters.menu.MyMenuAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.MoreMachineClickListener;
import com.hzjytech.operation.inter.MorePackClickListener;
import com.hzjytech.operation.inter.MoreSingleItemClickListener;
import com.hzjytech.operation.module.home.MorePackItemActivity;
import com.hzjytech.operation.module.home.MoreSingleItemActivity;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MenuActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.pcfl_menu)
    PtrClassicFrameLayout pcflMenu;
    private MyMenuAdapter menuAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private int menuId;
    private PtrHandler ptrDefaultHandler=new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private OnLoadMoreListener onLoadMoreListener;

    @Override
    protected int getResId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();
        initListeners();
    }
// TODO: 2017/5/3  
    /**
     * 初始化各种监听,记得根据getIntent确定传入的路径
     */
    private void initListeners() {
        menuAdapter.setMoreMachineClickListener(new MoreMachineClickListener() {
            @Override
            public void click() {
                Intent intent = new Intent(MenuActivity.this, MoreMachineActivity.class);
                intent.putExtra("menuId",menuId);
                startActivity(intent);
            }
        });
        menuAdapter.setMoreSingleItemClickListener(new MoreSingleItemClickListener() {
            @Override
            public void click() {
                Intent intent = new Intent(MenuActivity.this, MoreSingleItemActivity.class);
                intent.putExtra("menuId",menuId);
                startActivity(intent);
            }
        });
        menuAdapter.setMorePackClickListener(new MorePackClickListener() {
            @Override
            public void click() {
                Intent intent = new Intent(MenuActivity.this, MorePackItemActivity.class);
                intent.putExtra("menuId",menuId);
                startActivity(intent);
            }
        });
    }


    /**
     * 初始化下拉刷新、上拉加载更多
     */
    private void initPtrc() {
       pcflMenu.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflMenu .setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflMenu.setLoadMoreEnable(false);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.setLayoutManager(linearLayoutManager);
        menuAdapter = new MyMenuAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(menuAdapter);
        rvMenu.setAdapter(mAdapter);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        menuId = intent.getIntExtra("menuId", -1);
        Observable<MenuInfo> observable = MachinesApi.getMenuInfo(userInfo.getToken(), menuId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<MenuInfo>() {
            @Override
            public void onNext(MenuInfo menuInfo) {
                 menuAdapter.setMenuData(menuInfo);
                 titleBar.setTitle(menuInfo.getName());
            }
        }).setProgressDialog(pcflMenu.isRefreshing()||pcflMenu.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflMenu).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    /**
     * 初始化标题栏
     */
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
